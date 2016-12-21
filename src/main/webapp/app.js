(function (angular) {
    'use strict';
    angular
        .module('poretti', ['ngRoute', 'ngResource', 'ui.router', 'ngMaterial', 'angularFileUpload'])
        .config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
            $httpProvider.interceptors.push('authInterceptor');
            $urlRouterProvider.otherwise("/home");
            $stateProvider
                .state('home', {
                    url: '/home',
                    views: {
                        'body': {
                            templateUrl: 'app/advertisements/advertisements.html',
                            controller: 'AdvertisementsCtrlAs',
                            controllerAs: 'vm'
                        }
                    }
                });
        })

}(angular));