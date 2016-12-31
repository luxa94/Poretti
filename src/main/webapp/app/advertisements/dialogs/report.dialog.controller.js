(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('ReportDialogCtrlAs', ReportDialogCtrlAs);

    ReportDialogCtrlAs.$inject = ['$mdDialog', 'report'];

    function ReportDialogCtrlAs($mdDialog, report) {

        var vm = this;

        vm.hide = hide;
        vm.cancel = cancel;
        vm.confirmResponse = confirmResponse;
        vm.report = report;

        function hide() {
            $mdDialog.hide();
        }

        function cancel() {
            $mdDialog.cancel();
        }

        function confirmResponse(){
            $mdDialog.hide(vm.report);
        }
    }
})(angular);