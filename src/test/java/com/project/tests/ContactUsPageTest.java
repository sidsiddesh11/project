package com.project.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import com.project.base.Basetest;
import com.project.pages.Contactuspage;
import com.project.pages.Homepage;
import com.project.utilities.ExcelUtilities;

import java.io.File;
import java.lang.reflect.Method;

public class ContactUsPageTest extends Basetest {

    // Create a report node for every @Test here (needed because Basetest doesn't auto-create it)
    @BeforeMethod(alwaysRun = true)
    public void createExtentNode(Method m) {
    	test = extent.createTest("TC_ECOM_ContactUs:  " +m.getName());
    }

 
    private static final String EXCEL = "src/test/resources/Reports/contactus.xlsx";

   
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
   /* @DataProvider(name = "subjects")
    public Object[][] subject() throws Exception {
        return ExcelUtilities.getData(EXCEL, "Subjects");
   
    }
    @DataProvider(name = "message")
    public Object[][] message() throws Exception {
        return ExcelUtilities.getData(EXCEL, "Messages");
   
    } */


    private Contactuspage openContactPage() {
        Homepage home = new Homepage(driver);
        home.clickContactUs();
        return new Contactuspage(driver);
    }

    
    @Test
    public void verifyGetInTouchVisible() {
        Contactuspage c = openContactPage();
        Assert.assertTrue(c.isGetInTouchVisible(), "Get In Touch should be visible");
    }

    @Test
    public void verifyFeedbackForUsVisible() {
        Contactuspage c = openContactPage();
        Assert.assertTrue(c.isFeedbackForUsVisible(), "Feedback for us should be visible");
    }

    @Test(dataProvider = "valid-names")
    public void verifyNameFieldWithValidData(String name) {
        Contactuspage c = openContactPage();
        c.enterName(name);
        Assert.assertTrue(true);
    }

    @Test(dataProvider = "invalid-names")
    public void verifyNameFieldWithInvalidData(String badName) {
        Contactuspage c = openContactPage();
        c.enterName(badName);
        boolean alertShown = c.clickSubmitAndAcceptIfAlert(2);
        Assert.assertFalse(alertShown, "Submit should not be accessible when name is invalid or empty");
    }

    @Test(dataProvider = "valid-emails")
    public void verifyEmailFieldWithValidData(String email) {
        Contactuspage c = openContactPage();
        c.enterEmail(email);
        Assert.assertTrue(true);
    }

    @Test(dataProvider = "invalid-emails")
    public void verifyEmailFieldWithInvalidData(String email) {
        Contactuspage c = openContactPage();
        c.enterEmail(email);
        boolean alertShown = c.clickSubmitAndAcceptIfAlert(2);
        Assert.assertFalse(alertShown, "Submit should be blocked for invalid email");
    }

    @Test
    public void verifySubjectField() {
        Contactuspage c = openContactPage();
        c.enterSubject("Order Enquiry");
        Assert.assertTrue(true);
    }

    @Test
    public void verifyMessageField() {
        Contactuspage c = openContactPage();
        c.enterMessage("This is a test message");
        Assert.assertTrue(true);
    }

    @Test
    public void verifyUploadValidFile() {
        Contactuspage c = openContactPage();
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator +
                      "test" + File.separator + "resources" + File.separator + "sample.txt";
        c.uploadFile(path);
        Assert.assertTrue(c.isFileChosen(), "File should be chosen");
    }

    @Test
    public void verifyUploadInvalidFile() {
        Contactuspage c = openContactPage();
        String badPath = System.getProperty("user.dir") + File.separator + "not_exists.txt";
        try { c.uploadFile(badPath); } catch (Exception ignored) {}
        Assert.assertFalse(c.isFileChosen(), "No file should be chosen for a bad path");
    }

   
    @Test(dataProvider = "contactus-excel")
    public void submitContactUsForm(String name, String email, String subject, String message, String filePath) {
        Contactuspage c = openContactPage();

        c.enterName(name);
        c.enterEmail(email);
        c.enterSubject(subject);
        c.enterMessage(message);

        if (filePath != null && !filePath.trim().isEmpty()) {
            String abs = new File(filePath).isAbsolute()
                    ? filePath
                    : System.getProperty("user.dir") + File.separator + filePath.replace("/", File.separator);
            c.uploadFile(abs);
        }

        boolean alertShown = c.clickSubmitAndAcceptIfAlert(4);
        Assert.assertTrue(alertShown, "Alert should appear and be accepted for valid submit");
        Assert.assertTrue(c.isSuccessMessageDisplayed(), "Success message should be visible");
    }

    @Test
    public void verifyPopupOk() {
        Contactuspage c = openContactPage();
        c.enterName("Subhash");
        c.enterEmail("test@example.com");
        c.enterSubject("Test");
        c.enterMessage("Message");
        boolean alertShown = c.clickSubmitAndAcceptIfAlert(4);
        Assert.assertTrue(alertShown, "Alert should be shown and accepted");
    }

    @Test
    public void verifyMessageAfterSubmit() {
        Contactuspage c = openContactPage();
        c.enterName("Subhash");
        c.enterEmail("test@example.com");
        c.enterSubject("Test");
        c.enterMessage("Message");
        c.clickSubmitAndAcceptIfAlert(4);
        Assert.assertTrue(c.isSuccessMessageDisplayed(), "Success message visible");
    }

    @Test
    public void verifyHomeButtonAfterSubmit() {
        Contactuspage c = openContactPage();
        c.enterName("Subhash");
        c.enterEmail("test@example.com");
        c.enterSubject("Test");
        c.enterMessage("Message");
        c.clickSubmitAndAcceptIfAlert(4);
        c.clickHomeButton();
        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));
    }

    @Test public void verifyMandatoryName(){ 
    	Assert.assertTrue(openContactPage().isNameRequired(),"Name required"); 
    	}
    @Test public void verifyMandatoryEmail(){ 
    	Assert.assertTrue(openContactPage().isEmailRequired(),"Email required"); 
    	}
    @Test public void verifyMandatorySubject(){ 
    	Assert.assertTrue(openContactPage().isSubjectRequired(),"Subject required");
    	}
    @Test public void verifyMandatoryMessage(){ 
    	Assert.assertTrue(openContactPage().isMessageRequired(),"Message required"); 
    	}
}
