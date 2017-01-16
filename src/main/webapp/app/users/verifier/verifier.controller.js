(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('VerifierCtrlAs', VerifierCtrlAs);

    VerifierCtrlAs.$inject = ['$stateParams', '$state', 'userService', 'advertisementService', 'sessionService', 'PorettiHandler'];

    function VerifierCtrlAs($stateParams, $state, userService, advertisementService, sessionService, PorettiHandler) {

        var vm = this;

        vm.chosenAdvertisement = {};
        vm.reported = [];
        vm.showReports = false;
        vm.verifier = {};

        vm.actionBasedOnStatus = actionBasedOnStatus;
        vm.changeStatus = changeStatus;
        vm.getReports = getReports;
        vm.goToAdvertisement = goToAdvertisement;

        activate();

        function activate() {
            var userId = $stateParams.id;
            findUser(userId)
                .then(findReported)
                .catch(handleError);
        }

        function findUser(userId) {
            return userService.findOne(userId)
                .then(function(data) {
                    vm.verifier = data;
                });
        }

        function findReported() {
            return advertisementService.findReported()
                .then(function(data) {
                    return vm.reported = data;
                });
        }

        function getReports(advertisement) {
            vm.showReports = true;
            vm.chosenAdvertisement = advertisement;
            advertisementService.findReports(vm.chosenAdvertisement.id)
                .then(function(data) {
                    vm.chosenAdvertisement.reports = data;
                }).catch(handleError);
        }

        function goToAdvertisement(advertisement) {
            $state.go('advertisement', {id: advertisement.id});
        }

        function changeStatus() {
           advertisementService.changeStatus(vm.chosenAdvertisement)
               .then(findReported)
               .then(function() {
                   vm.showReports = false;
               }).catch(handleError);
        }

        function actionBasedOnStatus() {
            var action = advertisementService.actionBasedOnStatus(vm.chosenAdvertisement);
            return action;
        }

        function handleError(error) {
            PorettiHandler.report(error);
        }


    }
})(angular);
