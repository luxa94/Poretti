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

    angular
        .module('poretti')
        .service('sharerrorService', function ($http) {
            return {
                send: function (exception) {
                    var e = {};
                    e.fragment = exception.config.url;
                    e.appVersion = '1.0.0';
                    e.stackTrace = exception.data.stackTrace || exception;
                    e.stackTrace = JSON.stringify(e.stackTrace, null, 2);

                    $http.post('http://localhost:9000/api/applications/poretti/events', e)
                        .then(function () {

                        })
                        .catch(function () {

                        });
                }
            }
        })
        .config(exceptionConfig);

    exceptionConfig.$inject = ['$provide'];

    function exceptionConfig($provide) {
        $provide.decorator('$exceptionHandler', extendExceptionHandler);
    }

    extendExceptionHandler.$inject = ['$delegate', '$injector'];

    function extendExceptionHandler($delegate, $injector) {
        return function(exception, cause) {
            $delegate(exception, cause);
            var sharerrorService = $injector.get('sharerrorService');
            sharerrorService.send(exception);
        };
    }

}(angular));