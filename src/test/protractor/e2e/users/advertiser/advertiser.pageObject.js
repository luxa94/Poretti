var AdvertiserPage = function() {};

AdvertiserPage.prototype = Object.create({}, {

    nameSpan: {
        get: function() {
            return element(by.id("advertiser-span-name"));
        }
    },

    editButton: {
        get: function() {
            return element(by.id("advertiser-btn-edit"));
        }
    },

    reviewButton: {
        get: function() {
            return element(by.id("advertiser-btn-review"));
        }
    },

    realEstateButton: {
        get: function() {
            return element(by.id("advertiser-btn-realEstate"));
        }
    },

    advertisementButton: {
        get: function() {
            return element(by.id("advertiser-btn-advertisement"));
        }
    },

    advertisementList: {
        get: function() {
            return element.all(by.repeater("advertisement in vm.advertisements"));
        }
    },

    advertisementEditButton: {
        get: function() {
            return element.all(by.id("advertiser-btn-adv-edit")).get(0);
        }
    },

    advertisementDetailsButton: {
        get: function() {
            return element.all(by.id("advertiser-btn-adv-details")).get(0);
        }
    },

    advertisementDeleteButton: {
        get: function() {
            return element.all(by.id("advertiser-btn-adv-delete")).get(0);
        }
    },

    realEstateList: {
        get: function() {
            return element.all(by.repeater("realEstate in vm.realEstates"));
        }
    },

    realEstateEditButton: {
        get: function() {
            return element.all(by.id("advertiser-btn-re-edit")).get(0);
        }
    },

    realEstateDeleteButton: {
        get: function() {
            return element.all(by.id("advertiser-btn-re-delete")).get(0);
        }
    },

    reviewList: {
        get: function() {
            return element.all(by.repeater("review in vm.reviews"));
        }
    },

    noReviewsHeadline: {
        get: function() {
            return element(by.id("advertiser-h3-reviews"));
        }
    },
    
    membershipList: {
        get: function() {
            return element.all(by.repeater("membership in vm.memberships"));
        }
    },

    realEstatesTab: {
        get: function() {
            return element.all(by.tagName('md-tab-item')).get(1);
        }
    },

    advertisementsTab: {
        get: function() {
            return element.all(by.tagName('md-tab-item')).get(0);
        }
    },

    reviewsTab: {
        get: function() {
            return element.all(by.tagName('md-tab-item')).get(2);
        }
    },

    membershipsTab: {
        get: function() {
            return element.all(by.tagName('md-tab-item')).get(3);
        }
    },
    
    ensureIsRedirectedToAdvertisement: {
        value: function() {
            browser.wait(function() {
                return browser.getCurrentUrl().then(function(url) {
                   return url.indexOf('http://localhost:8080/#!/advertisement') !== -1;
                });
            }, 10000)
        }
    },

    ensureIsRedirectedToCompany: {
        value: function() {
            browser.wait(function() {
                return browser.getCurrentUrl().then(function(url) {
                    return url.indexOf('http://localhost:8080/#!/company') !== -1;
                });
            }, 10000)
        }
    },

    ensureReviewIsAdded: {
        value: function (numberOfReviews) {
            var thatReviewList = this.reviewList;
            browser.wait(function () {
                return thatReviewList.count().then(function(newNumberOfReviews){
                    return newNumberOfReviews === (numberOfReviews + 1);
                });
            }, 10000);
        }
    },

    ensureAdvertisementIsDeleted: {
        value: function(currentNumberOfItems) {
            var thatAdvertisementList = this.advertisementList;
            browser.wait(function() {
                return thatAdvertisementList.count().then(function(numberOfItems) {
                    return numberOfItems === currentNumberOfItems - 1;
                })
            }, 10000);
        }
    }
});

module.exports = AdvertiserPage;