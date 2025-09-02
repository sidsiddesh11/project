package com.project.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.project.pages.API_page;

public class ApiPageTest {

    WebDriver driver;
    API_page apiPage;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.automationexercise.com/api_list");

        apiPage = new API_page(driver);
        apiPage.initApiLocators();
    }

    @Test
    public void testClickAllApis() {
        apiPage.clickAllApis();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}