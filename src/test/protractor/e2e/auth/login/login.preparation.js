var Login = require("./login.pageObject.js");

exports.execLogin = function() {
    var login = new Login();

    browser.driver.get("http://localhost:8080/#!/login");

    login.inputUsername = "test_user";
    login.inputPassword = "admin";
    login.loginButton.click();

    login.ensureIsSuccessfullyRedirected();

    browser.executeScript(" return window.localStorage.getItem('porettiUser'); ").then(function(item) {
        expect(item).not.toEqual('');
    });
};

exports.execLogout = function() {
    // var login = Login();
    //
    // login.logoutButton.click();
    //
    // login.ensureIsSuccessfullyRedirected();
    //
    // browser.manage().getCookie('porettiUser').then(function (cookie) {
    //     expect(cookie).toBeUndefined();
    // });
};