(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .factory('PorettiHandler', PorettiHandler);

    PorettiHandler.$inject = ['alertify'];

    function PorettiHandler(alertify) {

        return {
            report: report
        };

        function report(reason) {
            if (!reason) {
                reason = "Error occurred.";
            }
            alertify.error(reason);
        }
    }
})(angular);
