var Login = require("./login.pageObject.js");
var navBar = require('./../../navbar/navbar.pageObject');

exports.execLogin = function(username, password) {
    var login = new Login();
    var testUsername = username ? username : 'test_user';
    var testPassword = password ? password : 'admin';
    
    browser.driver.get("http://localhost:8080/#!/login");

    login.inputUsername = testUsername;
    login.inputPassword = testPassword;
    login.loginButton.click();

    login.ensureIsSuccessfullyRedirected();

    browser.executeScript(" return window.localStorage.getItem('porettiUser'); ").then(function(item) {
        expect(item).not.toEqual('');
    });
};

exports.execLogout = function() {
    var login = new Login();
    login.ensureIsSuccessfullyRedirected();
    browser.executeScript(" return window.localStorage.getItem('porettiUser'); ").then(function(item) {
        expect(item).not.toEqual('');
    }).catch(function(error) {
        expect(error).not.toBeDefined();
    });
};