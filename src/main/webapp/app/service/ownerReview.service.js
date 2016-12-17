(function (angular) {
    'use strict'

    angular
        .module('poretti')
        .service(ownerReviewService, ['$http', ownerReviewService])

    function ownerReviewService($http) {
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
