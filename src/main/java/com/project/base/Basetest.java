package com.project.base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.project.utilities.ExtentManager;
import com.project.utilities.ScreenshotUtilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Basetest {
    protected WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        extent = ExtentManager.getinstance(); // initialize Extent report
    }

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://automationexercise.com/");
    }

    @AfterMethod
    public void teardown(ITestResult result) throws Exception {
        if (test != null) {
            // Capture screenshot for all outcomes
            String screenshotPath = ScreenshotUtilities.Capture(driver, result.getName());

            if (result.getStatus() == ITestResult.FAILURE) {
                test.fail("Test Failed: " + result.getThrowable(),
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                test.pass("Test Passed",
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } else if (result.getStatus() == ITestResult.SKIP) {
                test.skip("Test Skipped: " + result.getThrowable(),
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }
        }
        driver.quit();
    }

    @AfterSuite
    public void flushReport() {
        if (extent != null) {
            extent.flush(); // write the report to disk
        }
    }
}
