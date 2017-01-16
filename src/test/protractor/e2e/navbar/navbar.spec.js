var Navbar = require('./navbar.pageObject.js');
var login = require('../auth/login/login.preparation.js');

describe('Navbar', function () {

    var navbar;

    beforeAll(function () {
        browser.get('http://localhost:8080');
        navbar = new Navbar();
    });

    afterEach(function() {
        // browser.executeScript(" return window.localStorage.getItem('porettiUser'); ")
        // .then(function(item) {
        //     if (item) {
        //         navbar.loginButton.click();
        //     }
        // });
        browser.executeScript('window.localStorage.clear();');
    });

    it('should render ui for not logged user', function () {

        expect(navbar.loginButton.isDisplayed()).toBe(true);
        expect(navbar.registerButton.isDisplayed()).toBe(true);
        expect(navbar.profileButton.isPresent()).toBe(false);
        expect(navbar.logoutButton.isPresent()).toBe(false);
    });

    it('should render ui for logged user', function () {
        login.execLogin();

        expect(navbar.profileButton.isDisplayed()).toBe(true);
        expect(navbar.logoutButton.isDisplayed()).toBe(true);
        expect(navbar.loginButton.isPresent()).toBe(false);
        expect(navbar.registerButton.isPresent()).toBe(false);
    });



});