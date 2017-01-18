var AdvertisementDialog = function() {};

AdvertisementDialog.prototype = Object.create({}, {

    titleInput: {
        get: function() {
            return element(by.id("advertisement-input-title"));
        },
        set: function(value) {
            return this.titleInput.clear().sendKeys(value);
        }
    },

    priceInput: {
        get: function() {
            return element(by.id("advertisement-input-price"));
        },
        set: function(value) {
            return this.priceInput.clear().sendKeys(value);
        }
    },

    currencySelect: {
        get: function() {
            return element(by.id("advertisement-select-currency"));
        },
        set: function(value) {
            return this.currencySelect.sendKeys(value);
        }
    },

    typeSelect: {
        get: function() {
            return element(by.id("advertisement-select-type"));
        },
        set: function(value) {
            return this.typeSelect.sendKeys(value);
        }
    },

    endsOnDatepicker: {
        get: function() {
            return element(by.id("advertisement-datepicker-endsOn"));
        },
        set: function(value) {
            return this.endsOnDatepicker.sendKeys(value);
        }
    },

    chooseRealEstateButton: {
        get: function() {
            return element(by.id("advertisement-btn-chooseRE"));
        }
    },

    addRealEstateButton: {
        get: function() {
            return element(by.id("advertisement-btn-addRE"));
        }
    },

    addedRealEstateSpan: {
        get: function() {
            return element(by.id("advertisement-span-addedRE"));
        }
    },

    okButton: {
        get: function() {
            return element(by.id("advertisement-btn-confirm"));
        }
    },

    cancelButton: {
        get: function() {
            return element(by.id("advertisement-btn-cancel"));
        }
    },

    ensureAdvertisementIsAdded: {
        value: function(list, numberOfItemsInList) {
            browser.wait(function(){
                return list.count().then(function (newNumberOfItems) {
                    return newNumberOfItems === (numberOfItemsInList + 1);
                });
            }, 20000);
        }
    },

    ensureEndsOnIsAdded: {
        value: function() {
            var thatEndsOnDatepicker = this.endsOnDatepicker;
            browser.wait(function(){
                return thatEndsOnDatepicker.getText().then(function (text) {
                    return text !== "";
                });
            }, 10000);
        }
    }


});

module.exports = AdvertisementDialog;