var AdvertisementsPage = function() {};

AdvertisementsPage.prototype = Object.create({}, {

    filterTitleInput: {
        get: function(){
            return element(by.id("advertisements-input-filter-title"));
        },
        set: function(value) {
            return this.filterTitleInput.clear().sendKeys(value);
        }
    },

    filterAdvTypeSelect: {
        get: function(){
            return element(by.id("advertisements-select-filter-ad-type"));
        },
        set: function(value) {
            return this.filterAdvTypeSelect.sendKeys(value);
        }
    },

    filterPriceFromInput: {
        get: function() {
           return element(by.id("advertisements-input-filter-priceFrom"));
        },
        set: function(value) {
            return this.filterPriceFromInput.clear().sendKeys(value);
        }
    },

    filterPriceToInput: {
        get: function() {
            return element(by.id("advertisements-input-filter-priceTo"));
        },
        set: function(value) {
            return this.filterPriceToInput.clear().sendKeys(value);
        }
    },

    filterRealEstateNameInput: {
        get: function() {
            return element(by.id("advertisements-input-filter-re-name"));
        },
        set: function(value) {
            return this.filterRealEstateNameInput.clear().sendKeys(value);
        }
    },

    filterRealEstateTypeSelect: {
        get: function() {
            return element(by.id("advertisements-select-filter-re-type"));
        },
        set: function(value) {
            return this.filterRealEstateTypeSelect.sendKeys(value);
        }
    },

    searchButton: {
        get: function() {
            return element(by.id("advertisements-btn-search"));
        }
    },

    clearButton: {
        get: function() {
            return element(by.id("advertisements-btn-clear"));
        }
    },

    advertisementList: {
        get: function() {
            return element.all(by.repeater("advertisement in vm.advertisements"));
        }
    },

    advertisementCard: {
        get: function() {
            return this.advertisementList.get(0);
        }
    },

    advertisementTitleText: {
        get: function() {
            return this.advertisementList.get(0).element(by.tagName('md-card-title-text'));
        }
    },

    detailsButton: {
        get: function() {
            return this.advertisementList.get(0).element(by.tagName('button'));
        }
    },

    previousPageButton: {
        get: function() {
            return element(by.id("advertisements-btn-previousPage"));
        }
    },

    nextPageButton: {
        get: function() {
            return element(by.id("advertisements-btn-nextPage"));
        }
    },

    pageNumbersButtons: {
        get: function() {
            return element.all(by.repeater("pageNumber in vm.numberOfPages"));
        }
    },

    pageNumberButton: {
        get: function() {
            return this.pageNumbersButtons.get(0);
        }
    },

    ensureNumberOfPagesIsGood: {
        value: function(numberOfPages) {
            var thatPageNumberButtons = this.pageNumbersButtons;
            return browser.wait(function() {
                return thatPageNumberButtons.count().then(function(numberOfItems) {
                    return numberOfItems === numberOfPages.value;
                });
            }, 10000);
        }
    },

    ensureSizePerPageIsGood: {
        value: function(size) {
            var thatAdvertisementList = this.advertisementList;
            return browser.wait(function() {
                return thatAdvertisementList.count().then(function(numberOfItems) {
                    return numberOfItems === size.numberValue;
                });
            }, 10000);
        }
    },

    ensureFilterIsExecuted: {
        value: function() {
            var thatAdvertisementList = this.advertisementList;
            return browser.wait(function() {
                return thatAdvertisementList.count().then(function(numberOfItems) {
                    return (numberOfItems > 0);
                });
            }, 10000);
        }
    },

    getNumberOfPages: {
        value: function(numberOfPages) {
            var thatPageNumbersButtons = this.pageNumbersButtons;
            browser.wait(function() {
                return thatPageNumbersButtons.count().then(function(numberOfItems) {
                    return numberOfPages.value = numberOfItems;
                })
            }, 20000);
        }
    },

    getSizePerPage: {
        value: function(sizePerPage) {
            var thatAdvertisementList = this.advertisementList;
            return browser.wait(function() {
                return thatAdvertisementList.count().then(function(numberOfItems) {
                    return sizePerPage.numberValue = numberOfItems;
                });
            }, 20000);
        }
    }
});

module.exports = AdvertisementsPage;