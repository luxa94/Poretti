(function (angular) {
    'use strict';

    angular.module('poretti')
        .controller('AdminCtrlAs', AdminCtrlAs)

    AdminCtrlAs.$inject = ['$stateParams', 'userService', 'companyService'];

    function AdminCtrlAs($stateParams, userService, companyService) {
        var vm = this;

        vm.admin = {};
        vm.newUser = {};
        vm.newCompany = {};
        vm.companyUser = {};
        vm.newCompany.phoneNumbers= [];
        vm.newCompany.contactEmails = [];
        vm.companyUser.phoneNumbers = [];
        vm.companyUser.contactEmails = [];
        vm.newUserRole = "";
        vm.createCompany = createCompany;
        vm.creatingCompanyUser = false;
        vm.createAdminOfVerifier = createAdminOrVerifier;

        activate();

        function activate() {
            userService.findOne($stateParams.id)
                .then(function (response) {
                    vm.admin = response.data;
                })
        }

        function createCompany() {
            var registerCompanyDTO = {};
            registerCompanyDTO.company = vm.newCompany;
            registerCompanyDTO.user = vm.companyUser;
            companyService.create(registerCompanyDTO).then(function(response) {
                vm.newCompany = {};
                vm.companyUser = {};
            }).catch(function(error) {
                console.log(error);
            })
        }

        function createAdminOrVerifier() {
            if (vm.role === "ADMIN") {
                createAdmin();
            } else if (vm.role === "VERIFIER") {
                createVerifier();
            }
        }

        function createAdmin() {
            userService.createAdmin(vm.newUser).then(function (response) {
                console.log(response.data);
            }).catch(function (response) {
                console.log(response.status);
            })
        }

        function createVerifier() {
            userService.createVerifier(vm.newUser).then(function (response) {
                console.log(response.data);
            }).catch(function (response) {
                console.log(response.status);
            })
        }

    }
})(angular);
