package com.project.tests;

import com.project.base.Basetest;
import com.project.pages.TestCasesPage;
import com.project.utilities.ScreenshotUtilities;   // ✅ added
import com.aventstack.extentreports.Status;        // ✅ added

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class TestCasesPageTest extends Basetest {

    private TestCasesPage testCasesPage;

    @BeforeMethod
    public void setupPage() {
        driver.get("https://www.automationexercise.com/test_cases");

        testCasesPage = new TestCasesPage(driver);
        testCasesPage.initTestCaseLocators();

        test = extent.createTest("TC_ECOM_TestCases - Verify Clicking All Test Cases");
    }

    @Test
    public void testClickAllTestCases() throws IOException {
        try {
            testCasesPage.clickAllTestCases();
            test.log(Status.PASS, "Clicked all test cases successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "TestCasesPage"));
        } catch (Exception e) {
            test.log(Status.FAIL, "Failed to click all test cases: " + e.getMessage())
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "TestCasesPageFail"));
            throw e;
        }
    }



}
