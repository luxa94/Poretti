(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('VerifyAccountCtrlAs', VerifyAccountCtrlAs);

    VerifyAccountCtrlAs.$inject = ['$stateParams', 'authorizationDataService', '$state'];

    function VerifyAccountCtrlAs($stateParams, authorizationDataService, $state) {

        activate();

        function activate() {
            authorizationDataService.verifyAccount($stateParams.id)
                .then(function() {
                    alertify.success('Successfully verified.');
                    alertify.success('You can log in now.');
                    $state.go('login');
                }).catch(handleError);
        }

        function handleError() {
            alertify.error('Error verifying.');
            $state.go('login');
        }
    }
})(angular);