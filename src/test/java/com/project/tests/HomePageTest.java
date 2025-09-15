package com.project.tests;

import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.project.base.Basetest;
import com.project.pages.Homepage;

public class HomePageTest extends Basetest {

    @Test(priority = 1)
    public void verifyLogoDisplayed() {
        test = extent.createTest("TC_ECOM_Home_01 - Verify Home Page Logo");
        Homepage home = new Homepage(driver);

        try {
            test.log(Status.INFO, "Step 1: Checking if logo is displayed on Home Page");
            boolean logoDisplayed = home.isLogoDisplayed();

            Assert.assertTrue(logoDisplayed, "Logo should be visible on Home Page");
            test.log(Status.PASS, "Step 2: Logo is displayed successfully ✅");

        } catch (AssertionError e) {
            test.log(Status.FAIL, "Step Failed ❌ - " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }
    @Test(priority = 2)
    public void verifyHomePageTitle() {
        test = extent.createTest("TC_ECOM_Home_02 - Verify Home Page Title");
        Homepage home = new Homepage(driver);

        try {
            test.log(Status.INFO, "Step 1: Getting Home Page title");
            String title = home.getHomePageTitle();

            Assert.assertTrue(title.contains("Automation Exercise"),
                    "Page title should contain 'Automation Exercise'");
            test.log(Status.PASS, "Step 2: Home Page title verified: " + title);

        } catch (AssertionError e) {
            test.log(Status.FAIL, "Step Failed ❌ - " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 3)
    public void verifyNavigationToProducts() {
        test = extent.createTest("TC_ECOM_Home_03 - Verify Navigation to Products");
        Homepage home = new Homepage(driver);

        try {
            test.log(Status.INFO, "Step 1: Clicking on Products link");
            home.clickProducts();

            String actualTitle = driver.getTitle();
            Assert.assertTrue(actualTitle.contains("Products"), "User should navigate to Products page");
            test.log(Status.PASS, "Step 2: Navigated to Products successfully, Title: " + actualTitle);

        } catch (AssertionError e) {
            test.log(Status.FAIL, "Step Failed ❌ - " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 4)
    public void verifyNavigationToSignupLogin() {
        test = extent.createTest("TC_ECOM_Home_04 - Verify Navigation to Login/Signup");
        Homepage home = new Homepage(driver);

        try {
            test.log(Status.INFO, "Step 1: Clicking on Signup/Login link");
            home.clickSignupLogin();

            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("login"), "User should navigate to Login/Signup page");
            test.log(Status.PASS, "Step 2: Navigated to Signup/Login successfully, URL: " + currentUrl);

        } catch (AssertionError e) {
            test.log(Status.FAIL, "Step Failed ❌ - " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 5)
    public void verifyNavigationToTestCases() {
        test = extent.createTest("TC_ECOM_Home_05 - Verify Navigation to Test Cases");
        Homepage home = new Homepage(driver);

        try {
            test.log(Status.INFO, "Step 1: Clicking on Test Cases link");
            home.clickTestCases();

            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("test_cases"), "User should navigate to Test Cases page");
            test.log(Status.PASS, "Step 2: Navigated to Test Cases successfully, URL: " + currentUrl);

        } catch (AssertionError e) {
            test.log(Status.FAIL, "Step Failed ❌ - " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 6)
    public void verifyNavigationToApiTesting() {
        test = extent.createTest("TC_ECOM_Home_06 - Verify Navigation to API Testing");
        Homepage home = new Homepage(driver);

        try {
            test.log(Status.INFO, "Step 1: Clicking on API Testing link");
            home.clickApiTesting();

            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("api_list"), "User should navigate to API Testing page");
            test.log(Status.PASS, "Step 2: Navigated to API Testing successfully, URL: " + currentUrl);

        } catch (AssertionError e) {
            test.log(Status.FAIL, "Step Failed ❌ - " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 7)
    public void verifyNavigationToContactUs() {
        test = extent.createTest("TC_ECOM_Home_07 - Verify Navigation to Contact Us");
        Homepage home = new Homepage(driver);

        try {
            test.log(Status.INFO, "Step 1: Clicking on Contact Us link");
            home.clickContactUs();

            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("contact_us"), "User should navigate to Contact Us page");
            test.log(Status.PASS, "Step 2: Navigated to Contact Us successfully, URL: " + currentUrl);

        } catch (AssertionError e) {
            test.log(Status.FAIL, "Step Failed ❌ - " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 8)
    public void verifyNavigationToCart() {
        test = extent.createTest("TC_ECOM_Home_08 - Verify Navigation to Cart");
        Homepage home = new Homepage(driver);

        try {
            test.log(Status.INFO, "Step 1: Clicking on Cart link");
            home.clickCart();

            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("view_cart"), "User should navigate to Cart page");
            test.log(Status.PASS, "Step 2: Navigated to Cart successfully, URL: " + currentUrl);

        } catch (AssertionError e) {
            test.log(Status.FAIL, "Step Failed ❌ - " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }
}
