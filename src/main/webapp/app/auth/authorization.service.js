(function (angular) {
    'use strict';
    angular
        .module('poretti')
        .service('authorizationService', authorizationService);

    authorizationService.$inject = ['$http'];

    function authorizationService($http) {
        var BASE_URL = "api/authentication";

        return {
            login: login,
            register: register,
            logout: logout,
        };

        function login(user) {
            return $http.post(BASE_URL + "/login", user);
        }

        function register(user) {
            return $http.post(BASE_URL + "/register", user);
        }

        function logout(loggedInUser) {
            if (loggedInUser) {
                return $http.post(BASE_URL + "/logout", loggedInUser.token);
            }
        }
    }
}(angular));

