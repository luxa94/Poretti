(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .service('ownerReviewService', ownerReviewService);

    ownerReviewService.$inject = ['ownerReviewDataService'];

    function ownerReviewService(ownerReviewDataService) {

        return {
            deleteOne: deleteOne
        };

        function deleteOne(reviewId) {
            return ownerReviewDataService.delete(reviewId);
        }
    }
}(angular));
