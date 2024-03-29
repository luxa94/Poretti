(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('RealEstateDialogCtrlAs', RealEstateDialogCtrlAs);

    RealEstateDialogCtrlAs.$inject = ['$mdDialog', 'realEstate', 'NgMap'];

    function RealEstateDialogCtrlAs($mdDialog, realEstate, NgMap) {

        var vm = this;

        vm.hide = hide;
        vm.cancel = cancel;
        vm.confirmResponse = confirmResponse;
        vm.realEstate = {};
        vm.realEstate.location = {};

        activate();

        function activate() {
            if (!realEstate) {
                vm.realEstate.technicalEquipment = [];
            } else {
                vm.realEstate = realEstate;
            }
        }


        function hide() {
            $mdDialog.hide();
        }

        function cancel() {
            $mdDialog.cancel();
        }

        function confirmResponse(){
            $mdDialog.hide(vm.realEstate);
        }
    }
})(angular);
