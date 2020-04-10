module.exports.config = {
    framework: 'custom',
    frameworkPath: 'node_modules/protractor-cucumber-framework',
    cucumberOpts: {
        require: ['src/cucumber/steps/*.steps.js']
    },
    specs: ['src/cucumber/features/*.feature'],
    capabilities: {
        browserName: 'chrome',
    }
};