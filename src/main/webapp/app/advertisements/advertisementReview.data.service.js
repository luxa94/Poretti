(function (angular) {
    'use strict';
    angular
        .module('poretti')
        .service('advertisementReviewDataService', ['$http', advertisementReviewDataService]);

    function advertisementReviewDataService($http) {
        var BASE_URL = '/api/advertisementReviews';

        return {
            delete: deleteOne
        };

        function deleteOne(id) {
            return $http.delete(BASE_URL + '/' + id);
        }
    }
}(angular));

