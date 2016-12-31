(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('AdvertisementCtrlAs', AdvertisementCtrlAs);

    AdvertisementCtrlAs.$inject = ['$stateParams', '$mdDialog', 'advertisementService'];

    function AdvertisementCtrlAs($stateParams, $mdDialog, advertisementService) {

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
            advertisementService.findOne($stateParams.id).then(function (response) {
                vm.advertisement = response.data;
                return advertisementService.findReviews(vm.advertisement.id);
            }).then(function (response) {
                vm.advertisement.reviews = response.data;
                return advertisementService.findReports(vm.advertisement.id)
            }).then(function (response) {
                vm.advertisement.reports = response.data;
            }).catch(function (error) {
                console.log(error);
            })
        }

        function createReview() {
            advertisementService.createReview(vm.advertisement.id, vm.newReview).then(function (response) {
                return advertisementService.findReviews(vm.advertisement.id);
            }).then(function (response) {
                vm.advertisement.reviews = response.data;
            }).catch(function (error) {
                console.log(error);
            });
        }

        function createReport() {
            //TODO Change this mocked reason
            advertisementService.createReport(vm.advertisement.id, vm.newReport).then(function (response) {
                return advertisementService.findReports(vm.advertisement.id);
            }).then(function (response) {
                vm.advertisement.reports = response.data;
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
                vm.newReview = review;
                createReview();
            }).catch(function(data) {
                console.log("catch");
            })

        }

        function openDialogForReport(ev) {
            $mdDialog.show({
                controller: 'ReportDialogCtrlAs',
                controllerAs: 'vm',
                templateUrl: 'app/advertisements/dialogs/reportDialog.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                locals: {
                    report: vm.report
                }
            }).then(function (report) {
                vm.newReport = report;
                createReport();
            }).catch(function() {
                console.log("another catch");
            })
        }
    }
})(angular);