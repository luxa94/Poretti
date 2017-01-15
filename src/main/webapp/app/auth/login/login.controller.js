(function (angular) {
    'use strict';

    angular.module('poretti')
        .controller('LoginCtrlAs', LoginCtrlAs);

    LoginCtrlAs.$inject = ['$state', 'authorizationDataService', 'sessionService', 'roleService', 'PorettiHandler'];

    function LoginCtrlAs($state, authorizationDataService, sessionService, roleService, PorettiHandler) {

        var vm = this;

        vm.user = {};
        vm.login = login;

        function login() {
            authorizationDataService.login(vm.user)
                .then(function (response) {
                    sessionService.setUser(response.data);
                    redirectToPath();
                }).catch(handleError);
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

        function handleError(error) {
            PorettiHandler.report(error);
        }

    }

})(angular);
