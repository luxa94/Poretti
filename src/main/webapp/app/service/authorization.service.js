(function (angular) {
    'use strict';
    angular
        .module('poretti')
        .service('authorizationService', ['$http', '$window', authorizationService]);

    function authorizationSrvice($http, $window) {
        var LOCAL_STORAGE_KEY = 'porettiUser';
        var LOCAL_STORAGE_INSTANCE = $window.localStorage;

        return {
            login: login,
            register: register,
            logout: logout,
            getUser: getUser,
            setUser: setUser,
            removeUser: removeUser
        };

        function login(user) {
            return $http.post("/api/login", user);
        }

        function register(user) {
            return $http.post("/api/register", user);
        }

        function logout() {
            var loggedInUser = this.getUser();
            if (loggedInUser) {
                return $http.post("/api/logout", loggedInUser.token);
            }
        }

        function getUser() {
            if (LOCAL_STORAGE_INSTANCE) {
                var loggedInUser = LOCAL_STORAGE_INSTANCE.getItem(LOCAL_STORAGE_KEY);
                if (loggedInUser) {
                    return JSON.parse(loggedInUser);
                }
            }
        }

        function setUser(user) {
            if (LOCAL_STORAGE_INSTANCE && user) {
                LOCAL_STORAGE_INSTANCE.setItem(LOCAL_STORAGE_KEY, JSON.stringify(user));
            }
        }

        function removeUser() {
            LOCAL_STORAGE_INSTANCE && LOCAL_STORAGE_INSTANCE.removeItem(LOCAL_STORAGE_KEY);
        }
    }
}(angular));

