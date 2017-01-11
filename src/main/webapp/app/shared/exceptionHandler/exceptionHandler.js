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

        function report(message) {
            return function (reason) {
                console.log(reason);
                message ? alertify.error(message) : alertify.error(reason);
            };
        }
    }
})(angular);
