(function (angular) {
    'use strict';

    angular.module('poretti')
        .controller('LoginCtrlAs', LoginCtrlAs);

    LoginCtrlAs.$inject = ['$state', 'authorizationService', 'sessionService'];

    function LoginCtrlAs($state, authorizationService, sessionService) {

        var vm = this;

        vm.user = {};
        vm.login = login;

        function login() {
            authorizationService.login(vm.user).then(function (response) {
                sessionService.setUser(response.data);
                console.log("You're successfully logged in" + response.data.username);
                $state.go('home');
            }).catch(function (reject) {
                console.log("Bad credentials");
            });
        }
    }

})(angular);
