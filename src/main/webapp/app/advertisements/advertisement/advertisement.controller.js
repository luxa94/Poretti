(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('AdvertisementCtrlAs', AdvertisementCtrlAs);

    AdvertisementCtrlAs.$inject = ['$q', '$stateParams', '$state', 'dialogService',
        'advertisementService', 'sessionService', 'userService', 'companyService',
        'advertisementReviewDataService', 'PorettiHandler', 'dateHelper'];

    function AdvertisementCtrlAs($q, $stateParams, $state, dialogService,
                                 advertisementService, sessionService, userService, companyService,
                                 advertisementReviewDataService, PorettiHandler, dateHelper) {

        var vm = this;

        vm.userId = sessionService.getUser() ? sessionService.getUser().id : -1;
        vm.advertisement = {};
        vm.canAdd = false;
        vm.newReview = {};
        vm.newReport = {};

        vm.createReview = createReview;
        vm.createReport = createReport;
        vm.deleteReview = deleteReview;
        vm.goToProfileOf = goToProfileOf;
        vm.openDialogForReview = openDialogForReview;
        vm.openDialogForReport = openDialogForReport;
        vm.markAsDone = markAsDone;

        activate();

        function activate() {
            var advertisementId = $stateParams.id;
            findAdvertisement(advertisementId)
                .then(findReviews)
                .then(findReports)
                .then(determineIfCanAdd)
                .catch(handleError)
        }

        function findAdvertisement(advertisementId) {
            return advertisementService.findOne(advertisementId)
                .then(function (data) {
                    vm.advertisement = data;
                    vm.advertisement.announcedOn = dateHelper.format(vm.advertisement.announcedOn);
                    vm.advertisement.editedOn = dateHelper.format(vm.advertisement.editedOn);
                    vm.advertisement.endsOn = dateHelper.format(vm.advertisement.endsOn);
                }).catch(function(error) {
                    vm.advertisement = undefined;
                    return $q.reject("It seems like this advertisement has left the building");
                });
        }

        function findReviews() {
            return advertisementService.findReviews(vm.advertisement.id)
                .then(function (data) {
                    vm.advertisement.reviews = data;
                    vm.advertisement.reviews = advertisementService.reviewCanBeErased(vm.advertisement.reviews, sessionService.getUser())
                });
        }

        function findReports() {
            return advertisementService.findReports(vm.advertisement.id)
                .then(function (data) {
                    vm.advertisement.reports = data;
                });
        }

        function determineIfCanAdd() {
            var loggedUser = sessionService.getUser();
            if (loggedUser) {
                var isAdvertiserOrOwner = loggedUser.id === vm.advertisement.advertiser.id;
                if (!isAdvertiserOrOwner) {
                    vm.canAdd = true;
                }
            } else {
                vm.canAdd = false;
            }
        }

        function markAsDone() {
            advertisementService.markAsDone(vm.advertisement)
                .then(function () {
                    activate();
                    alertify.success('Marked as done.');
                })
                .catch(function () {
                    alertify.error('Unable to process request.');
                });
        }

        function createReview() {
            advertisementService.createReview(vm.advertisement.id, vm.newReview)
                .then(findReviews)
                .catch(handleError)
        }

        function createReport() {
            advertisementService.createReport(vm.advertisement.id, vm.newReport)
                .then(findReports)
                .catch(handleError)
        }

        function deleteReview(review) {
            advertisementReviewDataService.delete(review.id)
                .then(findReviews)
                .catch(handleError);
        }

        function openDialogForReview(ev) {
            dialogService.open(ev, 'ReviewDialogCtrlAs', 'app/advertisements/dialogs/reviewDialog.html')
                .then(function (review) {
                    vm.newReview = review;
                    createReview();
                }).catch(handleError);
        }

        function openDialogForReport(ev) {
            dialogService.open(ev, 'ReportDialogCtrlAs', 'app/advertisements/dialogs/reportDialog.html')
                .then(function (report) {
                    vm.newReport = report;
                    createReport();
                }).catch(handleError);
        }

        function goToProfileOf(user) {
            userService.findOne(user.id)
                .then(function (data) {
                    $state.go('user', {id: data.id});
                })
                .catch(function (error) {
                    companyService.find(user.id)
                        .then(function (data) {
                            $state.go('company', {id: data.id});
                        })
                        .catch(handleError);
                });
        }

        function handleError(error) {
            PorettiHandler.report(error);
        }

    }
})(angular);