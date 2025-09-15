package com.project.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import com.project.base.Basetest;
import com.project.pages.Contactuspage;
import com.project.pages.Homepage;
import com.project.utilities.ExcelUtilities;

import com.aventstack.extentreports.Status;

import java.lang.reflect.Method;

public class ContactUsPageTest extends Basetest {

    private static final String EXCEL = System.getProperty("user.dir")+"/src/test/resources/contactus.xlsx";

    @BeforeMethod(alwaysRun = true)
    public void createExtentNode(Method m) {
        test = extent.createTest("TC_ECOM_ContactUs :  " + m.getName());
    }

    @DataProvider(name = "contactus-excel")
    public Object[][] contactExcelData() throws Exception {
        return ExcelUtilities.getData(EXCEL, "Sheet1");
    }

    @DataProvider(name = "valid-names")
    public Object[][] validNames() throws Exception {
        return ExcelUtilities.getData(EXCEL, "ValidNames");
    }

    @DataProvider(name = "invalid-names")
    public Object[][] invalidNames() throws Exception {
        return ExcelUtilities.getData(EXCEL, "InvalidNames");
    }

    @DataProvider(name = "valid-emails")
    public Object[][] validEmails() throws Exception {
        return ExcelUtilities.getData(EXCEL, "ValidEmails");
    }

    @DataProvider(name = "invalid-emails")
    public Object[][] invalidEmails() throws Exception {
        return ExcelUtilities.getData(EXCEL, "InvalidEmails");
    }
    // ---------------------------------------------------

    private Contactuspage openContactPage() {
        test.log(Status.INFO, "Navigating to Contact Us page");
        Homepage home = new Homepage(driver);
        home.clickContactUs();
        return new Contactuspage(driver);
    }

    @Test
    public void verifyGetInTouchVisible() {
        Contactuspage c = openContactPage();
        test.log(Status.INFO, "Verifying 'Get In Touch' heading is visible");
        Assert.assertTrue(c.isGetInTouchVisible(), "Get In Touch should be visible");
        test.log(Status.PASS, "✅ 'Get In Touch' heading is visible");
    }

    @Test
    public void verifyFeedbackForUsVisible() {
        Contactuspage c = openContactPage();
        test.log(Status.INFO, "Verifying 'Feedback For Us' text is visible");
        Assert.assertTrue(c.isFeedbackForUsVisible(), "Feedback for us should be visible");
        test.log(Status.PASS, "✅ 'Feedback For Us' text is visible");
    }

    @Test(dataProvider = "valid-names")
    public void verifyNameFieldWithValidData(String name) {
        Contactuspage c = openContactPage();
        test.log(Status.INFO, "Entering Name: " + name);
        c.enterName(name);
        test.log(Status.PASS, "✅ Name accepted");
    }

    @Test(dataProvider = "invalid-names")
    public void verifyNameFieldWithInvalidData(String badName) {
        Contactuspage c = openContactPage();
        test.log(Status.INFO, "Entering a invalid name: '" + badName + "'");
        c.enterName(badName);
        boolean alertShown = c.clickSubmitAndAcceptIfAlert(2);
        Assert.assertFalse(alertShown, "Submit should not be accessible when name is invalid or empty");
        test.log(Status.PASS, "✅ Submit blocked");
    }

    @Test(dataProvider = "valid-emails")
    public void verifyEmailFieldWithValidData(String email) {
        Contactuspage c = openContactPage();
        test.log(Status.INFO, "Entering valid Email: " + email);
        c.enterEmail(email);
        test.log(Status.PASS, "✅ Email accepted");
    }

    @Test(dataProvider = "invalid-emails")
    public void verifyEmailFieldWithInvalidData(String email) {
        Contactuspage c = openContactPage();
        test.log(Status.INFO, "Trying invalid Email: " + email);
        c.enterEmail(email);
        boolean alertShown = c.clickSubmitAndAcceptIfAlert(2);
        Assert.assertFalse(alertShown, "Submit should be blocked for invalid email");
        test.log(Status.PASS, "✅ Submit blocked");
    }

    @Test
    public void verifySubjectField() {
        Contactuspage c = openContactPage();
        test.log(Status.INFO, "Entering Subject: 'Order Enquiry'");
        c.enterSubject("Order Enquiry");
        test.log(Status.PASS, "✅ Subject accepted");
    }

