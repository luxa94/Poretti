var login = require('../../auth/login/login.preparation');
var Navbar = require('../../navbar/navbar.pageObject');
var AdvertiserPage = require('./advertiser.pageObject');
var UserEditDialog = require('../dialogs/companyEdit.dialog.pageObject');
var RealEstateDialog = require('../../advertisements/dialogs/realEstate.dialog.pageObject');
var AdvertisementDialog = require('../../advertisements/dialogs/advertisement.dialog.pageObject');
var AdvertisementEditDialog = require('../../advertisements/dialogs/advertisementEdit.dialog.pageObject');
var ReviewDialog = require('../../advertisements/dialogs/review.dialog.pageObject');



describe('Advertiser profile page', function () {
    var navbar;
    var advertiserPage;
    var userEditDialog;
    var realEstateDialog;
    var advertisementDialog;
    var advertisementEditDialog;
    var reviewDialog;

    beforeAll(function () {
        browser.get('http://localhost:8080');
        navbar = new Navbar();
        advertiserPage = new AdvertiserPage();
        userEditDialog = new UserEditDialog();
        realEstateDialog = new RealEstateDialog();
        advertisementDialog = new AdvertisementDialog();
        advertisementEditDialog = new AdvertisementEditDialog();
        reviewDialog = new ReviewDialog();
    });

    afterEach(function () {
        browser.executeScript('window.localStorage.clear();');
    });

    // it('should render proper ui when logged user is not advertiser', function() {
    //     browser.get('http://localhost:8080/#!/user/2');
    //
    //     expect(browser.getCurrentUrl()).toEqual('http://localhost:8080/#!/user/2');
    //
    //     expect(advertiserPage.editButton.isDisplayed()).toBe(false);
    //     expect(advertiserPage.realEstateButton.isDisplayed()).toBe(false);
    //     expect(advertiserPage.advertisementButton.isDisplayed()).toBe(false);
    //     expect(advertiserPage.reviewButton.isDisplayed()).toBe(true);
    //
    //     expect(advertiserPage.advertisementDetailsButton.isDisplayed()).toBe(true);
    //     expect(advertiserPage.advertisementEditButton.isDisplayed()).toBe(false);
    //     expect(advertiserPage.advertisementDeleteButton.isDisplayed()).toBe(false);
    //
    //     expect(advertiserPage.realEstateEditButton.isDisplayed()).toBe(false);
    //     expect(advertiserPage.realEstateDeleteButton.isDisplayed()).toBe(false);
    // });

    // it('should render proper ui when logged user is advertiser', function() {
    //     login.execLogin();
    //
    //     navbar.profileButton.click();
    //     navbar.ensureIsRedirectedToProfile();
    //
    //     expect(browser.getCurrentUrl()).toEqual('http://localhost:8080/#!/user/2');
    //
    //     expect(advertiserPage.editButton.isDisplayed()).toBe(true);
    //     expect(advertiserPage.realEstateButton.isDisplayed()).toBe(true);
    //     expect(advertiserPage.advertisementButton.isDisplayed()).toBe(true);
    //     expect(advertiserPage.reviewButton.isDisplayed()).toBe(false);
    //
    //     expect(advertiserPage.advertisementDetailsButton.isDisplayed()).toBe(true);
    //     expect(advertiserPage.advertisementEditButton.isDisplayed()).toBe(true);
    //     expect(advertiserPage.advertisementDeleteButton.isDisplayed()).toBe(true);
    //
    //     expect(advertiserPage.realEstateEditButton.isDisplayed()).toBe(true);
    //     expect(advertiserPage.realEstateDeleteButton.isDisplayed()).toBe(true);
    // });

    // it('should successfully add new review', function() {
    //     login.execLogin("monica", "monica");
    //
    //     browser.get('http://localhost:8080/#!/user/2');
    //
    //     advertiserPage.reviewButton.click();
    //
    //     expect(reviewDialog.commentTextarea.isDisplayed()).toBe(true);
    //     expect(reviewDialog.ratingInput.isDisplayed()).toBe(true);
    //     expect(reviewDialog.okButton.isDisplayed()).toBe(true);
    //     expect(reviewDialog.cancelButton.isDisplayed()).toBe(true);
    //
    //     reviewDialog.commentTextarea = "Just to check if this this works";
    //     reviewDialog.ratingInput = 5;
    //     reviewDialog.okButton.click();
    //
    //     advertiserPage.ensureReviewIsAdded(currentNumOfReviews);
    // });

    // it('should successfully add new real estate', function () {
    //     login.execLogin();
    //
    //     browser.get('http://localhost:8080/#!/user/2');
    //
    //     advertiserPage.realEstateButton.click();
    //
    //     expect(realEstateDialog.nameInput.isDisplayed()).toBe(true);
    //     expect(realEstateDialog.areaInput.isDisplayed()).toBe(true);
    //     expect(realEstateDialog.descriptionTextArea.isDisplayed()).toBe(true);
    //     expect(realEstateDialog.locationCity.isDisplayed()).toBe(true);
    //     expect(realEstateDialog.pinMap.isDisplayed()).toBe(true);
    //
    //     realEstateDialog.nameInput = "Dom Zivojin Culum";
    //     realEstateDialog.areaInput = 1000;
    //     realEstateDialog.descriptionTextArea = "Just to check if this thing works";
    //     realEstateDialog.typeSelect = "HOUSE";
    //     realEstateDialog.locationCity = "Novi Sad";
    //     realEstateDialog.okButton.click();
    //
    //     //TODO figure out how to test maps
    //     realEstateDialog.ensureRealEstateIsAdded(advertiserPage.realEstateList, 3);
    // });
    //
    it('should successfully add new advertisement for real estate', function () {
        login.execLogin();

        browser.get('http://localhost:8080/#!/user/2');

        advertiserPage.advertisementButton.click();

        expect(advertisementDialog.titleInput.isDisplayed()).toBe(true);
        expect(advertisementDialog.priceInput.isDisplayed()).toBe(true);
        expect(advertisementDialog.currencySelect.isDisplayed()).toBe(true);
        expect(advertisementDialog.typeSelect.isDisplayed()).toBe(true);
        expect(advertisementDialog.endsOnDatepicker.isDisplayed()).toBe(true);
        expect(advertisementDialog.addRealEstateButton.isDisplayed()).toBe(true);
        expect(advertisementDialog.chooseRealEstateButton.isDisplayed()).toBe(true);
        expect(advertisementDialog.addedRealEstateSpan.isPresent()).toBe(false);
        expect(advertisementDialog.okButton.isDisplayed()).toBe(true);
        expect(advertisementDialog.cancelButton.isDisplayed()).toBe(true);


        advertisementDialog.titleInput = "Just to check if this thing works";
        advertisementDialog.priceInput = 1000;
        advertisementDialog.currencySelect = "RSD";
        advertisementDialog.typeSelect = "RENT";

        advertisementDialog.addRealEstateButton.click();


        expect(realEstateDialog.nameInput.isDisplayed()).toBe(true);
        expect(realEstateDialog.areaInput.isDisplayed()).toBe(true);
        expect(realEstateDialog.descriptionTextArea.isDisplayed()).toBe(true);
        expect(realEstateDialog.locationCity.isDisplayed()).toBe(true);
        expect(realEstateDialog.pinMap.isDisplayed()).toBe(true);

        realEstateDialog.nameInput = "Dom Car Lazar";
        realEstateDialog.areaInput = 1000;
        realEstateDialog.descriptionTextArea = "Just to check if this thing works";
        realEstateDialog.typeSelect = "HOUSE";
        realEstateDialog.locationCity = "Novi Sad";
        realEstateDialog.okButton.click();

        expect(advertisementDialog.okButton.isDisplayed()).toBe(true);
        expect(advertisementDialog.addedRealEstateSpan.isDisplayed()).toBe(true);
        expect(advertisementDialog.addedRealEstateSpan.getText()).toContain("You added this real estate");

        advertisementDialog.okButton.click();

        advertisementDialog.ensureAdvertisementIsAdded(advertiserPage.advertisementList, 6);
    });

    // it('should successfully edit advertisement', function() {
    //
    // });
    //
    // it('should successfully edit real estate', function() {
    //
    // });
    //
    // it('should successfully edit user data', function() {
    //
    // });
    //
    // it('should go to the advertisement page', function() {
    //     browser.get('http://localhost:8080/#!/user/2');
    //
    //     advertiserPage.advertisementDetailsButton.click();
    //
    //     advertiserPage.ensureIsRedirectedToAdvertisement();
    // });
    //
    // it('should go to the company page', function() {
    //
    // });
});