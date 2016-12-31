(function (angular) {
    'use strict';
    angular
        .module('poretti', ['ngRoute', 'ngResource', 'ui.router', 'ngMaterial', 'angularFileUpload'])
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
                        'navbar' : {
                            templateUrl: 'app/navbar/navbar.html',
                            controller: 'NavbarCtrlAs',
                            controllerAs: 'vm',
                        }
                    }
                });
        })
        .run(function ($rootScope, $location, sessionService, roleService) {

            var publicRoutes = ["/register", "/login", "/home"];
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
                   // ev.preventDefault();
                    $location.path('/home');
                }
                else if (routeIsIn(adminRoutes, $location.url()) && !roleService.isAdmin(loggedUser)) {
                    //TODO replace with 404 page
                    $location.path('/home');
                }
                else if (routeIsIn(verifierRoutes, $location.url()) && !roleService.isVerifier(loggedUser)) {
                    //TODO replace with 404 page
                    $location.path('/home');
                }
            });
        });

}(angular));