package com.project.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.project.base.Basetest;
import com.project.pages.Subscription_field;
import com.project.utilities.ExcelUtilities;

public class SubscriptionField_Test extends Basetest {

    String excelPath = System.getProperty("user.dir") + "/src/test/resources/Subscription new .xlsx";
    String sheetName = "Sheet1";

    @DataProvider(name = "subscriptionData")
    public Object[][] getSubscriptionData() throws Exception {
        return ExcelUtilities.getData(excelPath, sheetName);
    }

    @Test(dataProvider = "subscriptionData")
    public void testSubscription(String sno, String testCaseId, String objective, String preconditions,
                                 String steps, String testData, String expectedResult,
                                 String actualResult, String proof) {

        test = extent.createTest(testCaseId + " - " + objective);

        Subscription_field subscription = new Subscription_field(driver);

        // Step 1: Navigate to home page
        driver.get("https://automationexercise.com/");
        test.info("Navigated to Home Page");

        // Step 2: Scroll to subscription field
        subscription.scrollToSubscription();
        Assert.assertTrue(subscription.isEmailFieldDisplayed(), 
                "Subscription email input not visible on page | TC: " + testCaseId);
        test.info("Verified subscription field visible.");

        // Step 3: Enter email if provided
        if (testData != null && !testData.trim().isEmpty()) {
            subscription.enterEmail(testData.trim());
            test.pass("Entered email: " + testData);
        } else {
            test.pass("No email entered as per test case.");
        }

        // Step 4: Click Subscribe
        subscription.clickSubscribe();
        test.pass("Clicked Subscribe button");

        // Step 5: Capture actual result from UI
        String actualMessage;
        if (subscription.isSuccessMessageVisible()) {
            actualMessage = subscription.getSuccessMessage();
        } else if (subscription.isErrorMessageVisible()) {
            actualMessage = subscription.getErrorMessage();
        } else {
            actualMessage = "No message displayed";
        }

        // Step 6: Assert expected vs actual
        Assert.assertEquals(actualMessage.trim(), expectedResult.trim(),
                "Mismatch between Actual and Expected | TC: " + testCaseId);
        test.pass("Expected Result: " + expectedResult + " | Actual Result: " + actualMessage);

        // Step 7: Optional - attach proof screenshot
        // subscription.captureScreenshot(proof);
    }
}
