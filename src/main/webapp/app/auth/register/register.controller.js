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
            vm.user.phoneNumbers = [];
            vm.user.contactEmails = [];
            findAllCompanies();
        }

        function findAllCompanies() {
            companyService.findAll()
                .then(companyService.populateForRegister)
                .then(function (data) {
                    vm.companies = data;
                    vm.currentDisplayingCompanies = vm.companies.length ? vm.companies[0] : [];
                }).catch(handleError);
        }

        function register() {
            authorizationDataService.register(vm.user)
                .then(function (response) {
                    vm.isRegistered = true;
                }).catch(handleError);
        }

        function getNextCompanies() {
            vm.currentDisplayingCompanies = vm.companies[++vm.currentIndex];
        }

        function getPreviousCompanies() {
            vm.currentDisplayingCompanies = vm.companies[--vm.currentIndex];
        }
        
        function handleError(error) {
            PorettiHandler.report(error);
        }
    }
})(angular);
