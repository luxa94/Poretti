(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('AdvertisementDialogCtrlAs', AdvertisementDialogCtrlAs);

    AdvertisementDialogCtrlAs.$inject = ['$mdDialog', 'advertisement', 'realEstate', 'realEstateDialog', 'realEstatesDialog', 'advertisementDialog', 'alertify'];

    function AdvertisementDialogCtrlAs($mdDialog, advertisement, realEstate, realEstateDialog, realEstatesDialog, advertisementDialog, alertify) {

        var vm = this;

        vm.advertisement = advertisement;
        vm.advertisementDialog = advertisementDialog;
        vm.realEstate = realEstate;
        vm.realEstateDialog = realEstateDialog;
        vm.realEstateIsAdded = false;

        vm.cancel = cancel;
        vm.confirmResponse = confirmResponse;
        vm.hide = hide;
        vm.openDialogForRealEstate = openDialogForRealEstate;
        vm.openDialogForChoosingRealEstate = openDialogForChoosingRealEstate;
        vm.show = show;

        activate();

        function activate() {
            vm.realEstateIsAdded = !_.values(vm.realEstate).every(function (prop) {
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
            if (vm.realEstateIsAdded) {
                if (vm.realEstate.id) {
                    $mdDialog.hide({
                        realEstateIsChosen: true,
                        realEstateId: vm.realEstate.id,
                        advertisement: vm.advertisement
                    });
                } else {
                    $mdDialog.hide({
                        realEstateIsChosen: false,
                        advertisementDTO: vm.advertisement,
                        realEstateDTO: vm.realEstate
                    });
                }
            } else {
                alertify.error("You must specify real estate for this advertisement");
            }

        }

        function openDialogForRealEstate(ev) {
            realEstateDialog(ev).then(function (data) {
                vm.realEstate = data;
                vm.advertisementDialog(null);
            });
        }

        function openDialogForChoosingRealEstate(ev) {
            realEstatesDialog(ev).then(function (data) {
                vm.realEstate = data;
                vm.advertisementDialog(null);
            });
        }
    }
})(angular);
