var AdvertisementPage = function () {
};

AdvertisementPage.prototype = Object.create({}, {

    advertisementTitleSpan: {
        get: function () {
            return element(by.id("advertisement-span-title"));
        }
    },

    advertisementTypeSpan: {
        get: function () {
            return element(by.id("advertisement-span-type"));
        }
    },

    advertisementPriceAndCurrencySpan: {
        get: function () {
            return element(by.id("advertisement-span-price-currency"));
        }
    },

    advertisementAverageRating: {
        get: function () {
            return element(by.id("advertisement-span-averageRating"));
        }
    },

    realEstateImg: {
        get: function () {
            return element(by.id("advertisement-img-realEstate"));
        }
    },

    advertisementDescriptionParagraph: {
        get: function () {
            return element(by.id("advertisement-p-description"));
        }
    },

    advertisementReportButton: {
        get: function () {
            return element(by.id("advertisement-btn-report"));
        }
    },

    reportList: {
        get: function() {
            return element.all(by.repeater("report in vm.advertisement.reports"));
        }
    },

    ensureReportIsAdded: {
        value: function (numberOfReports) {
            var thatReportList = this.reportList;
            browser.wait(function () {
                return thatReportList.count().then(function(newNumberOfReports){
                    return newNumberOfReports === (numberOfReports + 1);
                });
            }, 10000);
        }
    },


    advertisementReviewButton: {
        get: function () {
            return element(by.id("advertisement-btn-review"));
        }
    },

    reviewList: {
        get: function() {
            return element.all(by.repeater("review in vm.advertisement.reviews"));
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
    }



});

module.exports = AdvertisementPage;