let {defineSupportCode} = require('cucumber');
 
let chai = require('chai').use(require('chai-as-promised'));
let expect = chai.expect;
 
defineSupportCode( function({When, Then}) {
 When('I navigate to {string}', function(site) {
   return browser.get(site);
 });
 
 Then('the title should be {string}', function(title) {
   return expect(browser.getTitle()).to.eventually.eql(title);
 });
});