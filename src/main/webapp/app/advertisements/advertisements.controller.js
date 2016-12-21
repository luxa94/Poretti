(function (angular) {

    angular
        .module('poretti')
        .controller('AdvertisementsCtrlAs', AdvertisementsCtrlAs)

    AdvertisementsCtrlAs.$inject = ['advertisementService']

    function AdvertisementsCtrlAs(advertisementService) {

        var vm = this;
        vm.heading = "Advertisements";
        vm.advertisements = [];

        activate();

        function activate() {
            advertisementService.find().then(function (response) {
                vm.advertisements = response.data.content;
            })
        }
    }


})(angular);