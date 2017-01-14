(function (angular) {
    'use strict';

    angular.module('poretti')
        .controller('pinMapController', pinMapController);

    pinMapController.$inject = ['$scope', 'NgMap'];

    function pinMapController($scope, NgMap) {

        var vm = this;
        vm.location = $scope.location;

        NgMap.getMap().then(function(map) {
            vm.map = map;
            if (vm.location.hasLatLong) {
                var l = {lat: vm.location.latitude, lng: vm.location.longitude};
                vm.map.panTo(l);
            }
        });

        vm.placeMarker = function(e) {
            vm.location.hasLatLong = true;
            vm.location.latitude = e.latLng.lat();
            vm.location.longitude = e.latLng.lng();

            vm.map.panTo(e.latLng);
        };

        vm.clearLocation = function () {
            vm.location.hasLatLong = false;
        }
    }
})(angular);
