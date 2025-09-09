
package com.project.tests;

import com.project.base.Basetest;
import com.project.pages.Signup_Page;
import com.project.utilities.ExcelUtilities;
import com.aventstack.extentreports.Status;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class Signup_Test extends Basetest {

    @DataProvider(name = "signupData")
    public Object[][] getSignupData() throws IOException {
        String excelPath = System.getProperty("user.dir") + "/src/test/resources/SignupData_Full.xlsx";
        return ExcelUtilities.getData(excelPath, "Sheet1");
    }

    @Test(dataProvider = "signupData")
    public void testSignupFlow(String name, String email, String password,
                               String title, String day, String month, String year,
                               String fname, String lname, String company,
                               String addr1, String addr2, String country,
                               String state, String city, String zipcode,
                               String mobile) {
        test = extent.createTest("Signup Test - " + name);

        Signup_Page signupPage = new Signup_Page(driver);

        // Step 1: Navigate to signup/login
        test.log(Status.INFO, "Clicking on Signup / Login link");
        signupPage.clickSignupLogin();

        // Step 2: Enter initial signup details
        test.log(Status.INFO, "Entering Name: " + name + " and Email: " + email);
        signupPage.enterSignupDetails(name, email);

        // Step 3: Fill Account Info
        test.log(Status.INFO, "Filling Account Information - Title: " + title + ", DOB: " + day + " " + month + " " + year);
        signupPage.fillAccountInfo(title, password, day, month, year);

        // Step 4: Fill Address Info
        test.log(Status.INFO, "Filling Address Details: " + fname + " " + lname + ", " + company + ", " + addr1 + ", " + country);
        signupPage.fillAddress(fname, lname, company, addr1, addr2, country, state, city, zipcode, mobile);

        // Step 5: Create Account
        test.log(Status.INFO, "Clicking Create Account button");
        signupPage.clickCreateAccount();

        // Step 6: Verification
        test.log(Status.INFO, "Verifying Account Creation");
        Assert.assertTrue(signupPage.isAccountCreated(), "Account creation failed!");

        test.log(Status.PASS, "✅ Account Created Successfully for: " + name);
    }
}