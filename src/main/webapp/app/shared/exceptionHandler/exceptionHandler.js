(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .factory('PorettiHandler', PorettiHandler);

    PorettiHandler.$inject = ['alertify', 'sharerrorService'];

    function PorettiHandler(alertify, sharerrorService) {

        return {
            report: report
        };

        function report(error) {
            var message = "";
            if (error) {
                debugger;
                if (error.data && error.status){
                    sharerrorService.send(error);
                    message = error.data.message ? error.data.message : "Error occurred";
                    if (error.status === 500) {
                        message = "Error occurred.";
                    }
                } else if (angular.isString(error)){
                    message = error;
                }
            } else {
                message = "Error occurred.";
            }
            alertify.error(message);
        }
    }
})(angular);
