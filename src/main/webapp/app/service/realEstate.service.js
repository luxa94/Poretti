(function (angular) {
    'use strict'

    angular
        .module('poretti')
        .service('realEstateService', ['$http', realEstateService])

    function realEstateService($http) {
        var BASE_URL = "/api/realEstates";

        function pathWithId(id) {
            return BASE_URL + "/" + id;
        }

        return {
            findOne: findOne,
            create: createOne,
            edit: edit,
            delete: deleteOne,
            findAdvertisements: findAdvertisements,
            createAdvertisement: createAdvertisement,
        };

        function findOne(id) {
            return $http.get(pathWithId(id));
        }

        function createOne(realEstateDTO) {
            return $http.post(BASE_URL, realEstateDTO);
        }

        function edit(id, realEstateDTO) {
            return $http.put(pathWithId(id), realEstateDTO);
        }

        function deleteOne(id) {
            return $http.delete(pathWithId(id));
        }

        function findAdvertisements(id) {
            var advertisementsPath = pathWithId(id) + '/advertisements';
            return $http.get(advertisementsPath);
        }

        function createAdvertisement(id, advertisementDTO) {
            var advertisementsPath = pathWithId(id) + '/advertisements';
            return $http.post(advertisementsPath, advertisementDTO);
        }
    }
}(angular));