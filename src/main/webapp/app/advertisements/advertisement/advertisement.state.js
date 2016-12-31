(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .config(function($stateProvider) {
            $stateProvider
                .state('advertisement', {
                    url: '/advertisement/:id',
                    views: {
                        'content' : {
                            templateUrl: 'app/advertisements/advertisement/advertisement.html',
                            controller: 'AdvertisementCtrlAs',
                            controllerAs: 'vm',
                        },
                        'navbar' : {
                            templateUrl: 'app/navbar/navbar.html',
                            controller: 'NavbarCtrlAs',
                            controllerAs: 'vm',
                        }
                    }
                })
        });
})(angular);
