package com.project.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.project.base.Basetest;
import com.project.pages.Login_page;
import com.project.utilities.ExcelUtilities;

public class Login_Tests extends Basetest {

    String excelPath = System.getProperty("user.dir") + "/src/test/resources/Login_TestCases_WithData .xlsx";
    String sheetName = "Sheet1";

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws Exception {
        return ExcelUtilities.getData(excelPath, sheetName);
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String sno, String testCaseId, String objective, String preconditions,
                          String steps, String testData, String expectedResult,
                          String actualResult, String status) {

        // Create ExtentTest for reporting
        test = extent.createTest(testCaseId + " - " + objective);

        Login_page loginPage = new Login_page(driver);

        try {
            // ===== Handle logout test case =====
            if (testCaseId.toLowerCase().contains("logout")) {
                test.info("Executing logout test case");

                loginPage.logout();
                test.info("Clicked on Logout link");

                boolean actual = loginPage.isAtLoginPage();
                Assert.assertTrue(actual, "Logout Test Failed for " + testCaseId);

                test.pass("Logout successful - User is at login page");
                return;
            }

            // ===== Normal login test case =====
            test.info("Navigating to Signup/Login page");
            loginPage.clickSignupLogin();

            // Extract email & password from testData column
            String email = "";
            String password = "";
            if (testData.contains("Email:")) {
                String[] parts = testData.split("\\|");
                email = parts[0].replace("Email:", "").trim();
                if (parts.length > 1) {
                    password = parts[1].replace("Password:", "").trim();
                }
            }

            test.info("Login with Email: " + email + " | Password: " + password);
            loginPage.login(email, password);

            boolean actual;
            if (expectedResult.toLowerCase().contains("success")) {
                test.info("Verifying login success...");
                actual = loginPage.isLoggedInAsVisible();
                Assert.assertTrue(actual, "Login Failed for " + testCaseId);
                test.pass("Login successful - User is logged in");
            } else {
                test.info("Verifying login error...");
                actual = loginPage.isLoginErrorVisible();
                Assert.assertTrue(actual, "Login Error not visible for " + testCaseId);
                test.pass("Login error correctly displayed");
            }

        } catch (Exception e) {
            test.fail("Test failed due to exception: " + e.getMessage());
            throw e; // rethrow so TestNG marks it failed
        }
    }
}
