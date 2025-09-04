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

        // Assign test to Basetest's static test for reporting
        Basetest.test = extent.createTest(testCaseId + " - " + objective);

        Login_page loginPage = new Login_page(driver);

        // ===== Handle logout test case =====
        if (testCaseId.toLowerCase().contains("logout")) {
            loginPage.logout();
            boolean actual = loginPage.isAtLoginPage();
            Assert.assertTrue(actual,
                    "Logout Test Failed for " + testCaseId + " | Expected: " + expectedResult);
            return;
        }

        // ===== Normal login test case =====
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

        loginPage.login(email, password);

        boolean actual;
        if (expectedResult.toLowerCase().contains("success")) {
            actual = loginPage.isLoggedInAsVisible();
        } else {
            actual = loginPage.isLoginErrorVisible();
        }

        Assert.assertTrue(actual,
                "Test Failed for " + testCaseId + " | Expected: " + expectedResult);
    }
}
