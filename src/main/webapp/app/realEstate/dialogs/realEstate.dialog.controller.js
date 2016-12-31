(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('RealEstateDialogCtrlAs', RealEstateDialogCtrlAs);

    RealEstateDialogCtrlAs.$inject = ['$mdDialog', 'realEstate'];

    function RealEstateDialogCtrlAs($mdDialog, realEstate) {

        var vm = this;

        vm.hide = hide;
        vm.cancel = cancel;
        vm.confirmResponse = confirmResponse;
        vm.realEstate = realEstate;

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
