(function (angular) {
    'use strict';

    angular.module('poretti')
        .controller('AdminCtrlAs', AdminCtrlAs);

    AdminCtrlAs.$inject = ['$stateParams', 'userService', 'companyService', 'PorettiHandler'];

    function AdminCtrlAs($stateParams, userService, companyService, PorettiHandler) {

        var vm = this;

        vm.admin = {};
        vm.companyUser = {};
        vm.creatingCompanyUser = false;
        vm.newUser = {};
        vm.newCompany = {};
        vm.newUserRole = "";

        vm.createCompany = createCompany;
        vm.createAdminOfVerifier = createAdminOrVerifier;

        activate();
        initializeFields();

        function activate() {
            var userId = $stateParams.id;
            findOne(userId);
        }

        function initializeFields() {
            vm.newCompany.phoneNumbers = [];
            vm.newCompany.contactEmails = [];
            vm.companyUser.phoneNumbers = [];
            vm.companyUser.contactEmails = [];
        }

        function findOne(userId) {
            userService.findOne(userId)
                .then(function (data) {
                    vm.admin = data;
                }).catch(handleError);
        }

        function createCompany() {
            companyService.create(vm.newCompany, vm.companyUser)
                .then(function(response) {
                    alertify.success("Company is created");
                }).catch(handleError);
        }

        function createAdminOrVerifier() {
            debugger;
            if (vm.role === "ADMIN") {
                createAdmin();
            } else if (vm.role === "VERIFIER") {
                createVerifier();
            }
        }

        function createAdmin() {
            userService.createAdmin(vm.newUser)
                .then(function(response) {
                    alertify.success("Admin is created");
                }).catch(handleError);
        }

        function createVerifier() {
            userService.createVerifier(vm.newUser)
                .then(function(response) {
                    alertify.success("Verifier is created");
                }).catch(handleError);
        }

        function handleError(error) {
            PorettiHandler.report(error.data.message);
        }

    }
})(angular);
