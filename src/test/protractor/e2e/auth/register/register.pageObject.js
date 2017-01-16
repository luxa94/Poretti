var RegisterPage = function() {};

RegisterPage.prototype = Object.create({}, {

    inputUsername: {
        get: function() {
            return element(by.id("register-input-username"));
        },
        set: function(value) {
            this.inputUsername.clear().sendKeys(value);
        }
    },

    inputEmail: {
        get: function() {
            return element(by.id("register-input-email"));
        },
        set: function(value) {
            this.inputEmail.clear().sendKeys(value);
        }
    },

    inputPassword: {
        get: function() {
            return element(by.id("register-input-password"))
        },
        set: function(value) {
            this.inputPassword.clear().sendKeys(value);
        }
    },

    inputName: {
        get: function() {
            return element(by.id("register-input-name"));
        },
        set: function(value) {
            this.inputName.clear().sendKeys(value);
        }
    },

    phoneNumbersChips: {
        get: function() {
            return element(by.id("register-chips-phoneNumbers"));
        },
        set: function(value) {
            this.phoneNumbersChips.sendKeys(value);
        }
    },

    contactEmailsChips: {
        get: function() {
            return element(by.id("register-chips-contactEmails"));
        },
        set: function(value) {
            this.contactEmailsChips.sendKeys(value);
        }
    },

    forCompanySpan: {
        get: function() {
            return element(by.id("register-span-forCompany"));
        }
    },

    companyList: {
        get: function() {
            return element.all(by.repeater('company in vm.currentDisplayingCompanies'));
        }
    },

    firstElementCheckbox: {
        get: function() {
            return this.companyList.get(0).element(by.tagName("md-checkbox"));
        }
    },

    checkEmailHeadline: {
        get: function() {
            return element(by.id("register-h2-checkEmail"));
        }
    },

    registerButton: {
        get: function() {
            return element(by.id("register-btn-register"));
        }
    },

    ensureIsRegistrated: {
        value: function() {
            browser.wait(function() {
                return browser.getCurrentUrl().then(function(url) {
                    return url === 'http://localhost:8080/#!/register';
                })
            }, 10000);
        }
    }

});

module.exports = RegisterPage;
