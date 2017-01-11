var LoginPage = function() {};

LoginPage.prototype = Object.create({}, {

    inputUsername: {
        get: function() {
            return element(by.id("login.input.username"));
        }
    },

    inputPassword: {
        get: function() {
            return element(by.id("login.input.password"))
        }
    },

    loginButton: {
        get: function() {
            return element(by.id("login.btn.login"))
        }
    }
});

module.exports = LoginPage;
