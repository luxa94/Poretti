(function (angular) {
    'use strict';
    angular
        .module('poretti')
        .service('companyService', ['$http', companyService]);

    function companyService($http) {
        return {
            findOne: findOne,
            findAll: findAll,
            create: create,
            edit: edit,
            createRealEstate: createRealEstate,
            findRealEstates: findRealEstates,
            editRealEstate: editRealEstate,
            deleteRealEstate: deleteRealEstate,
            createAdvertisement: createAdvertisement,
            findAdvertisements: findAdvertisements,
            createAdvertisementAndRealEstate: createAdvertisementAndRealEstate,
            editAdvertisement: editAdvertisement,
            deleteAdvertisement: deleteAdvertisement,
            createReview: createReview,
            findReviews: findReviews,
            findMemberships: findMemberships,
            createMembership: createMembership,
            approveMembership: approveMembership,
            leaveCompany: leaveCompany
        };

        function findOne(id) {
            return $http.get(pathWithId(id));
        }

        function findAll() {
            return $http.get(BASE_URL);
        }

        function create(registerCompanyDTO) {
            return $http.post(BASE_URL, registerCompanyDTO);
        }

        function edit(id, companyDTO) {
            return $http.put(pathWithId(id), companyDTO);
        }

        function createRealEstate(id, realEstateDTO) {
            return $http.post(pathWithId(id) + REAL_ESTATE_PATH, realEstateDTO);
        }

        function findRealEstates(id) {
            return $http.get(pathWithId(id) + REAL_ESTATE_PATH);
        }

        function editRealEstate(id, realEstateId, realEstateDTO) {
            return $http.put(companyAndRealEstate(id, realEstateId), realEstateDTO);
        }

        function deleteRealEstate(id, realEstateId) {
            return $http.delete(companyAndRealEstate(id, realEstateId));
        }

        function createAdvertisement(id, realEstateId, advertisementDTO) {
            return $http.post(companyAndRealEstate(id, realEstateId) + ADVERTISEMENT_PATH, advertisementDTO);
        }

        function findAdvertisements(id, params) {
            return $http.get(pathWithId(id) + ADVERTISEMENT_PATH, {params: params});
        }

        function createAdvertisementAndRealEstate(id, advertisementRealEstateDTO) {
            return $http.post(pathWithId(id) + ADVERTISEMENT_PATH, advertisementRealEstateDTO);
        }

        function editAdvertisement(id, advertisementId, advertisementDTO) {
            return $http.put(companyAndAdvertisement(id, advertisementId), advertisementDTO);
        }

        function deleteAdvertisement(id, advertisementId) {
            return $http.delete(companyAndAdvertisement(id, advertisementId));
        }

        function createReview(id, reviewDTO) {
            return $http.post(pathWithId(id) + REVIEW_PATH, reviewDTO);
        }

        function findReviews(id) {
            return $http.get(pathWithId(id) + REVIEW_PATH);
        }

        function findMemberships(id) {
            return $http.get(pathWithId(id) + MEMBERSHIP_PATH);
        }

        function createMembership(id) {
            return $http.post(pathWithId(id) + MEMBERSHIP_PATH);
        }

        function approveMembership(id, membershipId) {
            return $http.put(companyAndMembership(id, membershipId));
        }

        function leaveCompany(id, membershipId) {
            return $http.delete(companyAndMembership(id, membershipId));
        }
    }

    var BASE_URL = '/api/companies';
    var REAL_ESTATE_PATH = '/realEstates';
    var ADVERTISEMENT_PATH = '/advertisements';
    var REVIEW_PATH = '/reviews';
    var MEMBERSHIP_PATH = '/memberships';

    function pathWithId(id) {
        return BASE_URL + '/' + id;
    }

    function realEstateWithId(realEstateId) {
        return REAL_ESTATE_PATH + '/' + realEstateId;
    }

    function advertisementWithId(advertisementId) {
        return ADVERTISEMENT_PATH + '/' + advertisementId;
    }

    function membershipWithId(membershipId) {
        return MEMBERSHIP_PATH + '/' + membershipId;
    }

    function companyAndRealEstate(id, realEstateId) {
        return pathWithId(id) + realEstateWithId(realEstateId);
    }

    function companyAndAdvertisement(id, advertisementId) {
        return pathWithId(id) + advertisementWithId(advertisementId);
    }

    function companyAndMembership(id, membershipId) {
        return pathWithId(id) + membershipWithId(membershipId);
    }

}(angular));

