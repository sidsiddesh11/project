package com.project.tests;

import org.testng.annotations.BeforeMethod;
import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import com.project.pages.API_page;
import com.aventstack.extentreports.Status;
import com.project.base.Basetest;
import com.project.utilities.ScreenshotUtilities;

public class ApiPageTest extends Basetest {

    API_page apiPage;

    @BeforeMethod
    public void setupApiPage() {
        driver.get("https://www.automationexercise.com/api_list");

        apiPage = new API_page(driver);
        apiPage.initApiLocators();

        test = extent.createTest("API Page Test - Validate Each API Button");
        test.log(Status.INFO, "Navigated to API List Page");

        // ✅ Assert page heading
        Assert.assertEquals(apiPage.getPageHeading(), "APIS LIST FOR PRACTICE",
                "Page heading is incorrect!");
    }

    @Test
    public void testEachApiClickAndValidate() throws IOException {
        int totalApis = apiPage.getApiCount();

        for (int i = 0; i < totalApis; i++) {
            boolean isExpanded = apiPage.clickAndValidateApi(i);

            // ✅ Assert for each API
            Assert.assertTrue(isExpanded, "API panel at index " + i + " did not expand!");

            test.log(Status.PASS, "API button " + (i + 1) + " expanded successfully");
        }

        // ✅ Capture screenshot after last click
        String screenshotPath = ScreenshotUtilities.Capture(driver, "APIPage_AfterAllClicks");
        test.addScreenCaptureFromPath(screenshotPath);
    }

    @AfterClass
    public void tearDownApiPage() {
        if (driver != null) {
            driver.quit();
            test.log(Status.INFO, "Browser closed");
        }
    }
}
