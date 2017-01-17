var AdminPage = require('./admin.pageObject');
var login = require('../../auth/login/login.preparation');

describe('Admin page', function() {
   var adminPage;

    beforeAll(function() {
        login.execLogin("admin", "admin");
        adminPage = new AdminPage();
    });

    beforeEach(function() {
        adminPage.ensureAlertifyIsNotVisible();
    });

    afterAll(function() {
        browser.executeScript('window.localStorage.clear();');
    });

    it('should successfully create new admin', function() {

        expect(browser.getCurrentUrl()).toEqual('http://localhost:8080/#!/admin/1');

        adminPage.newUserUsernameInput = "adminko5";
        adminPage.newUserPasswordInput = "adminko";
        adminPage.newUserEmailInput = "adminko5@com.com";
        adminPage.newUserNameInput = "Aminko adminkovic";
        adminPage.newUserRoleSelect = "ADMIN";
        adminPage.createAdminVerifierButton.click();

        adminPage.ensureAddingFailed("Admin");
    });

    it('should successfully create new verifier', function() {
        expect(browser.getCurrentUrl()).toEqual('http://localhost:8080/#!/admin/1');

        adminPage.newUserUsernameInput = "verifier1";
        adminPage.newUserPasswordInput = "verifiarko";
        adminPage.newUserEmailInput = "verifier1@com.com";
        adminPage.newUserNameInput = "verifiarko verifiarko";
        adminPage.newUserRoleSelect = "VERIFIER";
        adminPage.createAdminVerifierButton.click();

        adminPage.ensureAddingFailed("Verifier");
    });

    it('should successfully create new company', function() {
        expect(browser.getCurrentUrl()).toEqual('http://localhost:8080/#!/admin/1');

        adminPage.companyPibInput = "12345678912";
        adminPage.companyNameInput = "Dom AAA";

        adminPage.locationCityInput = "Novi Sad";
        adminPage.locationCityAreaInput = "Liman 3";
        adminPage.locationZipCodeInput = 21000;
        adminPage.locationStreetInput = "Despota Stefana";
        adminPage.locationStreetNumberInput = "5a";

        adminPage.addCompanyUserButton.click();

        adminPage.companyUserUsernameInput = "slavkooo";
        adminPage.companyUserPasswordInput = "slavkooo";
        adminPage.companyUserEmailInput = "slavkooo@com.com";
        adminPage.companyUserNameInput = "slavko slavko";

        adminPage.createCompanyUserButton.click();

        expect(adminPage.addedCompanyUserSpan.getText()).not.toEqual("");

        adminPage.createCompanyButton.click();

        adminPage.ensureAddingSucceeded("Company");
    });

});
