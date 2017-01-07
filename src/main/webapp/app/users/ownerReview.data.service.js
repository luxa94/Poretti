(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .service('ownerReviewDataService', ['$http', ownerReviewDataService]);

    function ownerReviewDataService($http) {
        var BASE_URL = "/api/ownerReviews";

        function pathWithId(id) {
            return BASE_URL + "/" + id;
        }

        return {
            delete: deleteOne
        };

        function deleteOne(id) {
            return $http.delete(pathWithId(id));
        }
    }
}(angular));
