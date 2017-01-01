(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('AdvertisementDialogCtrlAs', AdvertisementDialogCtrlAs);

    AdvertisementDialogCtrlAs.$inject = ['$mdDialog', 'advertisement', 'realEstate', 'realEstateDialog', 'advertisementDialog'];

    function AdvertisementDialogCtrlAs($mdDialog, advertisement, realEstate, realEstateDialog, advertisementDialog) {

        var vm = this;

        vm.advertisement = advertisement;
        vm.realEstate = realEstate;
        vm.realEstateDialog = realEstateDialog;
        vm.advertisementDialog = advertisementDialog;
        vm.show = show;
        vm.hide = hide;
        vm.cancel = cancel;
        vm.confirmResponse = confirmResponse;
        vm.openDialogForRealEstate = openDialogForRealEstate;
        vm.realEstateIsAdded = false;

        activate();

        function activate() {
            debugger;
            vm.advertisement;
            vm.realEstateIsAdded = !_.values(vm.realEstate).every(function(prop) {
                return _.isUndefined(prop) || _.isEmpty(prop);
            });
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
            if (vm.realEstate.id){
                $mdDialog.hide({
                    realEstateIsChosen: true,
                    realEstateId: vm.realEstate.id,
                    advertisement: vm.advertisement});
            } else {
                $mdDialog.hide({
                    realEstateIsChosen: false,
                    advertisementDTO: vm.advertisement,
                    realEstateDTO: vm.realEstate
                });
            }

        }

        function openDialogForRealEstate(ev) {
            realEstateDialog(ev).then(function (data) {
                vm.realEstate = data;
                vm.advertisementDialog(null);
            });
        }
    }
})(angular);
