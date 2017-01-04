(function (angular) {

    angular
        .module('poretti')
        .controller('AdvertisementsCtrlAs', AdvertisementsCtrlAs)

    AdvertisementsCtrlAs.$inject = ['$q', '$state', '$mdDialog', 'advertisementService', 'realEstateService', 'alertify']

    function AdvertisementsCtrlAs($q, $state, $mdDialog, advertisementService, realEstateService, alertify) {

        var vm = this;
        vm.advertisements = [];
        vm.activePageNumber = 0;
        vm.size = 5;
        vm.numberOfPages = -1;
        vm.hidden = false;
        vm.isOpen = false;
        vm.filter = {};
        vm.toAdvertiserOf = toAdvertiserOf;
        vm.toAdvertisement = toAdvertisement;
        vm.nextPage = nextPage;
        vm.previousPage = previousPage;
        vm.toPage = toPage;
        vm.getNumberOfPages = getNumberOfPages;
        vm.searchByFilters = searchByFilters;
        vm.clearFilters = clearFilters;

        activate();

        function activate() {
            var params = {};
            params.page = vm.activePageNumber;
            params.size = vm.size;
            findAdvertisements(params);
        }

        function toAdvertiserOf(advertisement) {
            $state.go('user', {id: advertisement.advertiser.id});
        }

        function toAdvertisement(advertisement) {
            $state.go('advertisement', {id: advertisement.id});
        }

        function nextPage() {
            ++vm.activePageNumber;
            var params = {};
            params.page = vm.activePageNumber;
            params.size = vm.size;
            findAdvertisements(params);
        }

        function previousPage() {
            --vm.activePageNumber;
            var params = {};
            params.page = vm.activePageNumber;
            params.size = vm.size;
            findAdvertisements(params);
        }

        function findAdvertisements(params) {
            advertisementService.find(params).then(function (response) {
                vm.advertisements = response.data.content;
                vm.numberOfPages = Math.ceil(response.data.totalElements / response.data.size);
            }).catch(function (error) {
                console.log(error);
            });
        }

        function toPage(number) {
            vm.activePageNumber = number;
            var params = {};
            params.page = vm.activePageNumber;
            params.size = vm.size;
            findAdvertisements(params);
        }

        function getNumberOfPages() {
            return _.range(vm.numberOfPages);
        }

        function searchByFilters() {
            findAdvertisements(vm.filter);
        }

        function clearFilters() {
            vm.filter = {};
            var params = {};
            params.page = vm.activePageNumber;
            params.size = vm.size;
            findAdvertisements(params);
        }


    }


})(angular);