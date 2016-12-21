(function (angular) {
    'use strict';

    angular.module('poretti')
        .service('authInterceptor', authInterceptor)

    authInterceptor.$inject = ['sessionService'];

    function authInterceptor(sessionService) {

        return {
            request: request,
            responseError: responseError
        };

        function request(config) {
            var currentUser = sessionService.getUser();
            var authToken = currentUser.token;
            if (authToken) {
                config.headers["X-AUTH-TOKEN"] = authToken;
            }
            return config;
        }

        function responseError(response) {
            if (response.status === 401) {
                console.log('401');
            }

            return response;
        }
    }
})(angular);
