(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('CompanyCtrlAs', CompanyCtrlAs);

    CompanyCtrlAs.$inject = ['$q', '$state', '$stateParams', 'companyService',
        'sessionService', 'alertify', 'dialogService', 'roleService', 'PorettiHandler', 'ownerReviewService'];

    function CompanyCtrlAs($q, $state, $stateParams, companyService,
                           sessionService, alertify, dialogService, roleService, PorettiHandler, ownerReviewService) {

        var vm = this;

        vm.advertisements = [];
        vm.canAdd = false;
        vm.canEdit = false;
        vm.canJoinCompany = false;
        vm.canLeaveCompany = false;
        vm.company = {};
        vm.companyMemberships = [];
        vm.companyMembersCriteria = "";
        vm.newRealEstate = {};
        vm.newAdvertisement = {};
        vm.realEstates = [];
        vm.reviews = [];

        vm.approveMembership = approveMembership;
        vm.deleteReview = deleteReview;
        vm.deleteAdvertisement = deleteAdvertisement;
        vm.deleteRealEstate = deleteRealEstate;
        vm.filteredMemberships = filteredMemberships;
        vm.goToAdvertisement = goToAdvertisement;
        vm.leaveCompany = leaveCompany;
        vm.createMembership = createMembership;
        vm.openDialogForReview = openDialogForReview;
        vm.openDialogForAdvertisement = openDialogForAdvertisement;
        vm.openDialogForEditingCompany = openDialogForEditingCompany;
        vm.openDialogForEditingAdvertisement = openDialogForEditingAdvertisement;
        vm.openStandaloneDialogForRealEstate = openStandaloneDialogForRealEstate;

        activate();

        function activate() {
            var companyId = $stateParams.id;
            vm.newAdvertisement.endsOn = new Date();
            findCompany(companyId)
                .then(findMemberships)
                .then(findAdvertisements)
                .then(findRealEstates)
                .then(findReviews)
                .then(canEditOrAdd)
                .catch(handleError);

            vm.newAdvertisement.endsOn = new Date();
        }

        function findCompany(companyId) {
            return companyService.find(companyId)
                .then(function (data) {
                    vm.company = data;
                })
        }

        function findMemberships() {
            return companyService.findMemberships(vm.company.id)
                .then(function (data) {
                    vm.companyMemberships = data;
                    vm.canJoinCompany = companyService.canJoinCompany(sessionService.getUser(), vm.companyMemberships);
                    vm.canLeaveCompany = companyService.canLeaveCompany(sessionService.getUser(), vm.companyMemberships);
                });
        }

        function findAdvertisements() {
            return companyService.findAdvertisements(vm.company.id)
                .then(function (response) {
                    vm.newAdvertisement = {};
                    vm.newAdvertisement.endsOn = new Date();
                    vm.newRealEstate = {};
                    vm.advertisements = response.data.content;
                });
        }

        function findRealEstates() {
            return companyService.findRealEstates(vm.company.id)
                .then(function (data) {
                    vm.newRealEstate = {};
                    vm.realEstates = data;
                });
        }

        function findReviews() {
            return companyService.findReviews(vm.company.id)
                .then(function (data) {
                    vm.reviews = data;
                    vm.reviews = companyService.reviewCanBeErased(vm.reviews, sessionService.getUser())
                });
        }

        function canEditOrAdd() {
            vm.canAdd = false;
            vm.canEdit = false;
            var membership = companyService.findMembershipForUser(vm.companyMemberships, sessionService.getUser());
            if (membership && membership.confirmed) {
                vm.canAdd = true;
                vm.canEdit = true;
            }
        }


        function approveMembership(membership) {
            companyService.approveMembership(vm.company, membership)
                .then(function (response) {
                    return findMemberships();
                }).then(canEditOrAdd)
                .catch(handleError);

        }

        function leaveCompany() {
            var membership = companyService.findMembershipForUser(vm.companyMemberships, sessionService.getUser());
            companyService.leaveCompany(vm.company, membership)
                .then(function (response) {
                    vm.canLeaveCompany = false;
                    vm.canJoinCompany = !vm.canLeaveCompany;
                    return findMemberships();
                }).then(canEditOrAdd)
                .catch(handleError);
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
            companyService.createMembership(vm.company.id)
                .then(function (response) {
                    vm.canJoinCompany = false;
                    return findMemberships();
                }).then(canEditOrAdd)
                .catch(handleError);
        }

        function edit() {
            companyService.edit(vm.company.id, vm.company)
                .then(function (response) {
                    findCompany(vm.company.id)
                })
                .catch(handleError);
        }

        function openDialogForEditingCompany(ev) {
            var locals = {
                company: angular.copy(vm.company)
            };
            dialogService.open(ev, 'CompanyEditDialogCtrlAs', 'app/users/dialogs/companyEditDialog.html', locals)
                .then(function (editedCompany) {
                    vm.company = editedCompany;
                    edit();
                }).catch(function (error) {
                console.log("Canceled");
            });
        }

        function openDialogForReview(ev) {
            dialogService.open(ev, 'ReviewDialogCtrlAs', 'app/advertisements/dialogs/reviewDialog.html')
                .then(function (review) {
                    vm.newReview = review;
                    createReview();
                }).catch(function (error) {
                console.log("Canceled");
            })
        }

        function createReview() {
            companyService.createReview(vm.company.id, vm.newReview)
                .then(findReviews)
                .catch(handleError)
        }


        function openDialogForAdvertisement(ev) {
            var locals = {
                advertisement: vm.newAdvertisement,
                realEstate: vm.newRealEstate,
                realEstateDialog: openDialogForRealEstate,
                realEstatesDialog: openDialogForChoosingRealEstate,
                advertisementDialog: openDialogForAdvertisement
            };
            dialogService.open(ev, 'AdvertisementDialogCtrlAs', 'app/advertisements/dialogs/advertisementDialog.html', locals)
                .then(function (response) {
                    if (response.realEstateIsChosen) {
                        createAdvertisementForRealEstate(_.omit(response, ['realEstateIsChosen']));
                    } else {
                        createAdvertisementAndRealEstate(_.omit(response, ['realEstateIsChosen']));
                    }
                }).catch(function (error) {
                console.log("Canceled");
            });
        }

        function openDialogForEditingAdvertisement(ev, advertisement) {
            var locals = {
                advertisement: angular.copy(advertisement)
            };
            dialogService.open(ev, 'AdvertisementEditDialogCtrlAs', 'app/advertisements/dialogs/advertisementEditDialog.html', locals)
                .then(function (advertisement) {
                    editAdvertisement(advertisement);
                }).catch(function (error) {
                console.log("Canceled");
            });
        }

        function openDialogForRealEstate(ev, realEstate) {
            var deferred = $q.defer();
            var locals = {
                realEstate: angular.copy(realEstate)
            };
            dialogService.open(ev, 'RealEstateDialogCtrlAs', 'app/realEstate/dialogs/realEstateDialog.html', locals)
                .then(function (realEstate) {
                    vm.newRealEstate = realEstate;
                    deferred.resolve(vm.newRealEstate);
                }).catch(function (error) {
                console.log("Canceled");
            });

            return deferred.promise;
        }

        function openDialogForChoosingRealEstate(ev) {
            var deferred = $q.defer();
            var locals = {
                realEstates: vm.realEstates
            };
            dialogService.open(ev, 'RealEstatesDialogCtrlAs', 'app/realEstate/dialogs/realEstatesDialog.html', locals)
                .then(function (realEstate) {
                    vm.newRealEstate = realEstate;
                    deferred.resolve(vm.newRealEstate);
                }).catch(function (error) {
                console.log("Canceled");
            });

            return deferred.promise;
        }

        function openStandaloneDialogForRealEstate(ev, realEstate) {
            openDialogForRealEstate(ev, realEstate)
                .then(function (realEstate) {
                    if (realEstate.id) {
                        editRealEstate(realEstate);
                    } else {
                        createRealEstate(realEstate);
                    }
                }).catch(function (error) {
                vm.newRealEstate = {};
                console.log("Canceled");
            });
        }


        function createAdvertisementAndRealEstate(advertisementRealEstate) {
            companyService.createAdvertisementAndRealEstate(vm.company.id, advertisementRealEstate)
                .then(function (response) {
                    alertify.success('You successfully added new advertisement!');
                    findAdvertisements();
                }).catch(handleError);
        }

        function createAdvertisementForRealEstate(advertisementRealEstate) {
            companyService.createAdvertisementForRealEstate(vm.company.id, advertisementRealEstate)
                .then(function (response) {
                    alertify.success('You successfully added new advertisement!');
                    findAdvertisements();
                }).catch(handleError);
        }

        function createRealEstate() {
            companyService.createRealEstate(vm.company.id, vm.newRealEstate)
                .then(function (response) {
                    alertify.delay(3000).success('You successfully added new real estate!');
                    findRealEstates();
                }).catch(handleError);
        }

        function editRealEstate(realEstate) {
            companyService.editRealEstate(vm.company.id, realEstate)
                .then(function (response) {
                    alertify.success('You successfully edited real estate!');
                    findRealEstates();
                }).catch(handleError);
        }

        function editAdvertisement(advertisement) {
            companyService.editAdvertisement(vm.company.id, advertisement)
                .then(function (response) {
                    alertify.success("Advertisement is edited");
                    findAdvertisements();
                }).catch(handleError);
        }

        function deleteReview(review) {
            ownerReviewService.deleteOne(review.id)
                .then(findReviews)
                .catch(handleError);
        }

        function deleteAdvertisement(advertisement) {
            companyService.deleteAdvertisement(vm.company.id, advertisement.id)
                .then(findAdvertisements)
                .catch(handleError);
        }

        function deleteRealEstate(realEstate) {
            companyService.deleteRealEstate(vm.company.id, realEstate.id)
                .then(findAdvertisements)
                .then(findRealEstates)
                .catch(handleError)
        }

        function goToAdvertisement(advertisement) {
            $state.go('advertisement', {id: advertisement.id});
        }

        function handleError(error) {
            PorettiHandler.report(error);
        }

    }
})(angular);
