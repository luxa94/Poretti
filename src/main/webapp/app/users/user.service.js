(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .service('userService', userService);

    userService.$inject = ['userDataService'];

    function userService(userDataService) {

        return {
            findOne: findOne,
            createAdmin: createAdmin,
            createVerifier: createVerifier,
            findAdvertisements: findAdvertisements,
            findRealEstates: findRealEstates,
            findReviews: findReviews,
            reviewCanBeErased: reviewCanBeErased,
            edit: edit,
            createReview: createReview,
            findMemberships: findMemberships,
        };

        function findOne(userId) {
            return userDataService.findOne(userId)
                .then(findOneSuccess);
        }

        function findOneSuccess(response) {
            return response.data;
        }

        function createAdmin(userDTO) {
            return userDataService.createAdmin(userDTO);
        }

        function createVerifier(userDTO) {
            return userDataService.createVerifier(userDTO);
        }

        function findAdvertisements(userId) {
            return userDataService.findAdvertisements(userId);
        }

        function findRealEstates(userId) {
            return userDataService.findRealEstates(userId)
                .then(findRealEstatesSuccess)
        }

        function findRealEstatesSuccess(response) {
            return response.data;
        }

        function findReviews(userId) {
            return userDataService.findReviews(userId)
                .then(findReviewsSuccess);
        }

        function findReviewsSuccess(response) {
            var reviews = orderReviewsByDate(response.data);
            return reviews;
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

        function edit(userId, editedUser) {
            return userDataService.edit(userId, editedUser)
                .then(editSuccess);
        }

        function editSuccess(response) {
            return response.data;
        }

        function createReview(userId, reviewDTO) {
            return userDataService.createReview(userId, reviewDTO)
                .then(createReviewSuccess);
        }

        function createReviewSuccess(response) {
            return response.data;
        }

        function findMemberships(userId) {
            return userDataService.findMemberships(userId)
                .then(findMembershipsSuccess);
        }

        function findMembershipsSuccess(response) {
            return response.data;
        }
    }
})(angular);