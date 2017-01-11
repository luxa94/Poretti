(function (angular) {
    'use strict';

    angular.module('poretti')
        .controller('RegisterCtrlAs', RegisterCtrlAs);

    RegisterCtrlAs.$inject = ['authorizationDataService', 'companyService', 'PorettiHandler'];

    function RegisterCtrlAs(authorizationDataService, companyService, PorettiHandler) {

        var vm = this;

        vm.companies = [];
        vm.currentDisplayingCompanies = [];
        vm.currentIndex = 0;
        vm.isRegistered = false;
        vm.isRegisteringForCompany = false;
        vm.user = {};

        vm.getNextCompanies = getNextCompanies;
        vm.getPreviousCompanies = getPreviousCompanies;
        vm.register = register;

        activate();

        function activate() {
            findAllCompanies();
        }

        function findAllCompanies() {
            companyService.findAll()
                .then(companyService.populateForRegister)
                .then(function (data) {
                    vm.companies = data;
                    vm.currentDisplayingCompanies = vm.companies[0];
                }).catch(PorettiHandler.report());
        }

        function register() {
            authorizationDataService.register(vm.user)
                .then(function (response) {
                    vm.isRegistered = true;
                }).catch(PorettiHandler.report());
        }

        function getNextCompanies() {
            vm.currentDisplayingCompanies = vm.companies[++vm.currentIndex];
        }

        function getPreviousCompanies() {
            vm.currentDisplayingCompanies = vm.companies[--vm.currentIndex];
        }
    }
})(angular);
