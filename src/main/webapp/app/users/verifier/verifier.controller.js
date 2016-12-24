(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('VerifierCtrlAs', VerifierCtrlAs);

    VerifierCtrlAs.$inject = ['userService', 'advertisementService'];

    function VerifierCtrlAs(userService, advertisementService) {

        var vm = this;

        vm.reported = [];
        vm.chosenAdvertisement = {};
        vm.showReports = false;
        vm.actionNameBasedOnStatus = actionNameBasedOnStatus;
        vm.getReports = getReports;
        vm.hideReports = hideReports;
        vm.changeStatus = changeStatus;
        activate();

        function activate() {
            advertisementService.findReported().then(function(response) {
                vm.reported = response.data;
            }).catch(function(error) {
                console.log(error);
            });
        }

        function getReports(advertisement) {
            vm.chosenAdvertisement = advertisement;
            if (!vm.showReports) {
                toggleShowReports();
            }
            advertisementService.findReports(advertisement.id).then(function(response) {
                vm.chosenAdvertisement.reports = response.data;
                vm.actionBasedOnStatus = vm.chosenAdvertisement.status;
            });
        }

        function toggleShowReports() {
            vm.showReports = !vm.showReports;
        }

        function hideReports() {
            if (vm.showReports){
                toggleShowReports();
            }
        }

        function changeStatus() {
            if (vm.chosenAdvertisement.status === "INVALID" || vm.chosenAdvertisement.status === "PENDING_APPROVAL") {
                advertisementService.approve(vm.chosenAdvertisement.id).then(function(response) {
                   var index = vm.reported.indexOf(vm.chosenAdvertisement);
                    vm.reported.splice(index,1);
                }).catch(function(error) {
                    console.log(error);
                });
            } else if (vm.chosenAdvertisement.status === "ACTIVE") {
                advertisementService.invalidate(vm.chosenAdvertisement.id).then(function(response) {
                    console.log("invalidated");
                }).catch(function(error) {
                    console.log(error);
                });
            }
        }

        function actionNameBasedOnStatus() {
            if (vm.chosenAdvertisement.status === "INVALID" || vm.chosenAdvertisement.status === "PENDING_APPROVAL") {
                return "APPROVE";
            }
            else if(vm.chosenAdvertisement.status === "ACTIVE") {
                return "INVALIDATE";
            }
            //TODO what to do with DONE status?
        }


    }
})(angular);