    @Test
    public void verifyMessageField() {
        Contactuspage c = openContactPage();
        test.log(Status.INFO, "Entering Message: 'This is a test message'");
        c.enterMessage("This is a test message");
        test.log(Status.PASS, "✅ Message accepted");
    }

    @Test(dataProvider = "contactus-excel")
    public void submitContactUsForm(String name, String email, String subject, String message, String filePath) {
        Contactuspage c = openContactPage();

        test.log(Status.INFO, "Filling Contact Us form with provided test data");
        test.log(Status.INFO, "Name: " + name + " | Email: " + email + " | Subject: " + subject);

        c.enterName(name);
        c.enterEmail(email);
        c.enterSubject(subject);
        c.enterMessage(message);

        test.log(Status.INFO, "Clicking Submit and handling alert");
        boolean alertShown = c.clickSubmitAndAcceptIfAlert(4);
        Assert.assertTrue(alertShown, "Alert should appear and be accepted for valid submit");

        test.log(Status.INFO, "Verifying success message is displayed");
        Assert.assertTrue(c.isSuccessMessageDisplayed(), "Success message should be visible");

        test.log(Status.PASS, "✅ Contact Us form submitted successfully for: " + email);
    }

    @Test(dataProvider = "contactus-excel")
    public void verifyPopupOk(String name, String email, String subject, String message, String filePath) {
        Contactuspage c = openContactPage();
        test.log(Status.INFO, "Filling up the required fields");
        c.enterName(name);
        c.enterEmail(email);
        c.enterSubject(subject);
        c.enterMessage(message);

        boolean alertShown = c.clickSubmitAndAcceptIfAlert(4);
        Assert.assertTrue(alertShown, "Alert should be shown and accepted");
        test.log(Status.PASS, "✅ Alert shown and accepted");
    }

    @Test(dataProvider = "contactus-excel")
    public void verifyMessageAfterSubmit(String name, String email, String subject, String message, String filePath) {
        Contactuspage c = openContactPage();
        test.log(Status.INFO, "Submitting form and verifying success message after submit");
        c.enterName(name);
        c.enterEmail(email);
        c.enterSubject(subject);
        c.enterMessage(message);
        c.clickSubmitAndAcceptIfAlert(4);

        Assert.assertTrue(c.isSuccessMessageDisplayed(), "Success message visible");
        test.log(Status.PASS, "✅ Success message displayed");
    }

    @Test(dataProvider = "contactus-excel")
    public void verifyHomeButtonAfterSubmit(String name, String email, String subject, String message, String filePath) {
        Contactuspage c = openContactPage();
        test.log(Status.INFO, "Submitting form and clicking 'Home' after submit");
        c.enterName(name);
        c.enterEmail(email);
        c.enterSubject(subject);
        c.enterMessage(message);
        c.clickSubmitAndAcceptIfAlert(4);

        test.log(Status.INFO, "Clicking 'Home' button");
        c.clickHomeButton();

        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));
        test.log(Status.PASS, "✅ Returned to Home page successfully");
    }

    @Test
    public void verifyMandatoryName() {
        Contactuspage c = openContactPage();
        test.log(Status.INFO, "Verifying Name field has 'required' attribute");
        Assert.assertTrue(c.isNameRequired(), "Name required");
        test.log(Status.PASS, "✅ Name field is mandatory");
    }

    @Test
    public void verifyMandatoryEmail() {
        Contactuspage c = openContactPage();
        test.log(Status.INFO, "Verifying Email field has 'required' attribute");
        Assert.assertTrue(c.isEmailRequired(), "Email required");
        test.log(Status.PASS, "✅ Email field is mandatory");
    }

    @Test
    public void verifyMandatorySubject() {
        Contactuspage c = openContactPage();
        test.log(Status.INFO, "Verifying Subject field has 'required' attribute");
        Assert.assertTrue(c.isSubjectRequired(), "Subject required");
        test.log(Status.PASS, "✅ Subject field is mandatory");
    }

    @Test
    public void verifyMandatoryMessage() {
        Contactuspage c = openContactPage();
        test.log(Status.INFO, "Verifying Message field has 'required' attribute");
        Assert.assertTrue(c.isMessageRequired(), "Message required");
        test.log(Status.PASS, "✅ Message field is mandatory");
    }
}
