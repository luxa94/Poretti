(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('AdvertisementCtrlAs', AdvertisementCtrlAs);

    AdvertisementCtrlAs.$inject = ['$stateParams', 'dialogService', 'advertisementService', 'PorettiHandler'];

    function AdvertisementCtrlAs($stateParams, dialogService, advertisementService, PorettiHandler) {

        var vm = this;

        vm.advertisement = {};
        vm.newReview = {};
        vm.newReport = {};
        vm.createReview = createReview;
        vm.createReport = createReport;
        vm.openDialogForReview = openDialogForReview;
        vm.openDialogForReport = openDialogForReport;

        activate();

        function activate() {
            var advertisementId = $stateParams.id;
            findAdvertisement(advertisementId)
                .then(findReviews)
                .then(findReports)
                .catch(handleError)
        }

        function findAdvertisement(advertisementId) {
            return advertisementService.findOne(advertisementId)
                .then(function (data) {
                    vm.advertisement = data;
                });
        }

        function findReviews() {
            return advertisementService.findReviews(vm.advertisement.id)
                .then(function (data) {
                    vm.advertisement.reviews = data;
                });
        }

        function findReports() {
            return advertisementService.findReports(vm.advertisement.id)
                .then(function (data) {
                    vm.advertisement.reports = data;
                });
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

        function handleError(error) {
            PorettiHandler.report(error.data.message);
        }
    }
})(angular);