(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('RealEstatesDialogCtrlAs', RealEstatesDialogCtrlAs);

    RealEstatesDialogCtrlAs.$inject = ['$mdDialog', 'realEstates'];

    function RealEstatesDialogCtrlAs($mdDialog, realEstates) {

        var vm = this;

        vm.chosenRealEstate = {};
        vm.realEstates = realEstates;

        vm.hide = hide;
        vm.cancel = cancel;
        vm.confirmResponse = confirmResponse;



        function hide() {
            $mdDialog.hide();
        }

        function cancel() {
            $mdDialog.cancel();
        }

        function confirmResponse(){
            $mdDialog.hide(vm.chosenRealEstate);
        }
    }
})(angular);
