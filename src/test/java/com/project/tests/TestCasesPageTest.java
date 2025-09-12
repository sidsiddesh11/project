package com.project.tests;

import com.aventstack.extentreports.Status;
import com.project.base.Basetest;
import com.project.pages.TestCasesPage;
import com.project.utilities.ScreenshotUtilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;

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
        test.log(Status.INFO, "Starting the process of clicking all test cases.");
        testCasesPage.clickAllTestCases(test);

        test.log(Status.INFO, "Process complete. Final page state captured.");
        test.addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "TestCasesPage_Final"));
    }
}
