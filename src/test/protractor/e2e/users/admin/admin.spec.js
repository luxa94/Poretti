var AdminPage = require('./admin.pageObject');
var login = require('../../auth/login/login.preparation');

describe('Admin page', function() {
   var adminPage;

    beforeAll(function() {
        login.execLogin("admin", "admin");
        adminPage = new AdminPage();
    });

    afterAll(function() {
        browser.executeScript('window.localStorage.clear();');
    });

    it('should successfully create new admin', function() {

        expect(browser.getCurrentUrl()).toEqual('http://localhost:8080/#!/admin/1');

        adminPage.newUserUsernameInput = "adminko2";
        adminPage.newUserPasswordInput = "adminko";
        adminPage.newUserEmailInput = "adminko2@com.com";
        adminPage.newUserNameInput = "Aminko adminkovic";
        adminPage.newUserRoleSelect = "ADMIN";
        adminPage.createAdminVerifierButton.click();
        //TODO try to get text from dialog;
        expect(browser.getCurrentUrl()).toEqual('http://localhost:8080/#!/admin/1');
    });

    it('should successfully create new verifier', function() {
        expect(browser.getCurrentUrl()).toEqual('http://localhost:8080/#!/admin/1');

        adminPage.newUserUsernameInput = "verifiarko2";
        adminPage.newUserPasswordInput = "verifiarko";
        adminPage.newUserEmailInput = "verifiarko2@com.com";
        adminPage.newUserNameInput = "verifiarko verifiarko";
        adminPage.newUserRoleSelect = "VERIFIER";
        adminPage.createAdminVerifierButton.click();

        //TODO try to get text from dialog;
        expect(browser.getCurrentUrl()).toEqual('http://localhost:8080/#!/admin/1');
    });
    //
    // it('should successfully create new company', function() {
    //
    // });

});
