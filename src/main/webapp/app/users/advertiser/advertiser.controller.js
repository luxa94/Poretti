(function (angualar) {
    'use strict';

    angular
        .module('poretti')
        .controller('AdvertiserCtrlAs', AdvertiserCtrlAs);

    AdvertiserCtrlAs.$inject = ['$q', '$stateParams', '$state',
        'userService', 'sessionService', 'ownerReviewService',
        'advertisementService', 'realEstateService', 'roleService', 'dialogService', 'alertify'];

    function AdvertiserCtrlAs($q, $stateParams, $state,
                              userService, sessionService, ownerReviewService,
                              advertisementService, realEstateService, roleService,
                              dialogService, alertify) {

        var vm = this;

        vm.advertisements = [];
        vm.canAdd = false;
        vm.canEdit = false;
        vm.newAdvertisement = {};
        vm.newRealEstate = {};
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


        activate();

        function activate() {
            var userId = $stateParams.id;
            findUser(userId)
                .then(findAdvertisements)
                .then(findRealEstates)
                .then(findReviews)
                .then(findMemberships)
                .catch(handleError);

            vm.newAdvertisement.endsOn = new Date();
        }

        function findUser(userId) {
            return userService.findOne(userId)
                .then(function (data) {
                    vm.user = data;
                    if (vm.user.id === sessionService.getUser().id && roleService.isUser(vm.user)) {
                        vm.canEdit = true;
                        vm.canAdd = true;
                    }
                });
        }

        function findAdvertisements() {
            return userService.findAdvertisements(vm.user.id)
                .then(function (response) {
                    //TODO paging stuff?
                    vm.advertisements = response.data.content;
                })
        }

        function findRealEstates() {
            return userService.findRealEstates(vm.user.id)
                .then(function (data) {
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
            userService.createReview(vm.user.id)
                .then(findReviews)
                .catch(handleError)
        }

        function deleteReview(review) {
            ownerReviewService.deleteOne(review.id)
                .then(findReviews)
                .catch(handleError);
        }

        function goToAdvertisement(advertisement) {
            $state.go('advertisement', {id: advertisement.id});
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
                alertify.error("Server error.");
            });
        }

        function openDialogForAdvertisement(ev) {
            var locals = {
                advertisement: vm.newAdvertisement,
                realEstate: vm.newRealEstate,
                realEstateDialog: openDialogForRealEstate,
                advertisementDialog: vm.openDialogForAdvertisement
            };
            dialogService.open(ev, 'AdvertisementDialogCtrlAs', 'app/advertisements/dialogs/advertisementDialog.html', locals)
                .then(function (response) {
                    debugger;
                    if (response.realEstateIsChosen) {
                        createAdvertisementForRealEstate(_.omit(response, ['realEstateIsChosen']));
                    } else {
                        createAdvertisementAndRealEstate(_.omit(response, ['realEstateIsChosen']));
                    }
                }).catch(handleError);
        }


        function openDialogForEditingUser(ev) {
            dialogService.open(ev, 'UserEditDialogCtrlAs', 'app/users/dialogs/userEditDialog.html')
                .then(function (user) {
                    vm.userToEdit = user;
                    edit();
                }).catch(handleError);
        }

        function openDialogForEditingAdvertisement(ev, advertisement) {
            var locals = {
                advertisement: advertisement
            };
            dialogService.open(ev, 'AdvertisementEditDialogCtrlAs', 'app/advertisements/dialogs/advertisementEditDialog.html', locals)
                .then(function (advertisement) {
                    editAdvertisement(advertisement);
                }).catch(handleError);
        }

        function openDialogForRealEstate(ev, realEstate) {
            var deferred = $q.defer();
            var locals = {
                realEstate: realEstate
            };
            dialogService.open(ev, 'RealEstateDialogCtrlAs', 'app/realEstate/dialogs/realEstateDialog.html', locals)
                .then(function (realEstate) {
                    vm.newRealEstate = realEstate;
                    deferred.resolve(vm.newRealEstate);
                }).catch(handleError);

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
            }).catch(handleError);
        }

        function createAdvertisementAndRealEstate(advertisementRealEstate) {
            advertisementService.createAdvertisementAndRealEstate(advertisementRealEstate)
                .then(function (response) {
                    alertify.success('You successfully added new advertisement!');
                    findAdvertisements();
                }).catch(handleError);
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
                .then(function(response) {
                    alertify.success("Advertisement is edited");
                    findAdvertisements();
                }).catch(handleError);
        }

        function handleError() {
            //TODO handle error
        }

    }
})(angular);
