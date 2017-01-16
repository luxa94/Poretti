var Navbar = require("../../navbar/navbar.pageObject");
var RegisterPage = require("./register.pageObject");

describe('Register', function () {
    var navbar;
    var registerPage;

    beforeAll(function () {
        browser.get('http://localhost:8080');
        navbar = new Navbar();
        registerPage = new RegisterPage();
    });

    afterEach(function () {
        browser.executeScript('window.localStorage.clear();');
    });

    it('should successfully register user', function () {
        navbar.registerButton.click();

        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#!/register");

        registerPage.inputUsername = "Rika";
        registerPage.inputEmail = "Rika@someEmail.com";
        registerPage.inputPassword = "mika";
        registerPage.inputName = "Mika Nije Zika";
        //TODO : figure out how to test this because it's material design
       // registerPage.phoneNumbersChips = "021-34-34-34";
       // registerPage.contactEmailsChips = "nijeZikin@email.com";

        registerPage.registerButton.click();

        expect(registerPage.checkEmailHeadline.isDisplayed()).toBe(true);
    });

    it('should successfully register company user', function () {
        browser.get('http://localhost:8080');
        navbar.registerButton.click();

        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#!/register");

        registerPage.inputUsername = "Pika";
        registerPage.inputEmail = "pika@someEmail.com";
        registerPage.inputPassword = "zika";
        registerPage.inputName = "Zika nije Mika";
        registerPage.forCompanySpan.click();

        registerPage.firstElementCheckbox.click();

        registerPage.registerButton.click();

        expect(registerPage.checkEmailHeadline.isDisplayed()).toBe(true);
    });
});