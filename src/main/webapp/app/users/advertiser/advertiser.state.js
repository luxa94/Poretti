(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .config(function ($stateProvider) {
            $stateProvider
                .state('user', {
                    url: '/user/:id',
                    views: {
                        'content': {
                            templateUrl: 'app/users/advertiser/advertiser.html',
                            controller: 'AdvertiserCtrlAs',
                            controllerAs: 'vm'
                        }
                    }
                });
        });
})(angular);
