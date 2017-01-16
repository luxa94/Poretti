var Navbar = require("../../navbar/navbar.pageObject");
var LoginPage = require("./login.pageObject");

describe('User logs in', function () {
    var navbar;
    var loginPage;

    beforeAll(function () {
        browser.get('http://localhost:8080');
        navbar = new Navbar();
        loginPage = new LoginPage();
    });

    afterEach(function () {
        browser.executeScript('window.localStorage.clear();');
    });

    it('should successfully login with right credentials', function () {
        navbar.loginButton.click();

        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#!/login");

        loginPage.inputUsername = "test_user";
        loginPage.inputPassword = "admin";
        loginPage.loginButton.click();

        loginPage.ensureIsSuccessfullyRedirected();
    });

    it('should not login with wrong credentials', function () {
        browser.get('http://localhost:8080/#!/login');

        loginPage.inputUsername = "not_test_user";
        loginPage.inputPassword = "not_admin";
        loginPage.loginButton.click();

        loginPage.ensureIsNotLoggedIn();
    });

    it('should not login when email is not confirmed', function () {
        browser.get('http://localhost:8080/#!/login');

        loginPage.inputUsername = "test_test_user";
        loginPage.inputPassword = "admin";
        loginPage.loginButton.click();

        loginPage.ensureIsNotLoggedIn();
    });
});
