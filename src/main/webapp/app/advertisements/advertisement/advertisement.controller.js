(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('AdvertisementCtrlAs', AdvertisementCtrlAs);

    AdvertisementCtrlAs.$inject = ['$stateParams', '$state', 'dialogService',
        'advertisementService', 'sessionService', 'userService', 'companyService',
        'PorettiHandler', 'dateHelper'];

    function AdvertisementCtrlAs($stateParams, $state, dialogService,
                                 advertisementService, sessionService, userService, companyService,
                                 PorettiHandler, dateHelper) {

        var vm = this;

        vm.advertisement = {};
        vm.canAdd = false;
        vm.newReview = {};
        vm.newReport = {};

        vm.createReview = createReview;
        vm.createReport = createReport;
        vm.goToProfileOf = goToProfileOf;
        vm.openDialogForReview = openDialogForReview;
        vm.openDialogForReport = openDialogForReport;

        activate();

        function activate() {
            var advertisementId = $stateParams.id;
            findAdvertisement(advertisementId)
                .then(findReviews)
                .then(findReports)
                .then(determineIfCanAdd)
                .catch(handleError)
        }

        function findAdvertisement(advertisementId) {
            return advertisementService.findOne(advertisementId)
                .then(function (data) {
                    vm.advertisement = data;
                    vm.advertisement.announcedOn = dateHelper.format(vm.advertisement.announcedOn);
                    vm.advertisement.editedOn = dateHelper.format(vm.advertisement.editedOn);
                    vm.advertisement.endsOn = dateHelper.format(vm.advertisement.endsOn);
                });
        }

        function findReviews() {
            return advertisementService.findReviews(vm.advertisement.id)
                .then(function (data) {
                    vm.advertisement.reviews = data;
                    vm.advertisement.reviews = advertisementService.reviewCanBeErased(vm.reviews, sessionService.getUser())
                });
        }

        function findReports() {
            return advertisementService.findReports(vm.advertisement.id)
                .then(function (data) {
                    vm.advertisement.reports = data;
                });
        }

        function determineIfCanAdd() {
            var loggedUser = sessionService.getUser();
            if (loggedUser) {
                var isAdvertiserOrOwner = loggedUser.id === vm.advertisement.advertiser.id || loggedUser.id === vm.advertisement.owner.id;
                if (!isAdvertiserOrOwner) {
                    vm.canAdd = true;
                }
            } else {
                vm.canAdd = false;
            }
        }

        function createReview() {
            advertisementService.createReview(vm.advertisement.id, vm.newReview)
                .then(findReviews)
                .catch(handleError)
        }

        function createReport() {
            advertisementService.createReport(vm.advertisement.id, vm.newReport)
                .then(findReports)
                .catch(handleError)
        }

        function openDialogForReview(ev) {
            dialogService.open(ev, 'ReviewDialogCtrlAs', 'app/advertisements/dialogs/reviewDialog.html')
                .then(function (review) {
                    vm.newReview = review;
                    createReview();
                }).catch(handleError);
        }

        function openDialogForReport(ev) {
            dialogService.open(ev, 'ReportDialogCtrlAs', 'app/advertisements/dialogs/reportDialog.html')
                .then(function (report) {
                    vm.newReport = report;
                    createReport();
                }).catch(handleError);
        }

        function goToProfileOf(user) {
            userService.findOne(user.id)
                .then(function (data) {
                    $state.go('user', {id: data.id});
                }).catch(function(error) {
                    return companyService.find(user.id);
                }).then(function(data) {
                $state.go('company', {id: data.id});
            }).catch(handleError);
        }

        function handleError(error) {
            if (angular.isString(error)) {
                PorettiHandler.report(error);
            } else {
                PorettiHandler.report(error.data.message);
            }
        }

    }
})(angular);