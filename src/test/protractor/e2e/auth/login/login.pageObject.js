var LoginPage = function() {};

LoginPage.prototype = Object.create({}, {

    inputUsername: {
        get: function() {
            return element(by.id("login-input-username"));
        },
        set: function(value) {
            this.inputUsername.clear().sendKeys(value);
        }
    },

    inputPassword: {
        get: function() {
            return element(by.id("login-input-password"))
        },
        set: function(value) {
            this.inputPassword.clear().sendKeys(value);
        }
    },

    loginButton: {
        get: function() {
            return element(by.id("login-btn-login"))
        }
    },

    ensureIsSuccessfullyRedirected: {
        value: function() {
            browser.wait(function() {
                return browser.getCurrentUrl().then(function(url) {
                    return url === 'http://localhost:8080/#!/home';
                })
            }, 10000);
        }
    },

    ensureIsNotLoggedIn: {
        value: function() {
            browser.wait(function() {
                return browser.getCurrentUrl().then(function(url) {
                    return url === 'http://localhost:8080/#!/login';
                })
            }, 10000);
        }
    }

});

module.exports = LoginPage;
