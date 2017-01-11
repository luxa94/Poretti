var Navbar = require('./navbar.pageObject.js');

describe('Navbar', function() {

    var navbar;

    beforeAll(function() {
        browser.get('http://localhost:8080');
        navbar = new Navbar();
    });

    it('should render ui for not logged user', function() {

        expect(navbar.loginButton.isDisplayed()).toBe(true);
        expect(navbar.registerButton.isDisplayed()).toBe(true);
        expect(navbar.profileButton.isPresent()).toBe(false);
        expect(navbar.logoutButton.isPresent()).toBe(false);
    });

});