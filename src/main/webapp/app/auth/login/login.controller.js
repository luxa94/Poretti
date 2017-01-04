(function (angular) {
    'use strict';

    angular.module('poretti')
        .controller('LoginCtrlAs', LoginCtrlAs);

    LoginCtrlAs.$inject = ['$q', '$state', 'authorizationService', 'sessionService', 'roleService', 'alertify'];

    function LoginCtrlAs($q, $state, authorizationService, sessionService, roleService, alertify) {

        var vm = this;

        vm.user = {};
        vm.login = login;

        function login() {
            authorizationService.login(vm.user).then(function (response) {
                sessionService.setUser(response.data);
                redirectToPath();
            }).catch(function (error) {
                alertify.delay(5000).error("Username/password wrong.")
            });
        }

        function redirectToPath() {
            var loggedUser = sessionService.getUser();
            if (roleService.isAdmin(loggedUser)) {
                $state.go('admin', {id: loggedUser.id});
            } else if (roleService.isVerifier(loggedUser)) {
                $state.go('verifier', {id: loggedUser.id});
            } else if (roleService.isUser(loggedUser)) {
                $state.go('home');
            }
        }
    }

})(angular);
