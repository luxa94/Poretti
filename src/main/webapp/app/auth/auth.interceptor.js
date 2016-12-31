(function (angular) {
    'use strict';

    angular.module('poretti')
        .service('authInterceptor', authInterceptor)

    authInterceptor.$inject = ['$q', '$location', 'sessionService'];

    function authInterceptor($q, $location, sessionService) {

        return {
            request: request,
            responseError: responseError
        };

        function request(config) {
            var currentUser = sessionService.getUser();
            if (currentUser) {
                var authToken = currentUser.token;
                if (authToken) {
                    config.headers["X-AUTH-TOKEN"] = authToken;
                }
            }
            return config;
        }

        function responseError(response) {
            return $q.reject(response);
        }
    }
})(angular);
