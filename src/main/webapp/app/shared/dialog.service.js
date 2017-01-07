(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .service('dialogService', dialogService);

    dialogService.$inject = ['$mdDialog'];

    function dialogService($mdDialog) {

        return {
            open: open,
            cancel: cancel,
            confirmResponse: confirmResponse
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

        function cancel() {

        }

        function confirmResponse() {

        }
    }
})(angular);