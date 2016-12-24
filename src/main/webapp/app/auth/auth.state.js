(function (angular) {
    'use strict';

    angular.module('poretti')
        .config(function ($stateProvider) {
            $stateProvider
                .state('login', {
                    url: '/login',
                    views: {
                        'body': {
                            templateUrl: 'app/auth/login/login.html',
                            controller: 'LoginCtrlAs',
                            controllerAs: 'vm'
                        }
                    }
                })
                .state('register', {
                    url: '/register',
                    views: {
                        'body': {
                            templateUrl: 'app/auth/register/register.html',
                            controller: 'RegisterCtrlAs',
                            controllerAs: 'vm'
                        }
                    }
                })
                .state('verifyAccount', {
                    url: '/verifyAccount/:id',
                    views: {
                        'body' : {
                            templateUrl: 'app/auth/verifyAccount/verifyAccount.html',
                            controller: 'VerifyAccountCtrlAs',
                            controllerAs: 'vm'
                        }
                    }
                });
        });

})(angular);
