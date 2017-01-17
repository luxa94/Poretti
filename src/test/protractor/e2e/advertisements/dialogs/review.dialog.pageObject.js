var ReviewDialog = function() {};

ReviewDialog.prototype = Object.create({}, {

    commentTextarea: {
        get: function() {
            return element(by.id('review-textarea-comment'));
        },
        set: function(value) {
            return this.commentTextarea.clear().sendKeys(value);
        }
    },

    ratingInput: {
        get: function() {
            return element(by.id('review-input-rating'));
        },
        set: function(value) {
            return this.ratingInput.clear().sendKeys(value);
        }
    },

    okButton: {
        get: function() {
            return element(by.id('review-btn-ok'));
        }
    },

    cancelButton: {
        get: function() {
            return element(by.id('review-btn-cancel'));
        }
    }

});
module.exports = ReviewDialog;