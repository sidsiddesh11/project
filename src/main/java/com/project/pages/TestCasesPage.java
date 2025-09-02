package com.project.pages;

import org.openqa.selenium.*;
import java.util.ArrayList;
import java.util.List;

public class TestCasesPage {
    private final WebDriver driver;
    private final List<By> testCaseLinkLocators = new ArrayList<>();

    public TestCasesPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Builds robust, index-based locators for each Test Case row.
     * This mirrors the approach in API_page but targets the Test Cases list.
     */
    public void initTestCaseLocators() {
        // Count all “Test Case …” links in the accordion list
        List<WebElement> links = driver.findElements(
                By.cssSelector(".panel-group .panel .panel-heading h4 a"));

        testCaseLinkLocators.clear();
        for (int i = 1; i <= links.size(); i++) {
            // Index-based XPath to avoid stale references after expanding/collapsing
            By indexed = By.xpath("(//div[contains(@class,'panel-group')]//div[contains(@class,'panel')]//h4/a)[" + i + "]");
            testCaseLinkLocators.add(indexed);
        }
    }

    /**
     * Scrolls each row into view and clicks to expand it.
     * Uses JS scroll and a normal WebElement click (like your API_page).
     */
    public void clickAllTestCases() {
        for (By locator : testCaseLinkLocators) {
            try {
                WebElement link = driver.findElement(locator);

                // Scroll into view
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);

                // Click (regular click; reliable on this page)
                link.click();

                System.out.println("Clicked: " + locator);

                // Small wait so visual expand is noticeable and DOM settles
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println("Could not click: " + locator + " - " + e.getMessage());
            }
        }
    }
}
