var Jasmine2HtmlReporter = require('protractor-jasmine2-html-reporter');
var SpecReporter = require('jasmine-spec-reporter').SpecReporter;

exports.config = {

    specs: [
        'navbar/navbar.spec.js'
    ],

    capabilities: {
        'browserName': 'chrome'
    },

    directConnect: true,

    framework: 'jasmine2',

    jasmineNodeOpts: {
        showColors: true,
        isVerbose: true,
        defaultTimeoutInterval: 80000,
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
