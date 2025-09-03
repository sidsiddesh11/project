package com.project.tests; 

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import com.project.pages.API_page;
import com.aventstack.extentreports.Status;
import com.project.base.Basetest;

public class ApiPageTest extends Basetest {

    API_page apiPage;

    @BeforeMethod
    public void setupApiPage() {
        driver.get("https://www.automationexercise.com/api_list");

        apiPage = new API_page(driver);
        apiPage.initApiLocators();

        test = extent.createTest("API Page Test - Click All APIs");
    }

    @Test
    public void testClickAllApis() {
        apiPage.clickAllApis();
        test.log(Status.PASS, "All API links clicked successfully");
    }

    @AfterClass
    public void tearDownApiPage() {
        if (driver != null) {
            driver.quit();
        }
    }
}
