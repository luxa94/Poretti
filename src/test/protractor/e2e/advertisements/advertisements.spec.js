var AdvertisementsPage = require('./advertisements.pageObject');

var sizePerPage = 5;
var sizeOnThirdPage = 1;
var numberOfPages = 3;

describe("Advertisements page", function() {
    var advertisementsPage;

    beforeAll(function() {
        browser.get('http://localhost:8080');
        advertisementsPage = new AdvertisementsPage();
    });

    afterEach(function() {
        browser.executeScript('window.localStorage.clear();');
        advertisementsPage.clearButton.click();
        advertisementsPage.ensureNumberOfPagesIsGood();
    });

    it('should render advertisements by pages', function() {
        advertisementsPage.ensureNumberOfPagesIsGood();
        advertisementsPage.ensureSizePerPageIsGood();
    });

    it('should go to next page', function() {
        advertisementsPage.nextPageButton.click();
        advertisementsPage.nextPageButton.click();

        advertisementsPage.ensureSizePerPageIsGood(1);
    });

    it('should go to previous page', function() {
        advertisementsPage.previousPageButton.click();
        advertisementsPage.ensureSizePerPageIsGood();
    });

    it('should go to exact page', function() {
        advertisementsPage.pageNumberButton.click();
        advertisementsPage.ensureSizePerPageIsGood();
    });

    it('should go to advertisements after click on \'Details\' button', function() {
        advertisementsPage.pageNumberButton.click();

        advertisementsPage.ensureNumberOfPagesIsGood();

        advertisementsPage.detailsButton.click();

        expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/#!/advertisement/1");

        browser.get('http://localhost:8080');
    });

    it('should find advertisements by title filter', function() {
        advertisementsPage.filterTitleInput = "Advertisement";
        advertisementsPage.searchButton.click();

        advertisementsPage.ensureFilterIsExecuted();

        expect(advertisementsPage.advertisementTitleText.getText()).toContain("Advertisement title");
    });

    it('should find advertisements by adv type filter', function() {
        advertisementsPage.filterAdvTypeSelect = "SALE";
        advertisementsPage.searchButton.click();

        advertisementsPage.ensureFilterIsExecuted();

        expect(advertisementsPage.advertisementTitleText.getText()).toContain("Advertisement title");
    });

    it('should find advertisements by price from filter', function() {
        advertisementsPage.filterPriceFromInput = "2000";
        advertisementsPage.searchButton.click();

        advertisementsPage.ensureFilterIsExecuted();

        expect(advertisementsPage.advertisementTitleText.getText()).toContain("Advertisement title");
    });

    it('should find advertisements by price to filter', function() {
        advertisementsPage.filterPriceToInput = "2000";
        advertisementsPage.searchButton.click();

        advertisementsPage.ensureFilterIsExecuted();

        expect(advertisementsPage.advertisementTitleText.getText()).not.toContain("Advertisement title");
    });

    it('should find advertisements by re name filter', function() {
        advertisementsPage.filterRealEstateNameInput = "Test name";
        advertisementsPage.searchButton.click();

        advertisementsPage.ensureFilterIsExecuted();

        expect(advertisementsPage.advertisementTitleText.getText()).toContain("Advertisement title");
    });

    it('should find advertisements by re type filter', function() {
        advertisementsPage.filterRealEstateTypeSelect = "APARTMENT";
        advertisementsPage.searchButton.click();

        advertisementsPage.ensureFilterIsExecuted();

        expect(advertisementsPage.advertisementTitleText.getText()).toContain("Advertisement title");
    });

});