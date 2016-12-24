(function (angualar) {
    'use strict';

    angular
        .module('poretti')
        .controller('AdvertiserCtrlAs', AdvertiserCtrlAs)

    AdvertiserCtrlAs.$inject = ['$stateParams','userService', 'sessionService', 'ownerReviewService'];

    function AdvertiserCtrlAs($stateParams, userService, sessionService, ownerReviewService) {

        var vm = this;

        vm.user = {};
        vm.advertisements = [];
        vm.userToEdit = {};
        vm.newReview = {};
        vm.reviews = [];
        vm.canEdit = false;
        vm.edit = edit;
        vm.createReview = createReview;
        vm.deleteReview = deleteReview;

        activate();

        function activate() {
            userService.findOne($stateParams.id).then(function (response) {
                vm.user = response.data;
                var loggedInUser = sessionService.getUser();
                if (vm.user.id === loggedInUser.id) {
                    vm.canEdit = true;
                }
                return userService.findAdvertisements(vm.user.id);
            }).then(function(response) {
                vm.advertisements = response.data.content;
                return userService.findReviews(vm.user.id);
            }).then(function(response) {
                vm.reviews = response.data;
                var loggedInUser = sessionService.getUser();
                vm.reviews[0].author.id= 1;
                angular.forEach(vm.reviews, function(review, index) {
                    if (review.author.id === loggedInUser.id) {
                        review.canBeErased = true;
                    }
                });

            }).catch(function (error) {
                console.log(error);
            });
        }

        function edit() {
            userService.edit(vm.user.id, vm.userToEdit).then(function (response) {
                vm.user = response.data;
            }).catch(function (error) {
                console.log(error);
            })
        }

        function createReview() {
            userService.createReview(vm.user.id, vm.review).then(function (response) {
                return userService.findReviews(vm.user.id);
            }).then(function(response) {
                vm.reviews = response.data;
            }).catch(function (error) {
                console.log(error);
            });
        }

        function deleteReview(review) {
            ownerReviewService.delete(review.id).then(function(response) {
                console.log("Review with " + review.id + "is deleted");
                var index = vm.reviews.indexOf(review);
                vm.reviews.splice(index);
            }).catch(function(error) {
                console.log(error);
            })
        }
    }
})(angular);
