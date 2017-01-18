var Navbar = require("../../navbar/navbar.pageObject");
var AdvertisementPage = require("./advertisement.pageObject");
var ReviewDialog = require('../dialogs/review.dialog.pageObject');
var ReportDialog = require('../dialogs/report.dialog.pageObject');
var login = require('../../auth/login/login.preparation');

var existingId = 1;
var existingTitle = "Advertisement title";
var existingPriceAndCurrency = "3000 RSD";
var existingAdvertisementType = "SALE";
var nonExistingId= -1;
var currentNumOfReports = 17;
var currentNumOfReviews = 1;

describe("One advertisement", function() {
    var navbar;
    var advertisementPage;
    var reviewDialog;
    var reportDialog;

    beforeAll(function() {
        browser.get('http://localhost:8080');
        navbar = new Navbar();
        advertisementPage = new AdvertisementPage();
        reviewDialog = new ReviewDialog();
        reportDialog = new ReportDialog();
    });

    afterEach(function() {
        browser.executeScript('window.localStorage.clear();');
    });

    // it('should show info when advertisement exists', function() {
    //     browser.get('http://localhost:8080/#!/advertisement/' + existingId);
    //
    //     expect(advertisementPage.advertisementTitleSpan.getText()).toEqual(existingTitle);
    //     expect(advertisementPage.advertisementTypeSpan.getText()).toContain(existingAdvertisementType);
    //     expect(advertisementPage.advertisementPriceAndCurrencySpan.getText()).toEqual(existingPriceAndCurrency);
    //
    // });
    //
    // it('should not show info page when advertisement does not exists', function() {
    //     browser.get('http://localhost:8080/#!/advertisement/' + nonExistingId);
    //
    //     expect(advertisementPage.advertisementTitleSpan.isPresent()).toBe(false);
    //     expect(advertisementPage.advertisementTypeSpan.isPresent()).toBe(false);
    //     expect(advertisementPage.advertisementPriceAndCurrencySpan.isPresent()).toBe(false);
    // });
    //
    // it('should not show button for creating report when user is not logged', function() {
    //     browser.get('http://localhost:8080/#!/advertisement/' + existingId);
    //
    //     expect(advertisementPage.advertisementReportButton.isPresent()).toBe(false);
    //
    // });
    //
    // it('should not show button for creating report when logged user is owner', function() {
    //     login.execLogin();
    //
    //     browser.get('http://localhost:8080/#!/advertisement/' + existingId);
    //
    //     expect(advertisementPage.advertisementReportButton.isPresent()).toBe(false);
    // });
    //
    // it('should successfully create report when logged user is not owner', function() {
    //     login.execLogin("monica", "monica");
    //
    //     browser.get('http://localhost:8080/#!/advertisement/' + existingId);
    //
    //     advertisementPage.advertisementReportButton.click();
    //
    //     expect(reportDialog.reasonSelect.isDisplayed()).toBe(true);
    //     expect(reportDialog.descriptionTextarea.isDisplayed()).toBe(true);
    //     expect(reportDialog.okButton.isDisplayed()).toBe(true);
    //     expect(reportDialog.cancelButton.isDisplayed()).toBe(true);
    //
    //     reportDialog.reasonSelect = "OTHER";
    //     reportDialog.descriptionTextarea = "Just for no reason";
    //     reportDialog.okButton.click();
    //
    //     advertisementPage.ensureReportIsAdded(currentNumOfReports);
    // });
    //
    // it('should not show button for creating review when user is not logged', function() {
    //     browser.get('http://localhost:8080/#!/advertisement/' + existingId);
    //
    //     expect(advertisementPage.advertisementReviewButton.isPresent()).toBe(false);
    // });
    //
    // it('should not show button for creating review when logged user is owner', function() {
    //     login.execLogin();
    //
    //     browser.get('http://localhost:8080/#!/advertisement/' + existingId);
    //
    //     expect(advertisementPage.advertisementReviewButton.isPresent()).toBe(false);
    // });
    //
    it('should successfully create review when logged user is not owner', function() {
        login.execLogin("monica", "monica");

        browser.get('http://localhost:8080/#!/advertisement/' + existingId);

        advertisementPage.advertisementReviewButton.click();

        expect(reviewDialog.commentTextarea.isDisplayed()).toBe(true);
        expect(reviewDialog.ratingInput.isDisplayed()).toBe(true);
        expect(reviewDialog.okButton.isDisplayed()).toBe(true);
        expect(reviewDialog.cancelButton.isDisplayed()).toBe(true);

        reviewDialog.commentTextarea = "Just to check if this thing works";
        reviewDialog.ratingInput = 5;
        reviewDialog.okButton.click();

        advertisementPage.ensureReviewIsAdded(currentNumOfReviews);
    });
    //
    // it('should not show button for finishing advertisement when logged user is not owner', function() {
    //
    // });
    //
    // it('should successfully finish advertisement when logged user is owner', function() {
    //
    // });

});