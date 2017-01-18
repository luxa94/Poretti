(function (angualar) {
    'use strict';

    angular
        .module('poretti')
        .controller('AdvertiserCtrlAs', AdvertiserCtrlAs);

    AdvertiserCtrlAs.$inject = ['$q', '$stateParams', '$state',
        'userService', 'sessionService', 'ownerReviewService',
        'advertisementService', 'realEstateService', 'roleService', 'dialogService', 'alertify', 'PorettiHandler', '$mdDialog'];

    function AdvertiserCtrlAs($q, $stateParams, $state,
                              userService, sessionService, ownerReviewService,
                              advertisementService, realEstateService, roleService,
                              dialogService, alertify, PorettiHandler, $mdDialog) {

        var vm = this;

        vm.advertisements = [];
        vm.canAdd = false;
        vm.canEdit = false;
        vm.isAdvertiser = true;
        vm.newAdvertisement = {};
        vm.newRealEstate = {};
        vm.newReview = {};
        vm.memberships = [];
        vm.realEstates = [];
        vm.reviews = [];
        vm.user = {};
        vm.userToEdit = {};

        vm.edit = edit;
        vm.createReview = createReview;
        vm.deleteReview = deleteReview;
        vm.goToAdvertisement = goToAdvertisement;
        vm.goToCompany = goToCompany;
        vm.openDialogForReview = openDialogForReview;
        vm.openDialogForAdvertisement = openDialogForAdvertisement;
        vm.openStandaloneDialogForRealEstate = openStandaloneDialogForRealEstate;
        vm.openDialogForEditingUser = openDialogForEditingUser;
        vm.openDialogForEditingAdvertisement = openDialogForEditingAdvertisement;
        vm.deleteAdvertisement = deleteAdvertisement;
        vm.openDialogDeleteRealEstate = openDialogDeleteRealEstate;

        activate();

        function activate() {
            var userId = $stateParams.id;
            vm.newAdvertisement.endsOn = new Date();
            findUser(userId)
                .then(findAdvertisements)
                .then(findRealEstates)
                .then(findReviews)
                .then(findMemberships)
                .catch(handleError);
        }

        function findUser(userId) {
            return userService.findOne(userId)
                .then(function (data) {
                    vm.user = data;
                    if (!roleService.isUser(data)) {
                        vm.isAdvertiser = false;
                        return $q.reject("It seems like there is nothing for you on this page");
                    }
                    setupAllowedActionsForLoggedUser();
                });
        }

        function setupAllowedActionsForLoggedUser() {
            var loggedUser = sessionService.getUser();
            if (loggedUser) {
                if (vm.user.id === loggedUser.id && roleService.isUser(vm.user)) {
                    vm.canEdit = true;
                    vm.canAdd = true;
                }
            }
        }

        function findAdvertisements() {
            return userService.findAdvertisements(vm.user.id)
                .then(function (response) {
                    vm.newAdvertisement = {};
                    vm.newAdvertisement.endsOn = new Date();
                    vm.newRealEstate = {};
                    vm.advertisements = response.data.content;
                });
        }

        function findRealEstates() {
            return userService.findRealEstates(vm.user.id)
                .then(function (data) {
                    vm.newRealEstate = {};
                    vm.realEstates = data;
                });
        }

        function findReviews() {
            return userService.findReviews(vm.user.id)
                .then(function (data) {
                    vm.reviews = data;
                    vm.reviews = userService.reviewCanBeErased(vm.reviews, sessionService.getUser())
                });
        }

        function findMemberships() {
            return userService.findMemberships(vm.user.id)
                .then(function (data) {
                    vm.memberships = data;
                });
        }

        function edit() {
            userService.edit(vm.user.id, vm.userToEdit)
                .then(function (data) {
                    vm.user = data;
                }).catch(handleError);
        }

        function createReview() {
            userService.createReview(vm.user.id, vm.newReview)
                .then(findReviews)
                .catch(handleError);
        }

        function deleteReview(review) {
            ownerReviewService.deleteOne(review.id)
                .then(findReviews)
                .catch(handleError);
        }

        function goToAdvertisement(advertisement) {
            $state.go('advertisement', {id: advertisement.id});
        }

        function deleteAdvertisement(advertisement) {
            advertisementService.deleteOne(advertisement)
                .then(function() {
                    findAdvertisements();
                })
                .catch(function () {
                    alertify.error("Could not delete advertisement.");
                });
        }

        function goToCompany(company) {
            $state.go('company', {id: company.id});
        }

        function openDialogForReview(ev) {
            dialogService.open(ev, 'ReviewDialogCtrlAs', 'app/advertisements/dialogs/reviewDialog.html')
                .then(function (review) {
                    vm.newReview = review;
                    createReview();
                }).catch(function (error) {
                console.log("Canceled");
            });
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


        function openDialogForEditingUser(ev) {
            var locals = {
                user: angular.copy(vm.user)
            };
            dialogService.open(ev, 'UserEditDialogCtrlAs', 'app/users/dialogs/userEditDialog.html', locals)
                .then(function (user) {
                    vm.userToEdit = user;
                    edit();
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
                console.log("Canceled");
            });
        }

        function createAdvertisementAndRealEstate(advertisementRealEstate) {
            advertisementService.createAdvertisementAndRealEstate(advertisementRealEstate)
                .then(function (response) {
                    alertify.success('You successfully added new advertisement!');
                    findAdvertisements();
                }).then(findRealEstates)
                .catch(handleError);
        }

        function createAdvertisementForRealEstate(advertisementRealEstate) {
            realEstateService.createAdvertisementForRealEstate(advertisementRealEstate)
                .then(function (response) {
                    alertify.success('You successfully added new advertisement!');
                    findAdvertisements();
                }).catch(handleError);
        }

        function editRealEstate(realEstate) {
            realEstateService.edit(realEstate)
                .then(function (response) {
                    alertify.success('You successfully edited real estate!');
                    findRealEstates();
                }).catch(handleError);
        }


        function createRealEstate(realEstate) {
            realEstateService.create(realEstate)
                .then(function (response) {
                    alertify.success('You successfully added new real estate!');
                    findRealEstates();
                }).catch(handleError);
        }


        function editAdvertisement(advertisement) {
            advertisementService.edit(advertisement)
                .then(function (response) {
                    alertify.success("Advertisement is edited");
                    findAdvertisements();
                }).catch(handleError);
        }

        function openDialogDeleteRealEstate(ev, realEstate) {
            var confirm = $mdDialog.confirm()
                .title('Would you like to delete this real estate?')
                .textContent('Every advertisement which contains this real estate will be deleted also')
                .ariaLabel('Real estate deleting')
                .targetEvent(ev)
                .ok('Yes')
                .cancel('No');

            $mdDialog.show(confirm).then(function() {
                deleteRealEstate(realEstate);
            }).catch(function(error) {});
        }

        function deleteRealEstate(realEstate) {
            realEstateService.delete(realEstate)
                .then(findAdvertisements)
                .then(findRealEstates)
                .catch(handleError);
        }

        function handleError(error) {
            PorettiHandler.report(error);
        }

    }
})(angular);
