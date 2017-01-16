var Jasmine2HtmlReporter = require('protractor-jasmine2-html-reporter');
var SpecReporter = require('jasmine-spec-reporter').SpecReporter;

exports.config = {

    specs: [
        'navbar/navbar.spec.js',
        'auth/register/register.spec.js',
        'auth/login/login.spec.js',
        'advertisements/advertisements.spec.js',
        'advertisements/advertisement/advertisement.spec.js',
        'users/admin/admin.spec.js',
        'users/advertiser/advertiser.spec.js',
        'users/company/company.spec.js',
        'users/verifier/verifier.spec.js'
    ],

    capabilities: {
        'browserName': 'chrome'
    },

    directConnect: true,

    framework: 'jasmine2',

    jasmineNodeOpts: {
        showColors: true,
        isVerbose: true,
        defaultTimeoutInterval: 180000,
        includeStackTrace: true,
        print: function() {}
    },

    onPrepare: function() {
        browser.driver.manage().window().maximize();

        jasmine.getEnv().addReporter(new SpecReporter({
            displayStacktrace: true,
            displaySpecDuration: true,
            displayFailedSpec: true,
            displayFailuresSummary: false,
            displayPendingSummary: false
        }));
    }
};
