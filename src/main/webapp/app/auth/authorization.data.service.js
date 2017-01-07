(function (angular) {
    'use strict';
    angular
        .module('poretti')
        .service('authorizationDataService', authorizationDataService);

    authorizationDataService.$inject = ['$http'];

    function authorizationDataService($http) {
        var BASE_URL = "api/authentication";

        function pathWithId(id) {
            return BASE_URL + '/' + id;
        }

        return {
            login: login,
            register: register,
            logout: logout,
            verifyAccount: verifyAccount,
        };

        function login(user) {
            return $http.post(BASE_URL + "/login", user);
        }

        function register(user) {
            return $http.post(BASE_URL + "/register", user);
        }

        function logout(loggedInUser) {
            if (loggedInUser) {
                return $http.delete(BASE_URL + "/logout", loggedInUser.token);
            }
        }

        function verifyAccount(userId) {
            return $http.put(pathWithId(userId) + "/verify");
        }
    }
}(angular));

