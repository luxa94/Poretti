(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('AdvertisementDialogCtrlAs', AdvertisementDialogCtrlAs);

    AdvertisementDialogCtrlAs.$inject = ['$mdDialog', 'advertisement', 'realEstateDialog', 'advertisementDialog'];

    function AdvertisementDialogCtrlAs($mdDialog, advertisement, realEstateDialog, advertisementDialog) {

        var vm = this;

        vm.show = show;
        vm.hide = hide;
        vm.cancel = cancel;
        vm.confirmResponse = confirmResponse;
        vm.advertisement = advertisement;
        vm.advertisement.endsOn = new Date();
        vm.realEstateDialog = realEstateDialog;
        vm.openDialogForRealEstate = openDialogForRealEstate;
        vm.advertisementDialog = advertisementDialog;

        function show() {
            $mdDialog.show();
        }
        function hide() {
            $mdDialog.hide();
        }

        function cancel() {
            $mdDialog.cancel();
        }

        function confirmResponse(){
            $mdDialog.hide(vm.advertisement);
        }

        function openDialogForRealEstate(ev) {
            realEstateDialog(ev).then(function(data) {
                vm.advertisementDialog(null);
            });
        }
    }
})(angular);
