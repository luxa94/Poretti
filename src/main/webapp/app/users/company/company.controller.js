(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('CompanyCtrlAs', CompanyCtrlAs);

    CompanyCtrlAs.$inject = ['$q', '$stateParams', 'companyService', 'sessionService', 'alertify', '$mdDialog'];

    function CompanyCtrlAs($q, $stateParams, companyService, sessionService, alertify, $mdDialog) {

        var vm = this;

        vm.company = {};
        vm.companyMemberships = [];
        vm.advertisements = [];
        vm.companyMembersCriteria = "";
        vm.canJoinCompany = false;
        vm.canLeaveCompany = false;
        vm.newRealEstate = {
            technicalEquipment: []
        };
        vm.newAdvertisement = {
            endsOn: new Date()
        };
        vm.advertisementDialog = {};
        vm.approveMembership = approveMembership;
        vm.leaveCompany = leaveCompany;
        vm.filteredMemberships = filteredMemberships;
        vm.createMembership = createMembership;
        vm.openDialogForReview = openDialogForReview;
        vm.openDialogForAdvertisement = openDialogForAdvertisement;
        vm.openStandaloneDialogForRealEstate = openStandaloneDialogForRealEstate;

        activate();

        function activate() {
            var companyId = $stateParams.id;
            companyService.findOne(companyId).then(function (response) {
                vm.company = response.data;
                return companyService.findMemberships(companyId);
            }).then(function (response) {
                vm.companyMemberships = response.data;
                vm.canJoinCompany = !_.some(vm.companyMemberships, function (membership) {
                    return membership.member.id === sessionService.getUser().id;
                });
                vm.canLeaveCompany = _.some(vm.companyMemberships, function (membership) {
                    return membership.member.id === sessionService.getUser().id;
                });

            }).catch(function (error) {
                alertify.delay(2000).error("Server error");
            });
        }

        function approveMembership(membership) {
            companyService.approveMembership(vm.company.id, membership.id).then(function (response) {
                activate();
            }).catch(function (error) {
                alert(error);
            })
        }

        function leaveCompany() {
            var membership = _.find(vm.companyMemberships, function (membership) {
                return sessionService.getUser().id === membership.member.id;
            });
            companyService.leaveCompany(vm.company.id, membership.id).then(function (response) {
                vm.canLeaveCompany = false;
                activate();
            }).catch(function (error) {
                alert(error);
            });
        }

        function filteredMemberships() {
            return _.filter(vm.companyMemberships, function (membership) {
                if (vm.companyMembersCriteria === "confirmed") {
                    return membership.confirmed;
                } else if (vm.companyMembersCriteria === "notConfirmed") {
                    return !membership.confirmed;
                }
                return membership;
            });
        }

        function createMembership() {
            companyService.createMembership(vm.company.id).then(function (response) {
                vm.canJoinCompany = false;
                activate();
            }).catch(function (error) {
                alert(error);
            })
        }

        function openDialogForReview(ev) {
            $mdDialog.show({
                controller: 'ReviewDialogCtrlAs',
                controllerAs: 'vm',
                templateUrl: 'app/advertisements/dialogs/reviewDialog.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                locals: {
                    review: vm.newReview
                }
            }).then(function (review) {
                vm.newReview = review;
                createReview();
            }).catch(function (error) {
                alertify.delay(2000).error("Server error.")
            })
        }

        function createReview() {
            companyService.createReview(vm.user.id, vm.newReview).then(function (response) {
                alertify.delay(2000).success("You added new review");
                return userService.findReviews(vm.user.id);
            }).then(function (response) {
                vm.reviews = response.data;
                _.forEach(vm.reviews, function (value) {
                    value.canBeErased = value.author.id === vm.user.id;
                });
                _.orderBy(vm.reviews, ['editedOn'], ['desc']);
            }).catch(function (error) {
                alertify.delay(1000).error("Server error.")
            });
        }


        function openDialogForAdvertisement(ev) {
            vm.advertisementDialog = $mdDialog;
            vm.advertisementDialog.show({
                controller: 'AdvertisementDialogCtrlAs',
                controllerAs: 'vm',
                templateUrl: 'app/advertisements/dialogs/advertisementDialog.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                locals: {
                    advertisement: vm.newAdvertisement,
                    realEstate: vm.newRealEstate,
                    realEstateDialog: openDialogForRealEstate,
                    advertisementDialog: vm.openDialogForAdvertisement
                }
            }).then(function (response) {
                if (response.realEstateIsChosen) {
                    createAdvertisementForRealEstate(_.omit(response, ['realEstateIsChosen']));
                } else {
                    createAdvertisementAndRealEstate(_.omit(response, ['realEstateIsChosen']));
                }
            }).catch(function (data) {
                console.log("Error in dialog");
            })
        }

        function openDialogForRealEstate(ev) {
            var deferred = $q.defer();
            $mdDialog.show({
                controller: 'RealEstateDialogCtrlAs',
                controllerAs: 'vm',
                templateUrl: 'app/realEstate/dialogs/realEstateDialog.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                locals: {
                    realEstate: vm.newRealEstate
                }
            }).then(function (realEstate) {
                vm.newRealEstate = realEstate;
                deferred.resolve(vm.newRealEstate);
            }).catch(function (data) {
                console.log("catch");
            });

            return deferred.promise;
        }

        function openStandaloneDialogForRealEstate(ev) {
            openDialogForRealEstate(ev).then(function (realEstate) {
                createRealEstate();
            }).catch(function (error) {
                console.log("console log");
            });
        }

        function createAdvertisementAndRealEstate(advertisementRealEstate) {
            companyService.createAdvertisementAndRealEstate(advertisementRealEstate).then(function (response) {
                alertify.delay(3000).success('You successfully added new advertisement!');
            }).catch(function (error) {
                alertify.delay(2000).error('Server error.');
            })
        }

        function createAdvertisementForRealEstate(advertisementRealEstate) {
            companyService.createAdvertisement(advertisementRealEstate.realEstateId, advertisementRealEstate.advertisement)
                .then(function (response) {
                    alertify.delay(3000).success('You successfully added new advertisement!');
                    findAdvertisements();
                }).catch(function (error) {
                    alertify.delay(2000).error('Server error.');
                });
        }

        function createRealEstate() {
            companyService.createRealEstate(vm.company.id, vm.newRealEstate).then(function (response) {
                alertify.delay(3000).success('You successfully added new real estate!');
                return companyService.findRealEstates(vm.company.id);
            }).then(function (response) {
                vm.realEstates = response.data;
            }).catch(function (error) {
                alertify.delay(2000).error('Server error.');
            });
        }

    }
})(angular);
