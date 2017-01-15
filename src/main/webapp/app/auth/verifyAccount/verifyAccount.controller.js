(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .controller('VerifyAccountCtrlAs', VerifyAccountCtrlAs);

    VerifyAccountCtrlAs.$inject = ['$stateParams', 'authorizationDataService', '$state', 'PorettiHandler', 'alertify'];

    function VerifyAccountCtrlAs($stateParams, authorizationDataService, $state, PorettiHandler, alertify) {

        activate();

        function activate() {
            authorizationDataService.verifyAccount($stateParams.id)
                .then(function() {
                    alertify.success('Successfully verified.');
                    alertify.success('You can log in now.');
                    $state.go('login');
                }).catch(function(error) {
                    PorettiHandler.report(error);
                    $state.go('login');
                });
        }
    }
})(angular);