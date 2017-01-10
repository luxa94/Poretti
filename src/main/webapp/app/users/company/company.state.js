(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .config(function($stateProvider) {
            $stateProvider
                .state('company', {
                    url: '/company/:id',
                    views: {
                        'content': {
                            templateUrl: 'app/users/company/company.html',
                            controller: 'CompanyCtrlAs',
                            controllerAs: 'vm'
                        },
                        'navbar': {
                            templateUrl: 'app/navbar/navbar.html',
                            controller: 'NavbarCtrlAs',
                            controllerAs: 'vm'
                        }
                    }
                });
        });
})(angular);
