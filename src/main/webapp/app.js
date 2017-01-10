(function (angular) {
    'use strict';
    angular
        .module('poretti', ['ngRoute', 'ui.router', 'ngMaterial', 'angularFileUpload', 'ngAlertify', 'ngMap'])
        .config(function ($stateProvider, $urlRouterProvider, $httpProvider, $mdThemingProvider) {
            $mdThemingProvider
                .theme('default')
                .primaryPalette('red')
                .accentPalette('blue-grey');


            $httpProvider.interceptors.push('authInterceptor');
            $urlRouterProvider.otherwise("/home");
            $stateProvider
                .state('home', {
                    url: '/home',
                    views: {
                        'content': {
                            templateUrl: 'app/advertisements/advertisements.html',
                            controller: 'AdvertisementsCtrlAs',
                            controllerAs: 'vm'
                        },
                        'navbar': {
                            templateUrl: 'app/navbar/navbar.html',
                            controller: 'NavbarCtrlAs',
                            controllerAs: 'vm'
                        }
                    }
                });
        })
        .run(function ($rootScope, $location, sessionService, roleService) {

            var publicRoutes = ["/register", "/login", "/home", '/advertisement'];
            var adminRoutes = ["/admin"];
            var verifierRoutes = ["/verifier"];

            var routeIsIn = function (routes, currentRoute) {
                return _.find(routes, function (route) {
                    return _.startsWith(currentRoute, route);
                });
            };

            $rootScope.$on('$stateChangeSuccess', function (ev, to, toParams, from, fromParams) {
                var loggedUser = sessionService.getUser();

                if (!routeIsIn(publicRoutes, $location.url()) && !loggedUser) {
                    alertify.alert('Access denied.');
                    $location.path('/home');
                }
                else if (routeIsIn(adminRoutes, $location.url()) && !roleService.isAdmin(loggedUser)) {
                    alertify.alert('Access denied.');
                    $location.path('/home');
                }
                else if (routeIsIn(verifierRoutes, $location.url()) && !roleService.isVerifier(loggedUser)) {
                    alertify.alert('Access denied.');
                    $location.path('/home');
                }
            });
        });

}(angular));