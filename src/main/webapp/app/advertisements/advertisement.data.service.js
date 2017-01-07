(function () {
    'use strict';
    angular
        .module('poretti')
        .service('advertisementDataService', ['$http', advertisementDataService]);

    function advertisementDataService($http) {
        var BASE_URL = '/api/advertisements';

        function pathWithId(id) {
            return BASE_URL + '/' + id;
        }

        return {
            find: findAll,
            findOne: findOne,
            findReported: findReported,
            create: createOne,
            edit: edit,
            delete: deleteOne,
            invalidate: invalidate,
            approve: approve,
            done: done,
            createReport: createReport,
            findReports: findReports,
            createReview: createReview,
            findReviews: findReviews
        };

        function findAll(params) {
            return $http.get(BASE_URL, {params: params});
        }

        function findOne(id) {
            return $http.get(pathWithId(id));
        }

        function findReported() {
            return $http.get(BASE_URL + '/reported');
        }

        function createOne(advertisementRealEstateDTO) {
            return $http.post(BASE_URL, advertisementRealEstateDTO);
        }

        function edit(id, advertisementDTO) {
            return $http.put(pathWithId(id), advertisementDTO);
        }

        function deleteOne(id) {
            return $http.delete(pathWithId(id));
        }

        function invalidate(id) {
            return $http.put(pathWithId(id) + '/invalidate');
        }

        function approve(id) {
            return $http.put(pathWithId(id) + '/approve');
        }

        function done(id) {
            return $http.put(pathWithId(id) + '/done');
        }

        function createReport(id, reportDTO) {
            return $http.post(pathWithId(id) + '/reports', reportDTO);
        }

        function findReports(id) {
            return $http.get(pathWithId(id) + '/reports');
        }

        function createReview(id, reviewDTO) {
            return $http.post(pathWithId(id) + '/reviews', reviewDTO);
        }

        function findReviews(id) {
            return $http.get(pathWithId(id) + '/reviews');
        }
    }
}());

