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
        .run(function ($rootScope, $location, sessionService, roleService) {

            var publicRoutes = ["/register", "/login"];
            var adminRoutes = ["/admin"];
            var verifierRoutes = ["/verifier"];

            var routeIsIn = function (routes, currentRoute) {
                return _.find(routes, function (route) {
                    return _.startsWith(currentRoute, route);
                });
            };

            $rootScope.$on('$stateChangeStart', function (ev, to, toParams, from, fromParams) {
                var loggedUser = sessionService.getUser();

                if (!routeIsIn(publicRoutes, $location.url()) && !loggedUser) {
                    ev.preventDefault();
                    $location.path('/login');
                }
                else if (routeIsIn(adminRoutes, $location.url()) && !roleService.isAdmin(loggedUser)) {
                    ev.preventDefault();
                    $location.path("/login");
                }
                else if (routeIsIn(verifierRoutes, $location.url()) && !roleService.isVerifier(loggedUser)) {
                    ev.preventDefault();
                    $location.path("/login");
                }
            });
        });

}(angular));