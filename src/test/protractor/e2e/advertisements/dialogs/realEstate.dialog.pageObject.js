var RealEstateDialog = function() {};

RealEstateDialog.prototype = Object.create({}, {

    nameInput: {
        get: function() {
           return element(by.id("realEstate-input-name"));
        },
        set: function(value) {
            return this.nameInput.clear().sendKeys(value);
        }
    },

    areaInput: {
        get: function() {
            return element(by.id("realEstate-input-area"));
        },
        set: function(value) {
            return this.areaInput.clear().sendKeys(value);
        }
    },

    descriptionTextArea: {
        get: function() {
            return element(by.id("realEstate-textarea-desc"));
        },
        set: function(value) {
            return this.descriptionTextArea.clear().sendKeys(value);
        }
    },

    typeSelect: {
        get: function() {
            return element(by.id("realEstate-select-type"));
        },
        set: function(value) {
            return this.typeSelect.sendKeys(value);
        }
    },

    locationCity: {
        get: function() {
            return element(by.id("realEstate-input-location-city"))
        },
        set: function(value) {
            return this.locationCity.clear().sendKeys(value);
        }
    },

    pinMap: {
        get: function() {
            return element(by.id("realEstate-pinMap"));
        }
    },

    okButton: {
        get: function() {
            return element(by.id("realEstate-btn-ok"));
        }
    },

    cancelButton: {
        get: function() {
            return element(by.id("realEstate-btn-cancel"));
        }
    },

    ensureRealEstateIsAdded: {
        value: function(list, numberOfItemsInList) {
            browser.wait(function(){
                return list.count().then(function (newNumberOfReviews) {
                    return newNumberOfReviews === (numberOfItemsInList.numberValue + 1);
                });
            }, 20000);
        }
    }
});

module.exports = RealEstateDialog;