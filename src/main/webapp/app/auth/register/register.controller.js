(function (angular) {
    'use strict';

    angular.module('poretti')
        .controller('RegisterCtrlAs', RegisterCtrlAs);

    RegisterCtrlAs.$inject = ['authorizationService', '$state']

    function RegisterCtrlAs(authorizationService, $state) {

        var vm = this;

        vm.user = {};
        vm.status = false;
        vm.register = register;

        function register() {
            authorizationService.register(vm.user).then(function(response) {
                vm.status = true;
            }).catch(function(response) {
                console.log("Ooops, something went wrong :(...Kidding, everything is ok, it's just some feature bug.")
            })
        }
    }
})(angular);
