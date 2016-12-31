(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('NavbarCtrlAs', NavbarCtrlAs);

    NavbarCtrlAs.$inject = ['$state', 'sessionService', 'authorizationService', 'roleService', 'userService'];

    function NavbarCtrlAs($state, sessionService, authorizationService, roleService, userService) {

        var vm = this;

        vm.loggedUser = {};
        vm.renderUIForLoggedUser = false;
        vm.login = login;
        vm.register = register;
        vm.logout = logout;
        vm.goToProfile = goToProfile;


        activate();

        function activate() {
            var sessionUser = sessionService.getUser();
            if (sessionUser) {
                userService.findOne(sessionUser.id).then(function(response) {
                    vm.loggedUser = response.data;
                }).catch(function(error) {
                    console.log(error);
                })
            }
            vm.renderUIForLoggedUser = sessionUser ? true : false;
        }

        function login() {
            $state.go('login');
        }

        function register() {
            $state.go('register');
        }

        function logout() {
            var sessionUser = sessionService.getUser();
            authorizationService.logout(sessionUser).then(function(response) {
                debugger;
                sessionService.removeUser();
                $state.go('home', {}, {reload: true});
            }).catch(function(error) {
                console.log(error);
            });

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


    }
})(angular);
