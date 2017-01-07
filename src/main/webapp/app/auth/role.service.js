(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .service('roleService', roleService);

    roleService.$inject = [];

    function roleService() {

        var adminRoles = ["SYSTEM_ADMIN"];
        var verifierRoles = ["VERIFIER"];
        var userRoles = ["USER"];

        return {
            isAdmin: isAdmin,
            isVerifier: isVerifier,
            isUser: isUser,
        }

        function isAdmin(loggedUser) {
            if (loggedUser) {
                return _.includes(adminRoles, loggedUser.role);
            }
            return false;
        }

        function isVerifier(loggedUser) {
            return loggedUser ? _.includes(verifierRoles, loggedUser.role) : false;
        }

        function isUser(loggedUser) {
            return loggedUser ? _.includes(userRoles, loggedUser.role) : false;
        }
    }
})(angular);