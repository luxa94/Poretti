(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .service('dialogService', dialogService);

    dialogService.$inject = ['$mdDialog'];

    function dialogService($mdDialog) {

        return {
            open: open
        };

        function open(ev, ctrl, tUrl, locals) {
             return $mdDialog.show({
                controller: ctrl,
                controllerAs: 'vm',
                templateUrl: tUrl,
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                locals : locals
            });
        }
    }
})(angular);