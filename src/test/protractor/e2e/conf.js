var Jasmine2HtmlReporter = require('protractor-jasmine2-html-reporter');
var SpecReporter = require('jasmine-spec-reporter').SpecReporter;

exports.config = {

    specs: [
        'e2e/specs/login.spec.js'
    ],

    capabilities: {
        'browserName': 'chrome'
    },

    directConnect: true,

    baseUrl: 'http://localhost:8080/',

    framework: 'jasmine2',

    jasmineNodeOpts: {
        showColors: true,
        isVerbose: true,
        defaultTimeoutInterval: 30000,
        print: function() {}
    },

    onPrepare: function() {
        browser.driver.manage().window().maximize();

        jasmine.getEnv().addReporter(new Jasmine2HtmlReporter({
            savePath: "./target/reports/e2e/",
            takeScreenshots: true,
            takeScreenshotsOnlyOnFailures: true,
            fixedScreenshotName: true
        }));
        jasmine.getEnv().addReporter(new SpecReporter({
            displayStacktrace: 'all',
            displaySpecDuration: true,
            displayFailuresSummary: false,
            displayPendingSummary: false
        }));
    }
};
