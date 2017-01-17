var AdminPage = function () {
};

AdminPage.prototype = Object.create({}, {

    newUserUsernameInput: {
        get: function () {
            return element(by.id("admin-input-newUser-username"));
        },
        set: function (value) {
            return this.newUserUsernameInput.clear().sendKeys(value);
        }
    },

    newUserPasswordInput: {
        get: function () {
            return element(by.id("admin-input-newUser-password"));
        },
        set: function (value) {
            return this.newUserPasswordInput.clear().sendKeys(value);
        }
    },

    newUserEmailInput: {
        get: function () {
            return element(by.id("admin-input-newUser-email"));
        },
        set: function (value) {
            return this.newUserEmailInput.clear().sendKeys(value);
        }
    },

    newUserNameInput: {
        get: function () {
            return element(by.id("admin-input-newUser-name"));
        },
        set: function (value) {
            return this.newUserNameInput.clear().sendKeys(value);
        }
    },

    newUserRoleSelect: {
        get: function () {
            return element(by.id("admin-select-newUser-role"));
        },
        set: function (value) {
            return this.newUserRoleSelect.sendKeys(value);
        }
    },

    createAdminVerifierButton: {
        get: function () {
            return element(by.id("admin-btn-createAdminVerifier"));
        }
    },

    companyPibInput: {
        get: function () {
            return element(by.id("admin-input-company-pib"));
        },
        set: function (value) {
            return this.companyPibInput.clear().sendKeys(value);
        }
    },

    companyNameInput: {
        get: function () {
            return element(by.id("admin-input-company-name"));
        },
        set: function (value) {
            return this.companyNameInput.clear().sendKeys(value);
        }
    },

    addCompanyUserButton: {
        get: function () {
            return element(by.id("admin-btn-company-addUser"));
        }
    },

    addedCompanyUserSpan: {
        get: function () {
            return element(by.id("admin-span-addedUser"));
        }
    },

    createCompanyButton: {
        get: function () {
            return element(by.id("admin-btn-createCompany"));
        }
    },

    locationStateInput: {
        get: function () {
            return element(by.id("admin-input-location-state"));
        },
        set: function (value) {
            return this.locationStateInput.clear().sendKeys(value);
        }
    },

    locationCityInput: {
        get: function () {
            return element(by.id("admin-input-location-city"));
        },
        set: function (value) {
            return this.locationCityInput.clear().sendKeys(value);
        }
    },

    locationCityAreaInput: {
        get: function () {
            return element(by.id("admin-input-location-cityArea"));
        },
        set: function (value) {
            return this.locationCityAreaInput.clear().sendKeys(value);
        }
    },

    locationZipCodeInput: {
        get: function () {
            return element(by.id("admin-input-location-zipCode"));
        },
        set: function (value) {
            return this.locationZipCodeInput.clear().sendKeys(value);
        }
    },

    locationStreetInput: {
        get: function () {
            return element(by.id("admin-input-location-street"));
        },
        set: function (value) {
            return this.locationStreetInput.clear().sendKeys(value);
        }
    },

    locationStreetNumberInput: {
        get: function () {
            return element(by.id("admin-input-location-streetNumber"));
        },
        set: function (value) {
            return this.locationStreetNumberInput.clear().sendKeys(value);
        }
    },

    companyUserUsernameInput: {
        get: function () {
            return element(by.id("admin-input-companyUser-username"));
        },
        set: function (value) {
            return this.companyUserUsernameInput.clear().sendKeys(value);
        }
    },

    companyUserPasswordInput: {
        get: function () {
            return element(by.id("admin-input-companyUser-password"));
        },
        set: function (value) {
            return this.companyUserPasswordInput.clear().sendKeys(value);
        }
    },

    companyUserEmailInput: {
        get: function () {
            return element(by.id("admin-input-companyUser-email"));
        },
        set: function (value) {
            return this.companyUserEmailInput.clear().sendKeys(value);
        }
    },

    companyUserNameInput: {
        get: function () {
            return element(by.id("admin-input-companyUser-name"));
        },
        set: function (value) {
            return this.companyUserNameInput.clear().sendKeys(value);
        }
    },

    createCompanyUserButton: {
        get: function () {
            return element(by.id("admin-input-createCompanyUser"));
        }
    },



    ensureAlertifyIsNotVisible: {
        value: function () {
            var EC = protractor.ExpectedConditions;
            browser.wait(EC.invisibilityOf(element(by.css(".alertify-logs"))), 5000);
        }
    },

    ensureAddingSucceeded: {
        value: function (typeOfUser) {
            var EC = protractor.ExpectedConditions;
            browser.wait(EC.visibilityOf(element(by.css(".alertify-logs"))), 5000);
            var text = element(by.css(".alertify-logs")).element(by.css('.success')).getText();
            return text === typeOfUser + "is created";
        }
    },

    ensureAddingFailed: {
        value: function (typeOfUser) {
            var EC = protractor.ExpectedConditions;
            browser.wait(EC.visibilityOf(element(by.css(".alertify-logs"))), 5000);
            var text = element(by.css(".alertify-logs")).element(by.css('.error')).getText();
            return text !== typeOfUser + "is created";
        }
    }
});

module.exports = AdminPage;