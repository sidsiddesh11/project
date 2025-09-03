package com.project.tests; 

import org.testng.annotations.BeforeMethod;

import java.io.IOException;

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

        test = extent.createTest("API Page Test - Click All APIs");
        test.log(Status.INFO, "Navigated to API List Page");
    }

    @Test
    public void testClickAllApis() throws IOException {
        apiPage.clickAllApis();
        test.log(Status.PASS, "All API links clicked successfully");

        // ✅ Capture screenshot for success
        String screenshotPath = ScreenshotUtilities.Capture(driver, "APIPage_AllApis");
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
