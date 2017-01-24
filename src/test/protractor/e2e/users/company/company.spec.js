'use strict';

var login = require('../../auth/login/login.preparation');
var Navbar = require('../../navbar/navbar.pageObject');
var CompanyPage = require('./company.pageObject');
var CompanyEditDialog = require('../dialogs/companyEdit.dialog.pageObject');
var RealEstateDialog = require('../../advertisements/dialogs/realEstate.dialog.pageObject');
var RealEstatesDialog = require('../../advertisements/dialogs/realEstates.dialog.pageObject');
var AdvertisementDialog = require('../../advertisements/dialogs/advertisement.dialog.pageObject');
var AdvertisementEditDialog = require('../../advertisements/dialogs/advertisementEdit.dialog.pageObject');
var ReviewDialog = require('../../advertisements/dialogs/review.dialog.pageObject');

var existingCompanyUrl = 'http://localhost:8080/#!/company/10';

describe('Company profile page', function () {
    var navbar;
    var companyPage;
    var companyEditDialog;
    var realEstateDialog;
    var realEstatesDialog;
    var advertisementDialog;
    var advertisementEditDialog;
    var reviewDialog;
    var numberOfAdvertisements = {
        numberValue: 0
    };

    var numberOfRealEstates = {
        numberValue: 0
    };

    var numberOfReviews = {
        numberValue: 0
    };

    var numberOfCompanyMembers = {
        numberValue: 0
    };

    var numberOfNotApprovedMembers = {
        numberValue: 0
    };

    beforeAll(function () {
        browser.get('http://localhost:8080');
        navbar = new Navbar();
        companyPage = new CompanyPage();
        companyEditDialog = new CompanyEditDialog();
        realEstateDialog = new RealEstateDialog();
        realEstatesDialog = new RealEstatesDialog();
        advertisementDialog = new AdvertisementDialog();
        advertisementEditDialog = new AdvertisementEditDialog();
        reviewDialog = new ReviewDialog();

    });

    beforeEach(function () {
        companyPage.getNumberOfAdvertisements(numberOfAdvertisements);
    });

    afterEach(function () {
        browser.executeScript('window.localStorage.clear();');
    });

    it('should render proper ui when logged user is not company member', function () {
        browser.get(existingCompanyUrl);

        expect(browser.getCurrentUrl()).toEqual(existingCompanyUrl);

        expect(companyPage.editButton.isDisplayed()).toBe(false);
        expect(companyPage.realEstateButton.isDisplayed()).toBe(false);
        expect(companyPage.advertisementButton.isDisplayed()).toBe(false);
        expect(companyPage.leaveButton.isPresent()).toBe(false);
        expect(companyPage.joinButton.isPresent()).toBe(false);
        expect(companyPage.reviewButton.isDisplayed()).toBe(true);

        expect(companyPage.advertisementDetailsButton.isDisplayed()).toBe(true);
        expect(companyPage.advertisementEditButton.isDisplayed()).toBe(false);
        expect(companyPage.advertisementDeleteButton.isDisplayed()).toBe(false);

        expect(companyPage.realEstateEditButton.isDisplayed()).toBe(false);
        expect(companyPage.realEstateDeleteButton.isDisplayed()).toBe(false);
    });

    it('should render proper ui when logged user is company not approved member', function() {
        browser.get(existingCompanyUrl);

        expect(browser.getCurrentUrl()).toEqual(existingCompanyUrl);

        expect(companyPage.editButton.isDisplayed()).toBe(false);
        expect(companyPage.realEstateButton.isDisplayed()).toBe(false);
        expect(companyPage.advertisementButton.isDisplayed()).toBe(false);
        expect(companyPage.leaveButton.isPresent()).toBe(true);
        expect(companyPage.joinButton.isPresent()).toBe(false);
        expect(companyPage.reviewButton.isDisplayed()).toBe(true);

        expect(companyPage.advertisementDetailsButton.isDisplayed()).toBe(true);
        expect(companyPage.advertisementEditButton.isDisplayed()).toBe(false);
        expect(companyPage.advertisementDeleteButton.isDisplayed()).toBe(false);

        expect(companyPage.realEstateEditButton.isDisplayed()).toBe(false);
        expect(companyPage.realEstateDeleteButton.isDisplayed()).toBe(false);
    });

    it('should render proper ui when logged user is accepted company member', function () {
        login.execLogin("chandler", "asd");

        browser.get(existingCompanyUrl);

        expect(browser.getCurrentUrl()).toEqual(existingCompanyUrl);

        expect(companyPage.editButton.isDisplayed()).toBe(true);
        expect(companyPage.realEstateButton.isDisplayed()).toBe(true);
        expect(companyPage.advertisementButton.isDisplayed()).toBe(true);
        expect(companyPage.leaveButton.isPresent()).toBe(true);
        expect(companyPage.joinButton.isPresent()).toBe(false);
        expect(companyPage.reviewButton.isDisplayed()).toBe(false);

        expect(companyPage.advertisementDetailsButton.isDisplayed()).toBe(true);
        expect(companyPage.advertisementEditButton.isDisplayed()).toBe(true);
        expect(companyPage.advertisementDeleteButton.isDisplayed()).toBe(true);

        companyPage.realEstatesTab.click();

        expect(companyPage.realEstateEditButton.isDisplayed()).toBe(true);
        expect(companyPage.realEstateDeleteButton.isDisplayed()).toBe(true);
    });

    it('should successfully join new user into company', function() {
        login.execLogin("fluffy", "asd");

        browser.get(existingCompanyUrl);

        expect(browser.getCurrentUrl()).toEqual(existingCompanyUrl);

        companyPage.companyMembersTab.click();
        companyPage.getNumberOfCompanyMembers(numberOfCompanyMembers).then(function() {
            companyPage.joinButton.click();

            companyPage.ensureUserIsAdded(numberOfCompanyMembers);
        });
    });

    it('should successfully delete user from company', function() {
        login.execLogin("fluffy", "asd");

        browser.get(existingCompanyUrl);

        expect(browser.getCurrentUrl()).toEqual(existingCompanyUrl);

        companyPage.companyMembersTab.click();
        companyPage.getNumberOfCompanyMembers(numberOfCompanyMembers).then(function() {
            companyPage.leaveButton.click();

            companyPage.ensureUserIsDeleted(numberOfCompanyMembers);
        });
    });

    it('should successfully approve user for company', function() {
        login.execLogin("chandler", "asd");

        browser.get(existingCompanyUrl);

        expect(browser.getCurrentUrl()).toEqual(existingCompanyUrl);

        companyPage.companyMembersTab.click();
        companyPage.getNumberOfNotApprovedMembers(numberOfNotApprovedMembers).then(function() {
            companyPage.approveButton.click();

            companyPage.ensureUserIsApproved(numberOfCompanyMembers);
        });
    });

    it('should successfully add new review', function() {
        login.execLogin("joey", "asd");

        browser.get(existingCompanyUrl);

        companyPage.reviewsTab.click();

        companyPage.getNumberOfReviews(numberOfReviews).then(function() {
            companyPage.reviewButton.click();

            expect(reviewDialog.commentTextarea.isDisplayed()).toBe(true);
            expect(reviewDialog.ratingInput.isDisplayed()).toBe(true);
            expect(reviewDialog.okButton.isDisplayed()).toBe(true);
            expect(reviewDialog.cancelButton.isDisplayed()).toBe(true);

            reviewDialog.commentTextarea = "Just to check if this this works";
            reviewDialog.ratingInput = 5;
            reviewDialog.okButton.click();

            reviewDialog.ensureReviewIsAdded(companyPage.reviewList, numberOfReviews);
        });
    });

    it('should successfully add new real estate', function () {
        login.execLogin("chandler", "asd");

        browser.get(existingCompanyUrl);

        companyPage.realEstatesTab.click();

        companyPage.getNumberOfRealEstates(numberOfRealEstates).then(function() {
            companyPage.realEstateButton.click();

            expect(realEstateDialog.nameInput.isDisplayed()).toBe(true);
            expect(realEstateDialog.areaInput.isDisplayed()).toBe(true);
            expect(realEstateDialog.descriptionTextArea.isDisplayed()).toBe(true);
            expect(realEstateDialog.locationCity.isDisplayed()).toBe(true);
            expect(realEstateDialog.pinMap.isDisplayed()).toBe(true);

            realEstateDialog.nameInput = "Dom Zivojin Culum";
            realEstateDialog.areaInput = 1000;
            realEstateDialog.descriptionTextArea = "Just to check if this thing works";
            realEstateDialog.typeSelect = "HOUSE";
            realEstateDialog.locationCity = "Novi Sad";
            realEstateDialog.okButton.click();


            realEstateDialog.ensureRealEstateIsAdded(companyPage.realEstateList, numberOfRealEstates);
        });
    });

    it('should successfully add new advertisement and real estate', function () {
        login.execLogin("chandler", "asd");

        browser.get(existingCompanyUrl);

        companyPage.advertisementButton.click();

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

        advertisementDialog.ensureAdvertisementIsAdded(companyPage.advertisementList, numberOfAdvertisements);
    });

    it('should successfully add new advertisement for real estate', function () {
        login.execLogin("chandler", "asd");

        browser.get(existingCompanyUrl);

        companyPage.advertisementButton.click();

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

        advertisementDialog.chooseRealEstateButton.click();

        expect(realEstatesDialog.okButton.isDisplayed()).toBe(true);
        expect(realEstatesDialog.cancelButton.isDisplayed()).toBe(true);

        realEstatesDialog.firstRealEstate.click();

        realEstatesDialog.okButton.click();

        expect(advertisementDialog.okButton.isDisplayed()).toBe(true);
        expect(advertisementDialog.addedRealEstateSpan.isDisplayed()).toBe(true);
        expect(advertisementDialog.addedRealEstateSpan.getText()).toContain("You added this real estate");

        advertisementDialog.okButton.click();

        advertisementDialog.ensureAdvertisementIsAdded(companyPage.advertisementList, numberOfAdvertisements);
    });

    it('should successfully edit advertisement', function () {
        login.execLogin("chandler", "asd");

        browser.get(existingCompanyUrl);

        companyPage.advertisementsTab.click();

        companyPage.advertisementEditButton.click();

        expect(advertisementEditDialog.titleInput.isDisplayed()).toBe(true);
        expect(advertisementEditDialog.priceInput.isDisplayed()).toBe(true);
        expect(advertisementEditDialog.currencySelect.isDisplayed()).toBe(true);
        expect(advertisementEditDialog.typeSelect.isDisplayed()).toBe(true);
        expect(advertisementEditDialog.okButton.isDisplayed()).toBe(true);
        expect(advertisementEditDialog.cancelButton.isDisplayed()).toBe(true);

        var newTitle = "Just to check if this thing still works";

        advertisementEditDialog.titleInput = newTitle;

        advertisementEditDialog.okButton.click();

        expect(companyPage.advertisementList.get(0).getText()).toContain(newTitle);
    });

    it('should successfully edit real estate', function () {
        login.execLogin("chandler", "asd");

        browser.get(existingCompanyUrl);

        companyPage.realEstatesTab.click();

        companyPage.realEstateEditButton.click();

        expect(realEstateDialog.nameInput.isDisplayed()).toBe(true);
        expect(realEstateDialog.areaInput.isDisplayed()).toBe(true);
        expect(realEstateDialog.descriptionTextArea.isDisplayed()).toBe(true);
        expect(realEstateDialog.locationCity.isDisplayed()).toBe(true);
        expect(realEstateDialog.pinMap.isDisplayed()).toBe(true);

        realEstateDialog.nameInput = "Just to check if this thing still works";

        realEstateDialog.okButton.click();

        expect(companyPage.realEstateList.get(0).getText()).toContain("Just to check if this thing still works");
    });

    it('should successfully delete advertisement', function() {
        login.execLogin("chandler", "asd");

        browser.get(existingCompanyUrl);

        companyPage.advertisementsTab.click();

        companyPage.advertisementDeleteButton.click();

        companyPage.ensureAdvertisementIsDeleted(numberOfAdvertisements);
    });

    it('should successfully delete real estate', function() {
        login.execLogin("chandler", "asd");

        browser.get(existingCompanyUrl);

        companyPage.realEstatesTab.click();
        companyPage.getNumberOfRealEstates(numberOfRealEstates).then(function() {
            companyPage.realEstateDeleteButton.click();

            companyPage.ensureRealEstateIsDeleted(numberOfRealEstates);
        });
    });

    it('should successfully edit company data', function () {
        login.execLogin("chandler", "asd");

        browser.get(existingCompanyUrl);

        companyPage.editButton.click();

        expect(companyEditDialog.nameInput.isDisplayed()).toBe(true);
        expect(companyEditDialog.okButton.isDisplayed()).toBe(true);
        expect(companyEditDialog.cancelButton.isDisplayed()).toBe(true);

        var newName = "Just to check if this thing works";

        companyEditDialog.nameInput = newName;

        companyEditDialog.okButton.click();

        expect(companyPage.nameSpan.getText()).toEqual(newName);
    });


});