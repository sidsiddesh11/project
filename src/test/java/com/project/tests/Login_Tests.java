package com.project.tests;

import com.aventstack.extentreports.Status;
import com.project.base.Basetest;
import com.project.pages.Login_page;
import com.project.utilities.ScreenshotUtilities;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Login_Tests extends Basetest {

    Login_page page;
    String testEmail = "existingtest@mail.com"; // Use a valid pre-created account
    String testPassword = "Password@123";

    @BeforeMethod
    public void setUpPage() {
        driver.get("https://automationexercise.com/");
        page = new Login_page(driver);
    }

    // ---------- SIGNUP TEST CASES ----------
    @Test
    public void testSignupWithExistingEmail() {
        try {
            test = extent.createTest("Signup with Existing Email");
            page.clickSignupLogin();
            page.enterSignup("TestUser", testEmail);
            Assert.assertTrue(page.isExistingEmailErrorVisible(), "Error message not shown for existing email");
            test.log(Status.PASS, "Existing email error displayed correctly");
        } catch (Exception e) {
            captureFailure("testSignupWithExistingEmail", e);
        }
    }

    @Test
    public void testSignupWithEmptyFields() {
        try {
            test = extent.createTest("Signup with Empty Fields");
            page.clickSignupLogin();
            page.enterSignup("", "");
            // Since the site uses HTML5 required fields, no navigation should happen
            Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Signup should not proceed with empty fields");
            test.log(Status.PASS, "Signup prevented with empty fields");
        } catch (Exception e) {
            captureFailure("testSignupWithEmptyFields", e);
        }
    }

    @Test
    public void testSignupWithInvalidEmail() {
        try {
            test = extent.createTest("Signup with Invalid Email Format");
            page.clickSignupLogin();
            page.enterSignup("TestUser", "invalidEmail");
            // HTML5 should prevent submission
            Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Signup should not proceed with invalid email");
            test.log(Status.PASS, "Signup prevented with invalid email format");
        } catch (Exception e) {
            captureFailure("testSignupWithInvalidEmail", e);
        }
    }

    // ---------- LOGIN TEST CASES ----------
    @Test
    public void testValidLogin() {
        try {
            test = extent.createTest("Valid Login");
            page.clickSignupLogin();
            page.login(testEmail, testPassword);
            Assert.assertTrue(page.isLoggedInAsVisible(), "User not logged in with valid credentials");
            test.log(Status.PASS, "Valid login successful");
        } catch (Exception e) {
            captureFailure("testValidLogin", e);
        }
    }

    @Test
    public void testInvalidLogin() {
        try {
            test = extent.createTest("Invalid Login");
            page.clickSignupLogin();
            page.login("wrong@mail.com", "wrongpass");
            Assert.assertTrue(page.isLoginErrorVisible(), "Error not shown for invalid login");
            test.log(Status.PASS, "Invalid login error displayed");
        } catch (Exception e) {
            captureFailure("testInvalidLogin", e);
        }
    }

    @Test
    public void testLoginWithEmptyFields() {
        try {
            test = extent.createTest("Login with Empty Fields");
            page.clickSignupLogin();
            page.login("", "");
            // HTML5 required attribute should block submission
            Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Login should not proceed with empty fields");
            test.log(Status.PASS, "Login prevented with empty fields");
        } catch (Exception e) {
            captureFailure("testLoginWithEmptyFields", e);
        }
    }

    @Test
    public void testLogoutAfterLogin() {
        try {
            test = extent.createTest("Logout after Login");
            page.clickSignupLogin();
            page.login(testEmail, testPassword);
            Assert.assertTrue(page.isLoggedInAsVisible(), "Login failed before logout");
            page.logout();
            Assert.assertTrue(page.isAtLoginPage(), "User not redirected to login page after logout");
            test.log(Status.PASS, "Logout successful and user redirected to login page");
        } catch (Exception e) {
            captureFailure("testLogoutAfterLogin", e);
        }
    }

    // --------- Helper for Failures ---------
    private void captureFailure(String testName, Exception e) {
        try {
            String screenshotPath = ScreenshotUtilities.Capture(driver, testName);
            test.addScreenCaptureFromPath(screenshotPath);
        } catch (Exception io) {
            io.printStackTrace();
        }
        test.log(Status.FAIL, "Test failed: " + e.getMessage());
        Assert.fail(e.getMessage());
    }
}
