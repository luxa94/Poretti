var VerifierPage = require('./verifier.pageObject');
var login = require('../../auth/login/login.preparation');

describe('Verifier page', function () {

    var verifierPage;
    var numberOfAdvertisements = {
        value: 0
    };

    beforeAll(function () {
        login.execLogin('test_verifier', 'admin');
        verifierPage = new VerifierPage();
    });

    beforeEach(function () {
        verifierPage.getNumberOfAdvertisements(numberOfAdvertisements);
    });

    it('should successfully approve advertisement', function () {
        expect(browser.getCurrentUrl()).toEqual('http://localhost:8080/#!/verifier/4');

        verifierPage.showReportsButtonForPendingApproval.click();

        expect(verifierPage.approveButton.isDisplayed());
        expect(verifierPage.invalidateButton.isDisplayed());

        verifierPage.approveButton.click();

        verifierPage.ensureAdvertisementIsApproved(numberOfAdvertisements.value);
    });

    it('should successfully invalidate advertisement', function () {
        expect(browser.getCurrentUrl()).toEqual('http://localhost:8080/#!/verifier/4');

        verifierPage.showReportsButton.click();

        expect(verifierPage.invalidateButton.isDisplayed());

        verifierPage.invalidateButton.click();

        expect(verifierPage.advertisementStatusLabel.getText()).toEqual('INVALID');
    });

});