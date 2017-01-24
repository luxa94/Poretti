var CompanyEditDialog = function () {
};

CompanyEditDialog.prototype = Object.create({}, {

    nameInput: {
        get: function () {
            return element(by.id("companyEdit-input-name"));
        },
        set: function (value) {
            this.nameInput.clear().sendKeys(value);
        }
    },

    stateInput: {
        get: function () {
            return element(by.id("companyEdit-input-stateInput"));
        },

        set: function (value) {
            this.stateInput.clear().sendKeys(value);
        }
    },

    cityInput: {
        get: function () {
            return element(by.id("companyEdit-input-cityInput"));
        },
        set: function (value) {
            this.cityInput.clear().sendKeys(value);
        }
    },

    cityArea: {
        get: function () {
            return element(by.id("companyEdit-input-cityArea"));
        },
        set: function (vale) {
            this.cityArea.clear().sendKeys(value);
        }
    },

    zipCode: {
        get: function () {
            return element(by.id("companyEdit-input-zipCode"));
        },
        set: function (value) {
            this.zipCode.clear().sendKeys(value);
        }
    },

    street: {
        get: function () {
            return element(by.id("companyEdit-input-street"));
        },
        set: function (value) {
            this.street.clear().sendKeys(value);
        }
    },

    streetNumber: {
        get: function () {
            return element(by.id("companyEdit-input-streetNumber"));
        },
        set: function (value) {
            this.streetNumber.clear().sendKeys(value);
        }
    },

    okButton: {
        get: function () {
            return element(by.id("companyEdit-button-ok"));
        }
    },

    cancelButton: {
        get: function () {
            return element(by.id("companyEdit-button-cancel"));
        }
    }

});

module.exports = CompanyEditDialog;