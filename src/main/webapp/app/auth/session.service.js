(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .service('sessionService', sessionService)

    sessionService.$inject = ['$window', '$q'];

    function sessionService($window, $q) {
        var LOCAL_STORAGE_KEY = 'porettiUser';
        var LOCAL_STORAGE_INSTANCE = $window.localStorage;

        return {
            getUser: getUser,
            setUser: setUser,
            removeUser: removeUser,
        };

        function getUser() {
            var loggedInUser = {};
            if (LOCAL_STORAGE_INSTANCE) {
                loggedInUser = LOCAL_STORAGE_INSTANCE.getItem(LOCAL_STORAGE_KEY);
                if (loggedInUser) {
                    return JSON.parse(loggedInUser);
                }
            }
            return loggedInUser;
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
})(angular);
