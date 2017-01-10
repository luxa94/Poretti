var NavbarPage = function() {}

NavbarPage.prototype = Object.create({}, {

    loginButton: {
        get: function() {
            return element(by.id("navbar.btn.login"));
        }
    },

    registerButton: {
        get: function() {
            return element(by.id("navbar.btn.register"))
        }
    },

    profileButton: {
        get: function() {
            return element(by.id("navbar.btn.profile"))
        }
    },

    logoutButton: {
        get: function() {
            return element(by.id("navbar.btn.logout"))
        }
    }
});

module.exports = NavbarPage;