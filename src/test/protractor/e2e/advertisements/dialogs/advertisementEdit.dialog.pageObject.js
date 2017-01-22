var AdvertisementEditDialog = function() {};

AdvertisementEditDialog.prototype = Object.create({}, {

    titleInput: {
        get: function(){
            return element(by.id("advertisementEdit-input-title"));
        },
        set: function(value) {
            this.titleInput.clear().sendKeys(value);
        }
    },

    priceInput: {
        get: function() {
            return element(by.id("advertisementEdit-input-price"));
        },
        set: function(value) {
            return this.priceInput.clear().sendKeys(value);
        }
    },

    currencySelect: {
        get: function() {
            return element(by.id("advertisementEdit-select-currency"));
        },
        set: function(value) {
            return this.currencySelect.sendKeys(value);
        }
    },

    typeSelect: {
        get: function() {
            return element(by.id("advertisementEdit-select-type"))
        },
        set: function(value){
            this.typeSelect.sendKeys(value);
        }
    },

    okButton: {
        get: function() {
            return element(by.id("advertisementEdit-btn-ok"));
        }
    },

    cancelButton: {
        get: function() {
            return element(by.id("advertisementEdit-btn-cancel"));
        }
    }
});

module.exports = AdvertisementEditDialog;