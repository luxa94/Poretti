(function (angular) {

    angular
        .module('poretti')
        .controller('AdvertisementsCtrlAs', AdvertisementsCtrlAs)

    AdvertisementsCtrlAs.$inject = ['$state', 'advertisementService', 'pagingFilterService']

    function AdvertisementsCtrlAs($state, advertisementService, pagingFilterService) {

        var vm = this;

        vm.advertisements = [];
        vm.activePageNumber = 0;
        vm.filter = {};
        vm.numberOfPages = -1;
        vm.clearFilters = clearFilters;
        vm.nextPage = nextPage;
        vm.previousPage = previousPage;
        vm.searchByFilters = searchByFilters;
        vm.toAdvertiserOf = toAdvertiserOf;
        vm.toAdvertisement = toAdvertisement;
        vm.toPage = toPage;

        activate();

        function activate() {
            var params = pagingFilterService.setUpPagingFilterParams(vm.activePageNumber);
            findAdvertisements(params);
        }

        function findAdvertisements(params) {
            advertisementService.findAll(params)
                .then(function (response) {
                    vm.advertisements = response.data.content;
                    vm.numberOfPages = pagingFilterService.getNumberOfPages(response.data.totalPages);
                });
        }

        function clearFilters() {
            var params = pagingFilterService.setUpPagingFilterParams(vm.activePageNumber);
            findAdvertisements(params);
        }

        function nextPage() {
            var params = pagingFilterService.pageNext(vm.activePageNumber, vm.filter);
            findAdvertisements(params);
        }

        function previousPage() {
            var params = pagingFilterService.pageBack(vm.activePageNumber, vm.filter);
            findAdvertisements(params);
        }

        function searchByFilters() {
            var params = pagingFilterService.setUpPagingFilterParams(vm.activePageNumber, vm.filter);
            findAdvertisements(params);
        }

        function toAdvertiserOf(advertisement) {
            $state.go('user', {id: advertisement.advertiser.id});
        }

        function toAdvertisement(advertisement) {
            $state.go('advertisement', {id: advertisement.id});
        }

        function toPage(number) {
            var params = pagingFilterService.toExactPage(number, vm.filter);
            findAdvertisements(params);
        }

        function handleError() {
            //TODO
        }
    }


})(angular);