var NavbarPage = function() {};

NavbarPage.prototype = Object.create({}, {

    loginButton: {
        get: function() {
            return element(by.id("navbar-btn-login"));
        }
    },

    registerButton: {
        get: function() {
            return element(by.id("navbar-btn-register"))
        }
    },

    profileButton: {
        get: function() {
            return element(by.id("navbar-btn-profile"))
        }
    },

    logoutButton: {
        get: function() {
            return element(by.id("navbar-btn-logout"))
        }
    },

    ensureIsRedirectedToProfile: {
        value: function() {
            browser.wait(function(){
                return browser.getCurrentUrl().then(function(url) {
                    var advertiser = url.indexOf('http://localhost:8080/#!/user') !== -1;
                    var admin = url.indexOf('http://localhost:8080/#!/admin') !== -1;
                    var verifier = url.indexOf('http://localhost:8080/#!/verifier') !== -1;
                    var company = url.indexOf('http://localhost:8080/#!/company') !== -1;

                    return advertiser || admin || verifier || company;
                });
            }, 15000);
        }
    }
});

module.exports = NavbarPage;