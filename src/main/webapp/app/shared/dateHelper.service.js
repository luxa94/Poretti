(function(angular) {
    'use strict';

    angular
        .module('poretti')
        .service('dateHelper', dateHelper);

    function dateHelper() {
        return {
            format: format
        };

        function format(milliseconds) {
            var date = new Date(milliseconds);
            var dayDate = date.getDate();
            var month = date.getMonth()+1;
            var year = date.getFullYear();
            var hours = date.getHours();
            var minutes = date.getMinutes();
            var formattedDate = dayDate+"."+month+"."+year+"  "+hours+":"+minutes;

            return formattedDate;
        }
    }
})(angular);
