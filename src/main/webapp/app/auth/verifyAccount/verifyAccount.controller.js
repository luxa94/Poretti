(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('VerifyAccountCtrlAs', VerifyAccountCtrlAs);

    VerifyAccountCtrlAs.$inject = ['$stateParams', 'authorizationDataService'];

    function VerifyAccountCtrlAs($stateParams, authorizationDataService) {

        var vm = this;

        vm.confirmedStatus = false;

        activate();

        function activate() {
            authorizationDataService.verifyAccount($stateParams.id)
                .then(function(response) {
                    vm.confirmedStatus = true;
                }).catch(handleError);
        }

        function handleError() {
            //TODO: handle error;
        }
    }
})(angular);