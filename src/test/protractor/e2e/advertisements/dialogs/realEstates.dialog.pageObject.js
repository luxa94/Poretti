var RealEstatesDialog = function() {};

RealEstatesDialog.prototype = Object.create({}, {

    realEstatesList: {
        get: function() {
            return element.all(by.repeater("realEstate in vm.realEstates"));
        }
    },

    firstRealEstate: {
        get: function() {
            return element.all(by.tagName("md-checkbox")).get(0);
        }
    },

    okButton: {
        get: function() {
            return element(by.id("realEstates-btn-ok"));
        }
    },

    cancelButton: {
        get: function() {
            return element(by.id("realEstates-btn-cancel"));
        }
    },
});

module.exports = RealEstatesDialog;