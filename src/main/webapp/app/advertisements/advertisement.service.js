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
            findReviews: findReviews,
            findReports: findReports,
            findReported: findReported,
            createReview: createReview,
            createReport: createReport,
            determineIfCanBeErased: determineIfCanBeErased,
            changeStatus: changeStatus,
            actionBasedOnStatus: actionBasedOnStatus,
            edit: edit,
            createAdvertisementAndRealEstate: createAdvertisementAndRealEstate
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

        function changeStatus(advertisement) {
            if (advertisement.status === "INVALID" || advertisement.status === "PENDING_APPROVAL") {
                return approve(advertisement.id);
            }else if (advertisement.status === "ACTIVE") {
                return invalidate(advertisement.id);
            }
        }

        function approve(advertisementId) {
            return advertisementDataService.approve(advertisementId)
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

        function handleError(error) {
            //TODO PorettiError
            console.log(error);
        }

        function orderReviewsByDate(data) {
            return _.orderBy(data, ['editedOn'], ['desc']);
        }

        function determineIfCanBeErased() {
            //TODO
        }


    }

})(angular);