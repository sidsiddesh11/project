package com.project.tests;

import com.project.pages.TestCasesPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class TestCasesPageTest {

    private WebDriver driver;
    private TestCasesPage testCasesPage;

    @BeforeClass
    public void setup() {
        // If you rely on chromedriver on PATH, this is enough.
        // Otherwise set System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.automationexercise.com/test_cases");

        testCasesPage = new TestCasesPage(driver);
        testCasesPage.initTestCaseLocators();
    }

    @Test
    public void testClickAllTestCases() {
        testCasesPage.clickAllTestCases();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
