(function (angular) {

    'use strict';

    angular.module('poretti')
        .controller('uploadController', ['$scope', 'FileUploader', uploadController]);

    function uploadController($scope, FileUploader) {
        var vm = this;

        vm.object = $scope.object;
        vm.images = [];


        var uploader = vm.uploader = new FileUploader({
            url: '/api/images'
        });

        uploader.onWhenAddingFileFailed = function (item /*{File|FileLikeObject}*/, filter, options) {
            console.info('onWhenAddingFileFailed', item, filter, options);
        };
        uploader.onAfterAddingFile = function (fileItem) {
            console.info('onAfterAddingFile', fileItem);
            vm.images[0] = fileItem;
            vm.images[0].upload();
        };
        uploader.onAfterAddingAll = function (addedFileItems) {
            console.info('onAfterAddingAll', addedFileItems);
        };
        uploader.onBeforeUploadItem = function (item) {
            console.info('onBeforeUploadItem', item);
        };
        uploader.onProgressItem = function (fileItem, progress) {
            console.info('onProgressItem', fileItem, progress);
        };
        uploader.onProgressAll = function (progress) {
            console.info('onProgressAll', progress);
        };
        uploader.onSuccessItem = function (fileItem, response, status, headers) {
            console.info('onSuccessItem', fileItem, response, status, headers);
        };
        uploader.onErrorItem = function (fileItem, response, status, headers) {
            console.info('onErrorItem', fileItem, response, status, headers);
            delete vm.object.imageUrl;
        };
        uploader.onCancelItem = function (fileItem, response, status, headers) {
            console.info('onCancelItem', fileItem, response, status, headers);
            delete vm.object.imageUrl;
        };
        uploader.onCompleteItem = function (fileItem, response, status, headers) {
            console.info('onCompleteItem', fileItem, response, status, headers);
            vm.object.imageUrl = response;
        };
        uploader.onCompleteAll = function () {
            console.info('onCompleteAll');
        };

    }

}(angular));