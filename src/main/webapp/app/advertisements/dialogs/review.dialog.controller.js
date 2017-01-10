(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('ReviewDialogCtrlAs', ReviewDialogCtrlAs);

    ReviewDialogCtrlAs.$inject = ['$mdDialog'];

    function ReviewDialogCtrlAs($mdDialog) {

        var vm = this;

        vm.review = {};
        vm.cancel = cancel;
        vm.confirmResponse = confirmResponse;
        vm.hide = hide;

        function cancel() {
            $mdDialog.cancel();
        }

        function confirmResponse() {
            $mdDialog.hide(vm.review);
        }

        function hide() {
            $mdDialog.hide();
        }

    }
})(angular);