package com.project.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import java.util.ArrayList;
import java.util.List;

public class TestCasesPage {
    private final WebDriver driver;
    private final List<By> testCaseLinkLocators = new ArrayList<>();

    public TestCasesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void initTestCaseLocators() {
        // Count all “Test Case …” links in the accordion list
        List<WebElement> links = driver.findElements(
                By.cssSelector(".panel-group .panel .panel-heading h4 a"));

        testCaseLinkLocators.clear();
        for (int i = 1; i <= links.size(); i++) {
            // Index-based XPath to avoid stale references
            By indexed = By.xpath("(//div[contains(@class,'panel-group')]//div[contains(@class,'panel')]//h4/a)[" + i + "]");
            testCaseLinkLocators.add(indexed);
        }
    }

    public void clickAllTestCases(ExtentTest test) { // ✅ Method now accepts ExtentTest object
        test.log(Status.INFO, "Found " + testCaseLinkLocators.size() + " test cases to click.");
        for (By locator : testCaseLinkLocators) {
            String testCaseName = "[Unknown]"; // Default name
            try {
                WebElement link = driver.findElement(locator);
                testCaseName = link.getText(); // Get link text for better logging
                // ✅ Log the action before performing it
                test.log(Status.INFO, "Attempting to click: '" + testCaseName + "'");
                // Scroll into view
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);
                // Click the element
                link.click();
                // ✅ Log the successful result
                test.log(Status.PASS, "Successfully clicked: '" + testCaseName + "'");
                // Small wait for the DOM to settle
                Thread.sleep(500);
            } catch (Exception e) {
                // ✅ Log the failure
                test.log(Status.FAIL, "Could not click '" + testCaseName + "'. Locator: " + locator + ". Error: " + e.getMessage());
                // Break the loop on failure to avoid a cascade of errors
                break;
            }
        }
    }
}
