(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .config(function($stateProvider) {
            $stateProvider
                .state('advertisement', {
                    url: '/advertisement/:id',
                    views: {
                        'body' : {
                            templateUrl: 'app/advertisements/advertisement/advertisement.html',
                            controller: 'AdvertisementCtrlAs',
                            controllerAs: 'vm',
                        }
                    }
                })
        });
})(angular);
