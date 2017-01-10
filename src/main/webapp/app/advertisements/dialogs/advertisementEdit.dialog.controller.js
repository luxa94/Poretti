(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('AdvertisementEditDialogCtrlAs', AdvertisementEditDialogCtrlAs);

    AdvertisementEditDialogCtrlAs.$inject = ['$mdDialog', 'advertisement'];

    function AdvertisementEditDialogCtrlAs($mdDialog, advertisement) {

        var vm = this;

        vm.advertisement = advertisement;

        vm.show = show;
        vm.hide = hide;
        vm.cancel = cancel;
        vm.confirmResponse = confirmResponse;

        activate()

        function activate() {
            vm.advertisement.endsOn = new Date(advertisement.endsOn);
        }

        function show() {
            $mdDialog.show();
        }

        function hide() {
            $mdDialog.hide();
        }

        function cancel() {
            $mdDialog.cancel();
        }

        function confirmResponse() {
          $mdDialog.hide(vm.advertisement);
        }

    }
})(angular);
