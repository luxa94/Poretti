(function(angular){
    'use strict';

    angular.module('poretti')
        .controller('AdminCtrlAs', AdminCtrlAs)

    AdminCtrlAs.$inject = ['userService'];

    function AdminCtrlAs(userService) {
        var vm = this;

        vm.admin = {};
        vm.nadmin = {};
        vm.verifier = {};
        vm.createAdmin = createAdmin;
        vm.createVerifier = createVerifier;

        activate();

        function activate() {
            userService.findOne(1)
                .then(function(response) {
                    vm.admin = response.data;
                })
        }

        function createAdmin () {
            userService.createAdmin(vm.nadmin).then(function(response) {
                console.log(response.data);
            }).catch(function(response) {
                console.log(response.status);
            })
        }

        function createVerifier() {
            userService.createVerifier(vm.verifier).then(function(response) {
                console.log(response.data);
            }).catch(function(response) {
                console.log(response.status);
            })
        }
    }
})(angular);
