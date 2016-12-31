(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('ReviewDialogCtrlAs', ReviewDialogCtrlAs);

    ReviewDialogCtrlAs.$inject = ['$mdDialog', 'review'];

    function ReviewDialogCtrlAs($mdDialog, review) {

        var vm = this;

        vm.hide = hide;
        vm.cancel = cancel;
        vm.confirmResponse = confirmResponse;
        vm.review = review;

        function hide() {
            $mdDialog.hide();
        }

        function cancel() {
            $mdDialog.cancel();
        }

        function confirmResponse() {
            $mdDialog.hide(vm.review);
        }
    }
})(angular);