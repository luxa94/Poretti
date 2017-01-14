(function (angular) {
    'use strict';

    angular
        .module('poretti')
        .service('companyService', companyService);

    companyService.$inject = ['companyDataService'];

    function companyService(companyDataService) {

        return {
            find: findOne,
            findAll: findAll,
            findMemberships: findMemberships,
            populateForRegister: populateForRegister,
            create: create,
            edit: edit,
            canJoinCompany: canJoinCompany,
            canLeaveCompany: canLeaveCompany,
            approveMembership: approveMembership,
            leaveCompany: leaveCompany,
            findMembershipForUser: findMembershipForUser,
            createMembership: createMembership,
            findAdvertisements: findAdvertisements,
            findRealEstates: findRealEstates,
            findReviews: findReviews,
            createReview: createReview,
            reviewCanBeErased: reviewCanBeErased,
            createAdvertisementForRealEstate: createAdvertisementForRealEstate,
            createAdvertisementAndRealEstate: createAdvertisementAndRealEstate,
            createRealEstate: createRealEstate,
            editRealEstate: editRealEstate,
            editAdvertisement: editAdvertisement,
            areAllMembershipsUnconfirmed: areAllMembershipsUnconfirmed
        };

        function findOne(companyId) {
            return companyDataService.findOne(companyId)
                .then(findOneSuccess)
        }

        function findOneSuccess(response) {
            return response.data;
        }

        function findAll() {
            return companyDataService.findAll()
                .then(findAllSuccess);
        }

        function findAllSuccess(response) {
            return response.data;
        }

        function findMemberships(companyId) {
            return companyDataService.findMemberships(companyId)
                .then(findMembershipsSuccess);
        }

        function findMembershipsSuccess(response) {
            return response.data;
        }

        function canJoinCompany(user, companyMemberships) {
            return !_.some(companyMemberships, function (membership) {
                return membership.member.id === user.id;
            });
        }

        function canLeaveCompany(user, companyMemberships) {
            return _.some(companyMemberships, function (membership) {
                return membership.member.id === user.id;
            });
        }

        function approveMembership(company, membership) {
            return companyDataService.approveMembership(company.id, membership.id);
        }

        function leaveCompany(company, membership) {
            return companyDataService.leaveCompany(company.id, membership.id);
        }

        function findMembershipForUser(memberships, user) {
            return _.find(memberships, function (membership) {
                return user.id === membership.member.id;
            });
        }

        function createMembership(companyId) {
            return companyDataService.createMembership(companyId);
        }

        function findAdvertisements(companyId) {
            return companyDataService.findAdvertisements(companyId);
        }

        function findRealEstates(companyId) {
            return companyDataService.findRealEstates(companyId)
                .then(findRealEstatesSuccess)
        }

        function findRealEstatesSuccess(response) {
            return response.data;
        }

        function findReviews(companyId) {
            return companyDataService.findReviews(companyId)
                .then(findReviewsSuccess);
        }

        function findReviewsSuccess(response) {
            var reviews = orderReviewsByDate(response.data);
            return reviews;
        }

        function orderReviewsByDate(data) {
            return _.orderBy(data, ['editedOn'], ['desc']);
        }

        function populateForRegister(data) {
            return _.chunk(data, 5);
        }

        function reviewCanBeErased(reviews, loggedUser) {
            if (loggedUser && reviews.length) {
                return _.forEach(reviews, function (review) {
                    review.canBeErased = review.author.id === loggedUser.id;
                });
            }
            return reviews;
        }

        function create(companyDTO, userDTO) {
            var registerCompanyDTO = {};
            registerCompanyDTO.company = companyDTO;
            registerCompanyDTO.user = userDTO;
            return companyDataService.create(registerCompanyDTO);
        }

        function edit(companyId, companyToEdit) {
            return companyDataService.edit(companyId, companyToEdit);
        }

        function createReview(companyId, reviewDTO) {
            return companyDataService.createReview(companyId, reviewDTO)
                .then(createReviewSuccess);
        }

        function createReviewSuccess(response) {
            return response.data;
        }

        function createAdvertisementForRealEstate(companyId, advertisementRealEstate) {
            var realEstateId = advertisementRealEstate.realEstateId;
            var advertisement = advertisementRealEstate.advertisement;
            return companyDataService.createAdvertisement(companyId, realEstateId, advertisement);
        }

        function createAdvertisementAndRealEstate(companyId, advertisementRealEstate) {
            return companyDataService.createAdvertisementAndRealEstate(companyId, advertisementRealEstate);
        }

        function createRealEstate(companyId, realEstate) {
            return companyDataService.createRealEstate(companyId, realEstate);
        }

        function editRealEstate(companyId, realEstate) {
            return companyDataService.editRealEstate(companyId, realEstate.id, realEstate);
        }

        function editAdvertisement(companyId, advertisement) {
            return companyDataService.editAdvertisement(companyId, advertisement.id, advertisement);
        }

        function areAllMembershipsUnconfirmed(memberships) {
            if (memberships.length) {
                return _.every(memberships, function(membership) {
                    return !membership.confirmed;
                });
            }
        }


    }
})(angular);
