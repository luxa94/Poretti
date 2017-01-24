var CompanyPage = function () {
};

CompanyPage.prototype = Object.create({}, {
    nameSpan: {
        get: function () {
            return element(by.id("company-span-name"));
        }
    },

    editButton: {
        get: function () {
            return element(by.id("company-btn-edit"));
        }
    },

    reviewButton: {
        get: function () {
            return element(by.id("company-btn-review"));
        }
    },

    realEstateButton: {
        get: function () {
            return element(by.id("company-btn-realEstate"));
        }
    },

    advertisementButton: {
        get: function () {
            return element(by.id("company-btn-advertisement"));
        }
    },

    joinButton: {
        get: function () {
            return element(by.id("company-btn-join"));
        }
    },

    leaveButton: {
        get: function () {
            return element(by.id("company-btn-leave"));
        }
    },

    advertisementList: {
        get: function() {
            return element.all(by.repeater("advertisement in vm.advertisements"));
        }
    },

    advertisementEditButton: {
        get: function() {
            return element.all(by.id("company-btn-edit-advertisement")).get(0);
        }
    },

    advertisementDetailsButton: {
        get: function() {
            return element.all(by.id("company-btn-details-advertisement")).get(0);
        }
    },

    advertisementDeleteButton: {
        get: function() {
            return element.all(by.id("company-btn-delete-advertisement")).get(0);
        }
    },

    realEstateList: {
        get: function() {
            return element.all(by.repeater("realEstate in vm.realEstates"));
        }
    },

    realEstateEditButton: {
        get: function() {
            return element.all(by.id("company-btn-edit-realEstate")).get(0);
        }
    },

    realEstateDeleteButton: {
        get: function() {
            return element.all(by.id("company-btn-delete-realEstate")).get(0);
        }
    },

    reviewList: {
        get: function() {
            return element.all(by.repeater("review in vm.reviews"));
        }
    },

    companyMembersList: {
        get: function() {
            return element.all(by.repeater("membership in vm.filteredMemberships()"));
        }
    },

    advertisementsTab: {
        get: function() {
            return element.all(by.tagName('md-tab-item')).get(0);
        }
    },

    realEstatesTab: {
        get: function() {
            return element.all(by.tagName('md-tab-item')).get(1);
        }
    },

    companyMembersTab: {
        get: function() {
            return element.all(by.tagName('md-tab-item')).get(2);
        }
    },

    reviewsTab: {
        get: function() {
            return element.all(by.tagName('md-tab-item')).get(3);
        }
    },

    approveList: {
        get: function() {
            return element.all(by.id("company-btn-approve"));
        }
    },

    approveButton: {
        get: function() {
            return this.approveList.get(0);
        }
    },

    ensureIsRedirectedToAdvertisement: {
        value: function() {
            return browser.wait(function() {
                return browser.getCurrentUrl().then(function(url) {
                    return url.indexOf('http://localhost:8080/#!/advertisement') !== -1;
                });
            }, 10000)
        }
    },

    ensureReviewIsAdded: {
        value: function (numberOfReviews) {
            var thatReviewList = this.reviewList;
            return browser.wait(function () {
                return thatReviewList.count().then(function(newNumberOfReviews){
                    return newNumberOfReviews === (numberOfReviews.numberValue + 1);
                });
            }, 10000);
        }
    },

    ensureAdvertisementIsDeleted: {
        value: function(numberOfAdvertisements) {
            var thatAdvertisementList = this.advertisementList;
            return browser.wait(function() {
                return thatAdvertisementList.count().then(function(numberOfItems) {
                    return numberOfItems === (numberOfAdvertisements.numberValue - 1);
                });
            }, 10000);
        }
    },

    ensureRealEstateIsDeleted: {
        value: function(numberOfRealEstates) {
            var thatRealEstateList = this.realEstateList;
            return browser.wait(function() {
                return thatRealEstateList.count().then(function(numberOfItems) {
                    return numberOfItems === (numberOfRealEstates.numberValue - 1);
                });
            }, 10000);
        }
    },

    ensureUserIsAdded: {
        value: function(numberOfCompanyMembers) {
            var thatCompanyMembersList = this.companyMembersList;
            return browser.wait(function() {
                return thatCompanyMembersList.count().then(function(numberOfItems) {
                    return numberOfItems === (numberOfCompanyMembers.numberValue + 1);
                });
            }, 20000);
        }
    },

    ensureUserIsDeleted: {
        value: function(numberOfCompanyMembers) {
            var thatCompanyMembersList = this.companyMembersList;
            return browser.wait(function() {
                return thatCompanyMembersList.count().then(function(numberOfItems) {
                    return numberOfItems === (numberOfCompanyMembers.numberValue - 1);
                });
            }, 20000);
        }
    },

    ensureUserIsApproved: {
        value: function(numberOfNotApprovedMember) {
            var thatApproveList = this.approveList;
            return browser.wait(function() {
                return thatApproveList.count().then(function(numberOfItems) {
                    return numberOfItems === (numberOfNotApprovedMember.numberValue - 1);
                });
            }, 20000);
        }
    },


    getNumberOfAdvertisements: {
        value: function(numberOfAdvertisements) {
            var thatAdvertisementList = this.advertisementList;
            return browser.wait(function() {
                return thatAdvertisementList.count().then(function(numberOfItems) {
                    numberOfAdvertisements.numberValue = numberOfItems;
                    return numberOfAdvertisements;
                });
            }, 10000);
        }
    },

    getNumberOfRealEstates: {
        value: function(numberOfRealEstates) {
            var thatRealEstateList = this.realEstateList;
            return browser.wait(function() {
                return thatRealEstateList.count().then(function(numberOfItems) {
                    numberOfRealEstates.numberValue = numberOfItems;
                    return numberOfRealEstates;
                });
            }, 10000);
        }
    },


    getNumberOfReviews: {
        value: function(numberOfReviews) {
            var thatReviewList= this.reviewList;
            return browser.wait(function() {
                return thatReviewList.count().then(function(numberOfItems) {
                    numberOfReviews.numberValue = numberOfItems;
                    return numberOfReviews;
                });
            }, 20000);
        }
    },

    getNumberOfCompanyMembers: {
        value: function(numberOfCompanyMembers) {
            var thatCompanyMembersList= this.companyMembersList;
            return browser.wait(function() {
                return thatCompanyMembersList.count().then(function(numberOfItems) {
                    numberOfCompanyMembers.numberValue = numberOfItems;
                    return numberOfCompanyMembers;
                });
            }, 10000);
        }
    },

    getNumberOfNotApprovedMembers: {
        value: function(numberOfNotApprovedMember) {
            var thatApproveList= this.approveList;
            return browser.wait(function() {
                return thatApproveList.count().then(function(numberOfItems) {
                    numberOfNotApprovedMember.numberValue = numberOfItems;
                    return numberOfNotApprovedMember;
                });
            }, 10000);
        }
    }


});

module.exports = CompanyPage;