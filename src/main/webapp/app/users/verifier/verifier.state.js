(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .config(function ($stateProvider) {
            $stateProvider
                .state('verifier', {
                    url: '/verifier/:id',
                    views: {
                        'body' : {
                            templateUrl: 'app/users/verifier/verifier.html',
                            controller: 'VerifierCtrlAs',
                            controllerAs: 'vm',
                        }
                    }
                });
        })
})(angular);
