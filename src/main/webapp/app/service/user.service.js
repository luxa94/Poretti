(function (angular) {
    'use strict';
    angular
        .module('poretti')
        .service('userService', ['$http', userService]);

    function userService($http) {
        var BASE_URL = '/api/users';

        function pathWithId(id) {
            return BASE_URL + '/' + id;
        }

        return {
            createAdmin: createAdmin,
            createVerifier: createVerifier,
            findOne: findOne,
            edit: edit,
            findAdvertisements: findAdvertisements,
            findRealEstates: findRealEstates,
            findMemberships: findMemberships,
            createReview: createReview,
            findReviews: findReviews,
        };

        function createAdmin(registerDTO) {
            var adminPath = BASE_URL + "/createSysAdmin";
            return $http.post(adminPath, registerDTO);
        }

        function createVerifier(registerDTO) {
            var verifierPath = BASE_URL + "/createVerifier";
            return $http.post(verifierPath, registerDTO);
        }

        function findOne(id) {
            return $http.get(pathWithId(id));
        }

        function edit(id, userDTO) {
            return $http.put(pathWithId(id), userDTO);
        }

        function findAdvertisements(id, params) {
            var advertisementsPath = pathWithId(id) + '/advertisements'
            return $http.get(advertisementsPath, {params: params});
        }

        function findRealEstates(id) {
            var realEstatesPath = pathWithId(id) + '/realEstates';
            return $http.get(realEstatesPath);
        }

        function findMemberships(id) {
            var membershipsPath = pathWithId(id) + '/memberships';
            return $http.get(membershipsPath);
        }

        function createReview(id, reviewDTO) {
            var reviewsPath = pathWithId(id) + '/reviews';
            return $http.post(reviewsPath, reviewDTO);
        }

        function findReviews(id) {
            var reviewsPath = pathWithId(id) + '/reviews';
            return $http.get(reviewsPath);
        }
    }
}(angular));


