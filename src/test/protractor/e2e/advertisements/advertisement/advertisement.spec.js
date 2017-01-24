var Navbar = require("../../navbar/navbar.pageObject");
var AdvertisementPage = require("./advertisement.pageObject");
var ReviewDialog = require('../dialogs/review.dialog.pageObject');
var ReportDialog = require('../dialogs/report.dialog.pageObject');
var login = require('../../auth/login/login.preparation');

var advertisementUrl = 'http://localhost:8080/#!/advertisement/9';
var nonExistingAdvertisementUrl = 'http://localhost:8080/#!/advertisement/-1';
var existingTitle = "Advertisement title";
var existingPriceAndCurrency = "3000 RSD";
var existingAdvertisementType = "SALE";


describe("One advertisement", function() {
    var navbar;
    var advertisementPage;
    var reviewDialog;
    var reportDialog;
    var numberOfReviews = {
        numberValue : 0
    };

    var numberOfReports = {
        numberValue: 0
    };

    beforeAll(function() {
        browser.get('http://localhost:8080');
        navbar = new Navbar();
        advertisementPage = new AdvertisementPage();
        reviewDialog = new ReviewDialog();
        reportDialog = new ReportDialog();
    });

    beforeEach(function() {
        advertisementPage.getNumberOfReports(numberOfReports);
        advertisementPage.getNumberOfReviews(numberOfReviews);
    });

    afterEach(function() {
        browser.executeScript('window.localStorage.clear();');
    });

    it('should show info when advertisement exists', function() {
        browser.get(advertisementUrl);

        expect(advertisementPage.advertisementTitleSpan.getText()).toEqual(existingTitle);
        expect(advertisementPage.advertisementTypeSpan.getText()).toContain(existingAdvertisementType);
        expect(advertisementPage.advertisementPriceAndCurrencySpan.getText()).toEqual(existingPriceAndCurrency);

    });

    it('should not show info page when advertisement does not exists', function() {
        browser.get(nonExistingAdvertisementUrl);

        expect(advertisementPage.advertisementTitleSpan.isPresent()).toBe(false);
        expect(advertisementPage.advertisementTypeSpan.isPresent()).toBe(false);
        expect(advertisementPage.advertisementPriceAndCurrencySpan.isPresent()).toBe(false);
    });

    it('should not show button for creating report when user is not logged', function() {
        browser.get(advertisementUrl);

        expect(advertisementPage.advertisementReportButton.isPresent()).toBe(false);

    });

    it('should not show button for creating report when logged user is owner', function() {
        login.execLogin("chandler", "asd");

        browser.get(advertisementUrl);

        expect(advertisementPage.advertisementReportButton.isPresent()).toBe(false);
    });

    it('should successfully create report when logged user is not owner', function() {
        login.execLogin("joey", "asd");

        browser.get(advertisementUrl);

        advertisementPage.advertisementReportButton.click();

        expect(reportDialog.reasonSelect.isDisplayed()).toBe(true);
        expect(reportDialog.descriptionTextarea.isDisplayed()).toBe(true);
        expect(reportDialog.okButton.isDisplayed()).toBe(true);
        expect(reportDialog.cancelButton.isDisplayed()).toBe(true);

        reportDialog.reasonSelect = "OTHER";
        reportDialog.descriptionTextarea = "Just for no reason";
        reportDialog.okButton.click();

        advertisementPage.ensureReportIsAdded(numberOfReports);
    });

    it('should not show button for creating review when user is not logged', function() {
        browser.get(advertisementUrl);

        expect(advertisementPage.advertisementReviewButton.isPresent()).toBe(false);
    });

    it('should not show button for creating review when logged user is owner', function() {
        login.execLogin("chandler", "asd");

        browser.get(advertisementUrl);

        expect(advertisementPage.advertisementReviewButton.isPresent()).toBe(false);
    });

    it('should successfully create review when logged user is not owner', function() {
        login.execLogin("joey", "asd");

        browser.get(advertisementUrl);

        advertisementPage.advertisementReviewButton.click();

        expect(reviewDialog.commentTextarea.isDisplayed()).toBe(true);
        expect(reviewDialog.ratingInput.isDisplayed()).toBe(true);
        expect(reviewDialog.okButton.isDisplayed()).toBe(true);
        expect(reviewDialog.cancelButton.isDisplayed()).toBe(true);

        reviewDialog.commentTextarea = "Just to check if this thing works";
        reviewDialog.ratingInput = 5;
        reviewDialog.okButton.click();

        advertisementPage.ensureReviewIsAdded(numberOfReviews);
    });
});