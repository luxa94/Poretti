var UserEditDialog = function() {};

UserEditDialog.prototype = Object.create({}, {

    nameInput: {
        get: function() {
            return element(by.id("userToEdit-input-name"));
        },
        set: function(value) {
            return this.nameInput.clear().sendKeys(value);
        }
    },

    okButton: {
        get: function() {
            return element(by.id("userToEdit-btn-ok"));
        }
    },

    cancelButton: {
        get: function() {
            return element(by.id("userToEdit-btn-cancel"));
        }
    }

});

module.exports = UserEditDialog;