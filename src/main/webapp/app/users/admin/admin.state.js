(function (angular) {
    'use strict';

    angular.module('poretti')
        .config(function ($stateProvider) {
            $stateProvider
                .state('admin', {
                    url: '/admin',
                    views: {
                        'body': {
                            templateUrl: 'app/users/admin/admin.html',
                            controller: 'AdminCtrlAs',
                            controllerAs: 'vm'
                        }
                    }
                })
        })
})(angular);