package com.project.tests;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.project.base.Basetest;
import com.project.pages.VideoTutorials_page;
import com.project.utilities.ScreenshotUtilities;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.MediaEntityBuilder;

public class VideoTutorialsPageTest extends Basetest {

    VideoTutorials_page videoTutorialsPage;

    @BeforeMethod
    public void setUp() {
        driver.get("https://automationexercise.com/");
        videoTutorialsPage = new VideoTutorials_page(driver);

        test = extent.createTest("Video Tutorials Page Test");
    }

    @Test
    public void verifyVideoTutorialsButton() {
        // Click the Video Tutorials link
        videoTutorialsPage.clickVideoTutorials();

        // Get current URL
        String currentURL = driver.getCurrentUrl();

        // Verify YouTube is opened
        Assert.assertTrue(currentURL.contains("youtube.com"),
                "Video Tutorials button did not redirect to YouTube!");

        test.log(Status.PASS, "Video Tutorials redirected to YouTube successfully");
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        try {
            // Take screenshot BEFORE quitting the driver
            if (driver != null) {
                String screenshotPath = ScreenshotUtilities.Capture(driver, result.getName());

                if (result.getStatus() == ITestResult.SUCCESS) {
                    test.log(Status.PASS, "Test Passed",
                            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                } else if (result.getStatus() == ITestResult.FAILURE) {
                    test.log(Status.FAIL, "Test Failed: " + result.getThrowable(),
                            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                } else if (result.getStatus() == ITestResult.SKIP) {
                    test.log(Status.SKIP, "Test Skipped: " + result.getThrowable(),
                            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                }
            }
        } catch (Exception e) {
            test.log(Status.WARNING, "Screenshot capture failed: " + e.getMessage());
        } finally {
            // Quit AFTER screenshot
            if (driver != null) {
                driver.quit();
            }
        }
    }}