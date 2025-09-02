package com.project.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.project.base.Basetest;
import com.project.pages.Homepage;

public class HomePageTest extends Basetest {

    @Test(priority = 1)
    public void verifyLogoDisplayed() {
        test = extent.createTest("TC_ECOM_Home_01 - Verify Home Page Logo");
        Homepage home = new Homepage(driver);
        Assert.assertTrue(home.isLogoDisplayed(), "Logo should be visible on Home Page");
    }

    @Test(priority = 2)
    public void verifyHomePageTitle() {
        test = extent.createTest("TC_ECOM_Home_02 - Verify Home Page Title");
        Homepage home = new Homepage(driver);
        String title = home.getHomePageTitle();
        Assert.assertTrue(title.contains("Automation Exercise"), "Page title should contain 'Automation Exercise'");
    }

    @Test(priority = 3)
    public void verifyNavigationToProducts() {
        test = extent.createTest("TC_ECOM_Home_03 - Verify Navigation to Products");
        Homepage home = new Homepage(driver);
        home.clickProducts();
        Assert.assertTrue(driver.getTitle().contains("Products"));
    }

    @Test(priority = 4)
    public void verifyNavigationToSignupLogin() {
        test = extent.createTest("TC_ECOM_Home_04 - Verify Navigation to Login/Signup");
        Homepage home = new Homepage(driver);
        home.clickSignupLogin();
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }

    @Test(priority = 5)
    public void verifyNavigationToTestCases() {
        test = extent.createTest("TC_ECOM_Home_05 - Verify Navigation to Test Cases");
        Homepage home = new Homepage(driver);
        home.clickTestCases();
        Assert.assertTrue(driver.getCurrentUrl().contains("test_cases"));
    }

    @Test(priority = 6)
    public void verifyNavigationToApiTesting() {
        test = extent.createTest("TC_ECOM_Home_06 - Verify Navigation to API Testing");
        Homepage home = new Homepage(driver);
        home.clickApiTesting();
        Assert.assertTrue(driver.getCurrentUrl().contains("api_list"));
    }

    @Test(priority = 7)
    public void verifyNavigationToContactUs() {
        test = extent.createTest("TC_ECOM_Home_07 - Verify Navigation to Contact Us");
        Homepage home = new Homepage(driver);
        home.clickContactUs();
        Assert.assertTrue(driver.getCurrentUrl().contains("contact_us"));
    }

    @Test(priority = 8)
    public void verifyNavigationToCart() {
        test = extent.createTest("TC_ECOM_Home_08 - Verify Navigation to Cart");
        Homepage home = new Homepage(driver);
        home.clickCart();
        Assert.assertTrue(driver.getCurrentUrl().contains("view_cart"));
    }
}
