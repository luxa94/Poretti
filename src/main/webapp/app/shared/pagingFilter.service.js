(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .service('pagingFilterService', pagingFilterService);

    function pagingFilterService() {


        return {
            goByStep: goByStep,
            toExactPage: toExactPage,
            getNumberOfPages: getNumberOfPages,
            clearFilter: clearFilter,
            setUpPagingFilterParams: setUpPagingFilterParams,
            size: 5
        };

        function goByStep(currentPage, filter) {
            return this.setUpPagingFilterParams(currentPage, filter);
        }

        function toExactPage(pageNumber) {
            return this.setUpPagingFilterParams(pageNumber);
        }

        function getNumberOfPages(pages) {
            return _.range(pages);
        }

        function clearFilter(currentPage) {
            return this.setUpPagingFilterParams(currentPage);
        }

        function setUpPagingFilterParams(pageNumber, filter) {
            var params = {};
            params.size = this.size;
            params.page = pageNumber;
            if (filter) {
                _.forOwn(filter, function(value, key) {
                    params[key] = value;
                });
            }
            return params;
        }
    }
})(angular);
