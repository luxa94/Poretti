(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .config(function ($stateProvider) {
            $stateProvider
                .state('admin', {
                    url: '/admin/:id',
                    views: {
                        'content': {
                            templateUrl: 'app/users/admin/admin.html',
                            controller: 'AdminCtrlAs',
                            controllerAs: 'vm'
                        },
                        'navbar' : {
                            templateUrl: 'app/navbar/navbar.html',
                            controller: 'NavbarCtrlAs',
                            controllerAs: 'vm',
                        }
                    }
                });
        })
})(angular);