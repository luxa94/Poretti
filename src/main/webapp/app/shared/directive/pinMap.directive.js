(function (angular) {

    'use strict';

    angular.module('poretti')
        .directive('pinMap', pinMap);

    function pinMap() {
        return {
            scope: {
                location: "="
            },
            restrict: "E",
            templateUrl: "app/shared/directive/pinMap.html",
            controller: 'pinMapController',
            controllerAs: 'vm'
        }
    }

}(angular));