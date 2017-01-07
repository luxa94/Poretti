(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .service('realEstateService', realEstateService);

    realEstateService.$inject = ['realEstateDataService'];

    function realEstateService(realEstateDataService) {

        return {
            create: create,
            edit: edit,
            createAdvertisementForRealEstate: createAdvertisementForRealEstate
        };

        function create(realEstate) {
            return realEstateDataService.create(realEstate);
        }

        function edit(realEstate) {
            return realEstateDataService.edit(realEstate.id, realEstate);
        }

        function createAdvertisementForRealEstate(advertisementRealEstate) {
            var realEstateId = advertisementRealEstate.realEstateId;
            var advertisement = advertisementRealEstate.advertisement;
            return realEstateDataService.createAdvertisement(realEstateId, advertisement);
        }
    }
    
})(angular);
