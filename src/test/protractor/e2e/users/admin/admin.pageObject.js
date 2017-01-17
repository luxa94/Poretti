var AdminPage = function() {};

AdminPage.prototype = Object.create({}, {

    newUserUsernameInput: {
        get : function() {
            return element(by.id("admin-input-newUser-username"));
        },
        set: function(value) {
            return this.newUserUsernameInput.clear().sendKeys(value);
        }
    },

    newUserPasswordInput: {
        get : function() {
            return element(by.id("admin-input-newUser-password"));
        },
        set: function(value) {
            return this.newUserPasswordInput.clear().sendKeys(value);
        }
    },

    newUserEmailInput: {
        get : function() {
            return element(by.id("admin-input-newUser-email"));
        },
        set: function(value) {
            return this.newUserEmailInput.clear().sendKeys(value);
        }
    },

    newUserNameInput: {
        get : function() {
            return element(by.id("admin-input-newUser-name"));
        },
        set: function(value) {
            return this.newUserNameInput.clear().sendKeys(value);
        }
    },

    newUserRoleSelect: {
        get : function() {
            return element(by.id("admin-select-newUser-role"));
        },
        set: function(value) {
            return this.newUserRoleSelect.sendKeys(value);
        }
    },

    createAdminVerifierButton: {
        get: function () {
            return element(by.id("admin-btn-createAdminVerifier"));
        }
    }
});

module.exports = AdminPage;