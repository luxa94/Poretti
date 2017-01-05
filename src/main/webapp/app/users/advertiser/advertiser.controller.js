(function (angualar) {
    'use strict';

    angular
        .module('poretti')
        .controller('AdvertiserCtrlAs', AdvertiserCtrlAs)

    AdvertiserCtrlAs.$inject = ['$q', '$stateParams', 'userService', 'sessionService', 'ownerReviewService', 'advertisementService', 'realEstateService', 'alertify', '$mdDialog'];

    function AdvertiserCtrlAs($q, $stateParams,
                              userService, sessionService, ownerReviewService,
                              advertisementService, realEstateService, alertify, $mdDialog) {

        var vm = this;

        vm.user = {};
        vm.advertisements = [];
        vm.userToEdit = {};
        vm.newReview = {};
        vm.reviews = [];
        vm.canEdit = false;
        vm.newRealEstate = {
            technicalEquipment: []
        };
        vm.newAdvertisement = {
            endsOn: new Date()
        };
        vm.advertisementDialog = {};
        vm.canAdd = false;
        vm.edit = edit;
        vm.createReview = createReview;
        vm.deleteReview = deleteReview;
        vm.openDialogForReview = openDialogForReview;
        vm.openDialogForAdvertisement = openDialogForAdvertisement;
        vm.openStandaloneDialogForRealEstate = openStandaloneDialogForRealEstate;
        vm.openDialogForEditingData = openDialogForEditingData;


        activate();

        function activate() {
            userService.findOne($stateParams.id).then(function (response) {
                vm.user = response.data;
                if (vm.user.id === sessionService.getUser().id) {
                    vm.canEdit = true;
                    vm.canAdd = true;
                }
                return userService.findAdvertisements(vm.user.id);
            }).then(function (response) {
                vm.advertisements = response.data.content;
                return userService.findRealEstates(vm.user.id);
            }).then(function (response) {
                vm.realEstates = response.data;
                return userService.findReviews(vm.user.id);
            }).then(function (response) {
                debugger;
                if (response.data) {
                    vm.reviews = response.data;
                    vm.reviews = _.forEach(vm.reviews, function (review) {
                        review.canBeErased = review.author.id === sessionService.getUser().id;
                    });
                    vm.reviews = _.orderBy(vm.reviews, ['editedOn'], ['desc']);
                }
            }).catch(function (error) {
                alertify.delay(3000).error("Server error");
                console.log(error);
            });
        }

        function edit() {
            userService.edit(vm.user.id, vm.userToEdit).then(function (response) {
                vm.user = response.data;
            }).catch(function (error) {
                alertify.delay(2000).error("Server error");
            })
        }

        function createReview() {
            userService.createReview(vm.user.id, vm.newReview).then(function (response) {
                return userService.findReviews(vm.user.id);
            }).then(function (response) {
                vm.reviews = response.data;
            }).catch(function (error) {
                alertify.delay(2000).error("Server error");
            });
        }

        function deleteReview(review) {
            ownerReviewService.delete(review.id).then(function (response) {
                var index = vm.reviews.indexOf(review);
                vm.reviews.splice(index);
            }).catch(function (error) {
                console.log(error);
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
                debugger;
                vm.newReview = review;
                createReview();
            }).catch(function (error) {
                alertify.delay(2000).error("Server error.")
            })
        }

        function createReview() {
            userService.createReview(vm.user.id, vm.newReview).then(function (response) {
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
            })

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
            advertisementService.create(advertisementRealEstate).then(function (response) {
                alertify.delay(3000).success('You successfully added new advertisement!');
                findAdvertisements();
            }).catch(function (error) {
                alertify.delay(2000).error('Server error.');
            })
        }

        function createAdvertisementForRealEstate(advertisementRealEstate) {
            realEstateService.createAdvertisement(advertisementRealEstate.realEstateId, advertisementRealEstate.advertisement)
                .then(function (response) {
                    alertify.delay(3000).success('You successfully added new advertisement!');
                    findAdvertisements();
                }).catch(function (error) {
                alertify.delay(2000).error('Server error.');
            })
        }

        function createRealEstate() {
            realEstateService.create(vm.newRealEstate).then(function (response) {
                alertify.delay(3000).success('You successfully added new real estate!');
                return userService.findRealEstates(vm.user.id);
            }).then(function (response) {
                vm.realEstates = response.data;
            }).catch(function (error) {
                alertify.delay(2000).error('Server error.');
            })
        }

        function openDialogForEditingData(ev) {
            $mdDialog.show({
                controller: 'UserEditDialogCtrlAs',
                controllerAs: 'vm',
                templateUrl: 'app/users/dialogs/userEditDialog.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true
            }).then(function (user) {
                vm.userToEdit = user;
                var loggedUser = sessionService.getUser().id;
                return userService.edit(loggedUser, vm.userToEdit);
            }).then(function (response) {
                vm.user = response.data;
            }).catch(function (error) {
                alertify.delay(2000).error("Server error.")
            })
        }

    }
})(angular);
