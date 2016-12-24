(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('AdvertisementCtrlAs', AdvertisementCtrlAs);

    AdvertisementCtrlAs.$inject = ['$stateParams', 'advertisementService'];

    function AdvertisementCtrlAs($stateParams, advertisementService) {

        var vm = this;

        vm.advertisement = {};
        vm.newReview = {};
        vm.newReport = {};
        vm.createReview = createReview;
        vm.createReport = createReport;

        activate();

        function activate() {
            advertisementService.findOne($stateParams.id).then(function(response) {
                vm.advertisement = response.data;
                return advertisementService.findReviews(vm.advertisement.id);
            }).then(function(response) {
                vm.advertisement.reviews = response.data;
                return advertisementService.findReports(vm.advertisement.id)
            }).then(function(response) {
                vm.advertisement.reports = response.data;
            }).catch(function(error) {
                console.log(error);
            })
        }

        function createReview() {
            advertisementService.createReview(vm.advertisement.id, vm.newReview).then(function(response){
                return advertisementService.findReviews(vm.advertisement.id);
            }).then(function(response) {
                vm.advertisement.reviews = response.data;
            }).catch(function(error) {
                console.log(error);
            });
        }

        function createReport() {
            //TODO Change this mocked reason
            vm.newReport.reason = "OTHER";
            advertisementService.createReport(vm.advertisement.id, vm.newReport).then(function(response) {
                return advertisementService.findReports(vm.advertisement.id);
            }).then(function(response) {
                vm.advertisement.reports = response.data;
            }).catch(function(error) {
                console.log(error);
            })
        }
    }
})(angular);