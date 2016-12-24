(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('VerifyAccountCtrlAs', VerifyAccountCtrlAs);

    VerifyAccountCtrlAs.$inject = ['$stateParams', 'authorizationService'];

    function VerifyAccountCtrlAs($stateParams, authorizationService) {

        var vm = this;

        vm.confirmedStatus = false;

        activate();

        function activate() {
            authorizationService.verifyAccount($stateParams.id).then(function(response) {
                vm.confirmedStatus = true;
            }).catch(function(error) {
                console.log(error);
            })
        }
    }
})(angular);