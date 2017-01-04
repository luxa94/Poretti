(function (angular) {
    'use strict';

    angular.module('poretti')
        .controller('RegisterCtrlAs', RegisterCtrlAs);

    RegisterCtrlAs.$inject = ['$state', 'authorizationService', 'companyService', 'alertify']

    function RegisterCtrlAs( $state, authorizationService, companyService, alertify) {

        var vm = this;

        vm.user = {};
        vm.status = false;
        vm.isRegisteringForCompany = false;
        vm.companies = [];
        vm.currentDisplayingCompanies = [];
        vm.currentIndex = 0;
        vm.register = register;
        vm.getNextCompanies = getNextCompanies;
        vm.getPreviousCompanies = getPreviousCompanies;

        activate();

        function activate() {
            companyService.findAll().then(function(response) {
                vm.companies = _.chunk(response.data, 5);
                vm.currentDisplayingCompanies = vm.companies[0];
                vm.currentIndex = 0;
            }).catch(function(error) {
                alertify.delay(1000).error("Server error.")
            });
        }

        function register() {
            authorizationService.register(vm.user).then(function(response) {
                vm.status = true;
                return companyService.findAll();
            }).catch(function(error) {
                vm.user={};
                alertify.delay(1000).error("Server error.")
            });
        }

        function getNextCompanies() {
            vm.currentDisplayingCompanies = vm.companies[++vm.currentIndex];
        }

        function getPreviousCompanies() {
            vm.currentDisplayingCompanies = vm.companies[--vm.currentIndex];
        }
    }
})(angular);
