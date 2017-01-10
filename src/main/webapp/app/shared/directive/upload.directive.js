(function (angular) {

    'use strict';

    angular.module('poretti')
        .directive('imageUpload', imageUpload);

    function imageUpload() {
        return {
            scope: {
                object: "="
            },
            restrict: "E",
            templateUrl: "app/shared/directive/upload.html",
            controller: 'uploadController',
            controllerAs: 'vm'
        }
    }

}(angular));