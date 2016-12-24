(function (angular) {

    angular
        .module('poretti')
        .controller('AdvertisementsCtrlAs', AdvertisementsCtrlAs)

    AdvertisementsCtrlAs.$inject = ['$state', 'advertisementService']

    function AdvertisementsCtrlAs($state, advertisementService) {

        var vm = this;
        vm.heading = "Advertisements";
        vm.advertisements = [];
        vm.toAdvertiserOf = toAdvertiserOf;
        vm.toAdvertisement = toAdvertisement;

        activate();

        function activate() {
            advertisementService.find().then(function (response) {
                vm.advertisements = response.data.content;
            })
        }

        function toAdvertiserOf(advertisement) {
            $state.go('user', {id: advertisement.advertiser.id});
        }

        function toAdvertisement(advertisement) {
            $state.go('advertisement', {id: advertisement.id});
        }
    }


})(angular);