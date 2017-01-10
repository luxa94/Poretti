(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('UserEditDialogCtrlAs', UserEditDialogCtrlAs);

    UserEditDialogCtrlAs.$inject = ['$mdDialog'];

    function UserEditDialogCtrlAs($mdDialog) {

        var vm = this;

        vm.hide = hide;
        vm.cancel = cancel;
        vm.confirmResponse = confirmResponse;
        vm.userToEdit = {};

        activate();

        function activate() {
            vm.userToEdit.phoneNumbers = [];
            vm.userToEdit.contactEmails = [];
        }

        function hide() {
            $mdDialog.hide();
        }

        function cancel() {
            $mdDialog.cancel();
        }

        function confirmResponse(){
            $mdDialog.hide(vm.userToEdit);
        }
    }
})(angular);
