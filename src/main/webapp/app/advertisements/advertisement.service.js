(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .service('advertisementService', advertisementService);

    advertisementService.$inject = ['advertisementDataService'];

    function advertisementService(advertisementDataService) {

        return {
            findAll: findAll,
            findOne: findOne,
            markAsDone: markAsDone,
            deleteOne: deleteOne,
            findReviews: findReviews,
            findReports: findReports,
            findReported: findReported,
            createReview: createReview,
            createReport: createReport,
            actionBasedOnStatus: actionBasedOnStatus,
            edit: edit,
            createAdvertisementAndRealEstate: createAdvertisementAndRealEstate,
            reviewCanBeErased: reviewCanBeErased,
            getLocation: getLocation,
            approve: approve,
            invalidate: invalidate
        };

        function findAll(params) {
            return advertisementDataService.find(params)
                .then(findAdvertisementsSuccess);
        }

        function findAdvertisementsSuccess(response) {
            return response;
        }

        function findOne(id) {
            return advertisementDataService.findOne(id)
                .then(findAdvertisementSuccess);
        }

        function findAdvertisementSuccess(response) {
            return response.data;
        }

        function findReviews(advertisementId) {
            return advertisementDataService.findReviews(advertisementId)
                .then(findReviewsSuccess);
        }

        function findReviewsSuccess(response) {
            var reviews = orderReviewsByDate(response.data);
            return reviews;
        }

        function findReports(advertisementId) {
            return advertisementDataService.findReports(advertisementId)
                .then(findReportsSuccess);
        }

        function findReportsSuccess(response) {
            return response.data;
        }

        function createReview(advertisementId, reviewDTO) {
            return advertisementDataService.createReview(advertisementId, reviewDTO);
        }

        function createReport(advertisementId, reportDTO) {
            return advertisementDataService.createReport(advertisementId, reportDTO);
        }

        function findReported() {
            return advertisementDataService.findReported()
                .then(findReportedSuccess);
        }

        function findReportedSuccess(response) {
            return response.data;
        }

        function approve(advertisementId) {
            return advertisementDataService.approve(advertisementId);
        }

        function invalidate(advertisementId){
            return advertisementDataService.invalidate(advertisementId);
        }

        function actionBasedOnStatus(advertisement) {
            if (advertisement.status === "INVALID" || advertisement.status === "PENDING_APPROVAL") {
                return "APPROVE";
            } else if(advertisement.status === "ACTIVE") {
                return "INVALIDATE";
            }
            //TODO what to do with DONE status?
        }

        function edit(advertisement) {
            return advertisementDataService.edit(advertisement.id, advertisement);
        }

        function createAdvertisementAndRealEstate(advertisementRealEstate) {
            return advertisementDataService.create(advertisementRealEstate);
        }

        function orderReviewsByDate(data) {
            return _.orderBy(data, ['editedOn'], ['desc']);
        }

        function reviewCanBeErased(reviews, loggedUser) {
            if (loggedUser && reviews.length) {
                return _.forEach(reviews, function (review) {
                    review.canBeErased = review.author.id === loggedUser.id;
                });
            }
            return reviews;
        }

        function deleteOne(advertisement) {
            return advertisementDataService.delete(advertisement.id);
        }

        function markAsDone(advertisement) {
            return advertisementDataService.done(advertisement.id);
        }

        function getLocation(advertisement) {
            var l = advertisement.realEstate.location;
            return l.hasLatLong ? (l.latitude + ', ' + l.longitude) : ((l.street || '') + ' ' + (l.streetNumber || '') + ' ' + l.city + ' ' + (l.state || ''));
        }

    }

})(angular);