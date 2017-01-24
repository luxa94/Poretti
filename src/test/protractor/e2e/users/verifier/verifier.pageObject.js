var VerifierPage = function () {
};

VerifierPage.prototype = Object.create({}, {

    showReportsButton: {
        get: function () {
            return element(by.css("button[id^=verifier-show-reports]"));
        }
    },

    advertisementStatusLabel: {
        get: function () {
            return element(by.id('verifier-advertisement-status'));
        }
    },

    showReportsButtonForPendingApproval: {
        get: function () {
            return element(by.id('verifier-show-reports-PENDING_APPROVAL'));
        }
    },

    approveButton: {
        get: function () {
            return element(by.id('verifier-button-approve'));
        }
    },

    invalidateButton: {
        get: function () {
            return element(by.id('verifier-button-invalidate'));
        }
    },

    statusList: {
        get: function () {
            return element.all(by.id('verifier-advertisement-status'));
        }
    },

    numberOfInvalid: {
        get: function () {
            var i = 0;
            this.statusList.forEach(function (obj) {
                if (obj.status === 'INVALID') {
                    i += 1;
                }
            });
            return i;
        }
    },

    numberOfPending: {
        get: function () {
            var i = 0;
            this.statusList.forEach(function (obj) {
                if (obj.status === 'PENDING_APPROVAL') {
                    i += 1;
                }
            });
            return i;
        }
    },

    ensureAdvertisementIsInvalidated: {
        value: function (oldInvalidCount) {
            var newInvalidCount = this.numberOfInvalid;
            browser.wait(function () {
                return oldInvalidCount === newInvalidCount - 1;
            }, 10000);
        }
    },

    ensureAdvertisementIsApproved: {
        value: function (oldCount) {
            var thatAdvertisementList = this.statusList;
            return browser.wait(function () {
                return thatAdvertisementList.count().then(function (newCount) {
                    return oldCount === newCount + 1;
                });
            }, 10000);
        }
    },

    getNumberOfAdvertisements: {
        value: function (numberOfAdvertisements) {
            var thatAdvertisementsList = this.statusList;
            return browser.wait(function () {
                return thatAdvertisementsList.count().then(function (numberOfItems) {
                    numberOfAdvertisements.value = numberOfItems;
                    return thatAdvertisementsList;
                });
            }, 10000)
        }
    }

});

module.exports = VerifierPage;
