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

            var publicRoutes = ["/register", "/login", "/home", "/advertisement", "/user", "/company"];
            var restrictedRoutesForLoggedUser = ["/register", "/login"];
            var adminRoutes = ["/admin"];
            var verifierRoutes = ["/verifier"];

            var routeIsIn = function (routes, currentRoute) {
                return _.find(routes, function (route) {
                    return _.startsWith(currentRoute, route);
                });
            };

            $rootScope.$on('$stateChangeSuccess', function (ev, to, toParams, from, fromParams) {
                var loggedUser = sessionService.getUser();

                if (routeIsIn(restrictedRoutesForLoggedUser, $location.url()) && loggedUser) {
                    debugger;
                    if (fromParams){
                        from.url = from.url.replace(/:id/gm,fromParams.id);
                    }
                    $location.path(from.url);
                }

                if (!routeIsIn(publicRoutes, $location.url()) && !loggedUser) {
                    $location.path('/home');
                }
                else if (routeIsIn(adminRoutes, $location.url()) && !roleService.isAdmin(loggedUser)) {
                    $location.path('/home');
                }
                else if (routeIsIn(verifierRoutes, $location.url()) && !roleService.isVerifier(loggedUser)) {
                    $location.path('/home');
                }
            });
        });

}(angular));