var AdvertisementsPage = require('./advertisements.pageObject');

var advertisementUrl = "http://localhost:8080/#!/advertisement/9";
var advertisementTitle = "Advertisement title";

describe("Advertisements page", function() {
    var advertisementsPage;
    var numberOfPages = {
        value : 0
    };

    var sizePerPage = {
        numberValue: 0
    };


    beforeAll(function() {
        browser.get('http://localhost:8080');
        advertisementsPage = new AdvertisementsPage();
    });

    beforeEach(function() {
       advertisementsPage.getNumberOfPages(numberOfPages);
        advertisementsPage.getSizePerPage(sizePerPage);
    });

    afterEach(function() {
        browser.executeScript('window.localStorage.clear();');
        advertisementsPage.clearButton.click();
        advertisementsPage.ensureNumberOfPagesIsGood(numberOfPages);
    });

    it('should render advertisements by pages', function() {
        advertisementsPage.ensureNumberOfPagesIsGood(numberOfPages).then(function() {
            advertisementsPage.ensureSizePerPageIsGood(sizePerPage);
        });

    });

    it('should go to next page', function() {
        advertisementsPage.nextPageButton.click();
        advertisementsPage.getSizePerPage(sizePerPage).then(function() {
            advertisementsPage.ensureSizePerPageIsGood(sizePerPage);
        });
    });

    it('should go to previous page', function() {
        advertisementsPage.previousPageButton.click();
        advertisementsPage.getSizePerPage(sizePerPage).then(function() {
            advertisementsPage.ensureSizePerPageIsGood(sizePerPage);
        });
    });

    it('should go to exact page', function() {
        advertisementsPage.pageNumberButton.click();
        advertisementsPage.getSizePerPage(sizePerPage).then(function() {
            advertisementsPage.ensureSizePerPageIsGood(sizePerPage);
        });
    });

    it('should go to advertisements after click on \'Details\' button', function() {
        advertisementsPage.pageNumberButton.click();

        advertisementsPage.ensureNumberOfPagesIsGood(numberOfPages).then(function() {
            advertisementsPage.detailsButton.click();

            expect(browser.getCurrentUrl()).toEqual(advertisementUrl);

            browser.get('http://localhost:8080');
        });
    });

    it('should find advertisements by title filter', function() {
        advertisementsPage.filterTitleInput = "Advertisement";
        advertisementsPage.searchButton.click();

        advertisementsPage.ensureFilterIsExecuted().then(function() {
            expect(advertisementsPage.advertisementTitleText.getText()).toContain(advertisementTitle);
        });

    });

    it('should find advertisements by advertisement type filter', function() {
        advertisementsPage.filterAdvTypeSelect = "SALE";
        advertisementsPage.searchButton.click();

        advertisementsPage.ensureFilterIsExecuted().then(function() {
            expect(advertisementsPage.advertisementTitleText.getText()).toContain(advertisementTitle);
        });
    });

    it('should find advertisements by price from filter', function() {
        advertisementsPage.filterPriceFromInput = "2000";
        advertisementsPage.searchButton.click();

        advertisementsPage.ensureFilterIsExecuted().then(function() {
            expect(advertisementsPage.advertisementTitleText.getText()).toContain(advertisementTitle);
        });
    });

    it('should find advertisements by price to filter', function() {
        advertisementsPage.filterPriceToInput = "2000";
        advertisementsPage.searchButton.click();

        advertisementsPage.ensureFilterIsExecuted().then(function() {
            expect(advertisementsPage.advertisementTitleText.getText()).toContain(advertisementTitle);
        });
    });

    it('should find advertisements by real estate name filter', function() {
        advertisementsPage.filterRealEstateNameInput = "Test name";
        advertisementsPage.searchButton.click();

        advertisementsPage.ensureFilterIsExecuted().then(function() {
            expect(advertisementsPage.advertisementTitleText.getText()).toContain(advertisementTitle);
        });
    });

    it('should find advertisements by  real estate type filter', function() {
        advertisementsPage.filterRealEstateTypeSelect = "APARTMENT";
        advertisementsPage.searchButton.click();

        advertisementsPage.ensureFilterIsExecuted().then(function() {
            expect(advertisementsPage.advertisementTitleText.getText()).toContain(advertisementTitle);
        });
    });

});