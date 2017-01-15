(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('NavbarCtrlAs', NavbarCtrlAs);

    NavbarCtrlAs.$inject = ['$state', 'sessionService', 'authorizationDataService', 'roleService', 'userService', 'PorettiHandler'];

    function NavbarCtrlAs($state, sessionService, authorizationDataService, roleService, userService, PorettiHandler) {

        var vm = this;

        vm.loggedUser = {};
        vm.renderUIForLoggedUser = false;
        vm.goToProfile = goToProfile;
        vm.login = login;
        vm.logout = logout;
        vm.register = register;
        vm.toHome = toHome;

        activate();

        function activate() {
            var sessionUser = sessionService.getUser();
            findLoggedUser(sessionUser);
        }

        function findLoggedUser(sessionUser) {
            if (sessionUser) {
                userService.findOne(sessionUser.id)
                    .then(function(data) {
                        vm.loggedUser = data;
                    }).catch(handleError);
                vm.renderUIForLoggedUser = true;
            }
        }

        function goToProfile() {
            if (vm.loggedUser) {
                if (roleService.isAdmin(vm.loggedUser)) {
                    $state.go('admin', {id: vm.loggedUser.id});
                } else if (roleService.isVerifier(vm.loggedUser)) {
                    $state.go('verifier', {id: vm.loggedUser.id});
                } else if (roleService.isUser(vm.loggedUser)) {
                    $state.go('user', {id: vm.loggedUser.id});
                }
            }
        }

        function login() {
            $state.go('login');
        }

        function logout() {
            var sessionUser = sessionService.getUser();
            authorizationDataService.logout(sessionUser)
                .then(function(response) {
                    sessionService.removeUser();
                    $state.go('home', {}, {reload: true});
                }).catch(handleError);

        }

        function register() {
            $state.go('register');
        }

        function toHome() {
            $state.go('home');
        }

        function handleError(error) {
            PorettiHandler.report(error);
        }

    }
})(angular);
