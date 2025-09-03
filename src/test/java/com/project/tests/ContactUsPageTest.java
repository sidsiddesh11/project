package com.project.tests;

import org.testng.annotations.DataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.project.base.Basetest;
import com.project.pages.Contactuspage;
import com.project.pages.Homepage;
import com.project.utilities.ExcelUtilities;
import com.project.utilities.ScreenshotUtilities;   // ✅ added import

import com.aventstack.extentreports.Status;

import java.io.File;
import java.io.IOException;

public class ContactUsPageTest extends Basetest {

    static String projectpath = System.getProperty("user.dir");

    @DataProvider(name = "contactus-data")
    public Object[][] contactData() throws Exception {
        String excelPath = projectpath + "/src/test/resources/contactus.xlsx";
        return ExcelUtilities.getData(excelPath, "Sheet1");
    }

    @Test(dataProvider = "contactus-data")
    public void submitContactUsForm(String name, String email, String subject, String message, String filePath) throws InterruptedException, IOException {
        test = extent.createTest("TC_ECOM_ContactUs - Submit Form for: " + name);

        Homepage home = new Homepage(driver);
        home.clickContactUs();
        test.log(Status.INFO, "Navigated to Contact Us page")
            .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ContactUsPage"));

        Contactuspage contact = new Contactuspage(driver);
        contact.enterName(name);
        contact.enterEmail(email);
        contact.enterSubject(subject);
        contact.enterMessage(message);
        test.log(Status.INFO, "Entered form details")
            .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "EnteredDetails"));

        if (filePath != null && !filePath.trim().isEmpty()) {
            String absolutePath = filePath;
            if (!new File(filePath).isAbsolute()) {
                absolutePath = System.getProperty("user.dir") + File.separator + filePath.replace("/", File.separator);
            }
            contact.uploadFile(absolutePath);
            test.log(Status.INFO, "Uploaded file: " + absolutePath)
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "FileUploaded"));
        }

        contact.clickSubmit();
        test.log(Status.INFO, "Clicked submit button")
            .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "FormSubmitted"));

        try {
            Assert.assertTrue(contact.isSuccessMessageDisplayed(), "Success message not displayed");
            test.log(Status.PASS, "Contact Us form submitted successfully for: " + name)
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "SuccessMessage"));
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Contact Us form submission failed for: " + name)
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "Failure"));
            throw e; // rethrow to mark test as failed
        }
    }
}
