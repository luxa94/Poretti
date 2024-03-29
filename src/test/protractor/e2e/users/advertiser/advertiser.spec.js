'use strict';

var login = require('../../auth/login/login.preparation');
var Navbar = require('../../navbar/navbar.pageObject');
var AdvertiserPage = require('./advertiser.pageObject');
var UserEditDialog = require('../dialogs/userEdit.dialog.pageObject');
var RealEstateDialog = require('../../advertisements/dialogs/realEstate.dialog.pageObject');
var RealEstatesDialog = require('../../advertisements/dialogs/realEstates.dialog.pageObject');
var AdvertisementDialog = require('../../advertisements/dialogs/advertisement.dialog.pageObject');
var AdvertisementEditDialog = require('../../advertisements/dialogs/advertisementEdit.dialog.pageObject');
var ReviewDialog = require('../../advertisements/dialogs/review.dialog.pageObject');

var advertiserUrl = 'http://localhost:8080/#!/user/2';

describe('Advertiser profile page', function () {
    var navbar;
    var advertiserPage;
    var userEditDialog;
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


    beforeAll(function () {
        browser.get('http://localhost:8080');
        navbar = new Navbar();
        advertiserPage = new AdvertiserPage();
        userEditDialog = new UserEditDialog();
        realEstateDialog = new RealEstateDialog();
        realEstatesDialog = new RealEstatesDialog();
        advertisementDialog = new AdvertisementDialog();
        advertisementEditDialog = new AdvertisementEditDialog();
        reviewDialog = new ReviewDialog();

    });

    beforeEach(function () {
        advertiserPage.getNumberOfAdvertisements(numberOfAdvertisements);
    });

    afterEach(function () {
        browser.executeScript('window.localStorage.clear();');
    });

    it('should render proper ui when logged user is not advertiser', function() {
        login.execLogin("joey", "asd");
        browser.get(advertiserUrl);

        expect(browser.getCurrentUrl()).toEqual(advertiserUrl);

        expect(advertiserPage.editButton.isDisplayed()).toBe(false);
        expect(advertiserPage.realEstateButton.isDisplayed()).toBe(false);
        expect(advertiserPage.advertisementButton.isDisplayed()).toBe(false);
        expect(advertiserPage.reviewButton.isDisplayed()).toBe(true);

        expect(advertiserPage.advertisementDetailsButton.isDisplayed()).toBe(true);
        expect(advertiserPage.advertisementEditButton.isDisplayed()).toBe(false);
        expect(advertiserPage.advertisementDeleteButton.isDisplayed()).toBe(false);

        expect(advertiserPage.realEstateEditButton.isDisplayed()).toBe(false);
        expect(advertiserPage.realEstateDeleteButton.isDisplayed()).toBe(false);
    });

    it('should render proper ui when logged user is advertiser', function () {
        login.execLogin();

        navbar.profileButton.click();
        navbar.ensureIsRedirectedToProfile();

        expect(browser.getCurrentUrl()).toEqual(advertiserUrl);

        expect(advertiserPage.editButton.isDisplayed()).toBe(true);
        expect(advertiserPage.realEstateButton.isDisplayed()).toBe(true);
        expect(advertiserPage.advertisementButton.isDisplayed()).toBe(true);
        expect(advertiserPage.reviewButton.isDisplayed()).toBe(false);

        expect(advertiserPage.advertisementDetailsButton.isDisplayed()).toBe(true);
        expect(advertiserPage.advertisementEditButton.isDisplayed()).toBe(true);
        expect(advertiserPage.advertisementDeleteButton.isDisplayed()).toBe(true);

        advertiserPage.realEstatesTab.click();

        expect(advertiserPage.realEstateEditButton.isDisplayed()).toBe(true);
        expect(advertiserPage.realEstateDeleteButton.isDisplayed()).toBe(true);
    });

     it('should successfully add new review', function() {
         login.execLogin("chandler", "asd");

         browser.get(advertiserUrl);

         advertiserPage.reviewsTab.click();

         advertiserPage.getNumberOfReviews(numberOfReviews).then(function() {
             advertiserPage.reviewButton.click();

             expect(reviewDialog.commentTextarea.isDisplayed()).toBe(true);
             expect(reviewDialog.ratingInput.isDisplayed()).toBe(true);
             expect(reviewDialog.okButton.isDisplayed()).toBe(true);
             expect(reviewDialog.cancelButton.isDisplayed()).toBe(true);

             reviewDialog.commentTextarea = "Just to check if this this works";
             reviewDialog.ratingInput = 5;
             reviewDialog.okButton.click();

             reviewDialog.ensureReviewIsAdded(advertiserPage.reviewList, numberOfReviews);
         });
     });

     it('should successfully add new real estate', function () {
         login.execLogin();

         browser.get(advertiserUrl);

         advertiserPage.realEstatesTab.click();

         advertiserPage.getNumberOfRealEstates(numberOfRealEstates).then(function() {
             advertiserPage.realEstateButton.click();

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


             realEstateDialog.ensureRealEstateIsAdded(advertiserPage.realEstateList, numberOfRealEstates);
         });
    });

     it('should successfully add new advertisement and real estate', function () {
         login.execLogin();

         browser.get(advertiserUrl);

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

         advertisementDialog.ensureAdvertisementIsAdded(advertiserPage.advertisementList, numberOfAdvertisements);
     });

     it('should successfully add new advertisement for real estate', function () {
         login.execLogin();

         browser.get(advertiserUrl);

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

         advertisementDialog.chooseRealEstateButton.click();

         expect(realEstatesDialog.okButton.isDisplayed()).toBe(true);
         expect(realEstatesDialog.cancelButton.isDisplayed()).toBe(true);

         realEstatesDialog.firstRealEstate.click();

         realEstatesDialog.okButton.click();

         expect(advertisementDialog.okButton.isDisplayed()).toBe(true);
         expect(advertisementDialog.addedRealEstateSpan.isDisplayed()).toBe(true);
         expect(advertisementDialog.addedRealEstateSpan.getText()).toContain("You added this real estate");

         advertisementDialog.okButton.click();

         advertisementDialog.ensureAdvertisementIsAdded(advertiserPage.advertisementList, numberOfAdvertisements);
     });

     it('should successfully edit advertisement', function() {
         login.execLogin();

         browser.get(advertiserUrl);

         advertiserPage.advertisementsTab.click();

         advertiserPage.advertisementEditButton.click();

         expect(advertisementEditDialog.titleInput.isDisplayed()).toBe(true);
         expect(advertisementEditDialog.priceInput.isDisplayed()).toBe(true);
         expect(advertisementEditDialog.currencySelect.isDisplayed()).toBe(true);
         expect(advertisementEditDialog.typeSelect.isDisplayed()).toBe(true);
         expect(advertisementEditDialog.okButton.isDisplayed()).toBe(true);
         expect(advertisementEditDialog.cancelButton.isDisplayed()).toBe(true);

         var newTitle = "Just to check if this thing still works";

         advertisementEditDialog.titleInput = newTitle;

         advertisementEditDialog.okButton.click();

         expect(advertiserPage.advertisementList.get(0).getText()).toContain(newTitle);
     });

      it('should successfully edit real estate', function() {
          login.execLogin();

          browser.get(advertiserUrl);

          advertiserPage.realEstatesTab.click();

          advertiserPage.realEstateEditButton.click();

          expect(realEstateDialog.nameInput.isDisplayed()).toBe(true);
          expect(realEstateDialog.areaInput.isDisplayed()).toBe(true);
          expect(realEstateDialog.descriptionTextArea.isDisplayed()).toBe(true);
          expect(realEstateDialog.locationCity.isDisplayed()).toBe(true);
          expect(realEstateDialog.pinMap.isDisplayed()).toBe(true);

           realEstateDialog.nameInput = "Just to check if this thing still works";

           realEstateDialog.okButton.click();

           expect(advertiserPage.realEstateList.get(0).getText()).toContain("Just to check if this thing still works");
      });

     it('should successfully delete advertisement', function() {
         login.execLogin();

         browser.get(advertiserUrl);

         advertiserPage.advertisementsTab.click();

         advertiserPage.advertisementDeleteButton.click();

         advertiserPage.ensureAdvertisementIsDeleted(numberOfAdvertisements);
     });

     it('should successfully delete real estate', function() {
         login.execLogin();

         browser.get(advertiserUrl);

         advertiserPage.realEstatesTab.click();
         advertiserPage.getNumberOfRealEstates(numberOfRealEstates).then(function() {
             advertiserPage.realEstateDeleteButton.click();

             advertiserPage.ensureRealEstateIsDeleted(numberOfRealEstates);
         });
     });

     it('should successfully edit user data', function () {
         login.execLogin();

         browser.get(advertiserUrl);

         advertiserPage.editButton.click();

         expect(userEditDialog.nameInput.isDisplayed()).toBe(true);
         expect(userEditDialog.okButton.isDisplayed()).toBe(true);
         expect(userEditDialog.cancelButton.isDisplayed()).toBe(true);

         var newName = "Just to check if this thing works";

         userEditDialog.nameInput = newName;

         userEditDialog.okButton.click();

         expect(advertiserPage.nameSpan.getText()).toEqual(newName);
     });

     it('should go to the advertisement page', function () {
         browser.get(advertiserUrl);

         advertiserPage.advertisementDetailsButton.click();

         advertiserPage.ensureIsRedirectedToAdvertisement();
     });
});