(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('ReportDialogCtrlAs', ReportDialogCtrlAs);

    ReportDialogCtrlAs.$inject = ['$mdDialog'];

    function ReportDialogCtrlAs($mdDialog) {

        var vm = this;

        vm.report = {};
        vm.cancel = cancel;
        vm.confirmResponse = confirmResponse;
        vm.hide = hide;

        function cancel() {
            $mdDialog.cancel();
        }

        function confirmResponse(){
            $mdDialog.hide(vm.report);
        }

        function hide() {
            $mdDialog.hide();
        }
    }
})(angular);