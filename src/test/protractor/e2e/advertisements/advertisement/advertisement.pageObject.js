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
        get: function () {
            return element.all(by.repeater("report in vm.advertisement.reports"));
        }
    },

    ensureReportIsAdded: {
        value: function (numberOfReports) {
            var thatReportList = this.reportList;
            return browser.wait(function () {
                return thatReportList.count().then(function (newNumberOfReports) {
                    return newNumberOfReports === (numberOfReports.numberValue + 1);
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
        get: function () {
            return element.all(by.repeater("review in vm.advertisement.reviews"));
        }
    },

    ensureReviewIsAdded: {
        value: function (numberOfReviews) {
            var thatReviewList = this.reviewList;
            return browser.wait(function () {
                return thatReviewList.count().then(function (newNumberOfReviews) {
                    return newNumberOfReviews === (numberOfReviews.numberValue + 1);
                });
            }, 10000);
        }
    },

    getNumberOfReports: {
        value: function (numberOfReports) {
            var thatReportList = this.reportList;
            return browser.wait(function () {
                return thatReportList.count().then(function (numberOfItems) {
                    return numberOfReports.numberValue = numberOfItems;
                });
            }, 20000);
        }
    },

    getNumberOfReviews: {
        value: function (numberOfReviews) {
            var thatReviewList = this.reviewList;
            return browser.wait(function () {
                return thatReviewList.count().then(function (numberOfItems) {
                    return numberOfReviews.numberValue = numberOfItems;
                });
            }, 20000);
        }
    }
});

module.exports = AdvertisementPage;