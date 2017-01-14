(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('CompanyEditDialogCtrlAs', CompanyEditDialogCtrlAs);

    CompanyEditDialogCtrlAs.$inject = ['$mdDialog', 'company'];

    function CompanyEditDialogCtrlAs($mdDialog, company) {

        var vm = this;

        vm.hide = hide;
        vm.cancel = cancel;
        vm.confirmResponse = confirmResponse;
        vm.companyToEdit = company;

        function hide() {
            $mdDialog.hide();
        }

        function cancel() {
            $mdDialog.cancel();
        }

        function confirmResponse(){
            $mdDialog.hide(vm.companyToEdit);
        }
    }
})(angular);

