(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('UserEditDialogCtrlAs', UserEditDialogCtrlAs);

    UserEditDialogCtrlAs.$inject = ['$mdDialog', 'user'];

    function UserEditDialogCtrlAs($mdDialog, user) {

        var vm = this;

        vm.hide = hide;
        vm.cancel = cancel;
        vm.confirmResponse = confirmResponse;
        vm.userToEdit = user;

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
