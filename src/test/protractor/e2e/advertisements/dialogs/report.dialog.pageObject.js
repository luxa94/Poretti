var ReportDialog = function() {};

ReportDialog.prototype = Object.create({}, {

    reasonSelect: {
        get: function() {
            return element(by.id('report-select-reason'));
        },
        set: function(value) {
            return this.reasonSelect.sendKeys(value);
        }
    },

    descriptionTextarea: {
        get: function() {
            return element(by.id('report-textarea-description'));
        },
        set: function(value) {
            return this.descriptionTextarea.sendKeys(value);
        }
    },

    okButton: {
        get: function() {
            return element(by.id('report-btn-ok'));
        }
    },

    cancelButton: {
        get: function() {
            return element(by.id('report-btn-cancel'));
        }
    }

});
module.exports = ReportDialog;