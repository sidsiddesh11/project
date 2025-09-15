package com.project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;

import java.util.ArrayList;
import java.util.List;

public class API_page {
    WebDriver driver;

    public API_page(WebDriver driver) {
        this.driver = driver;
    }

    // Store all API locators dynamically (clickable <a> elements)
    private List<By> apiLocators = new ArrayList<>();

    // Locator for page heading
    private By pageHeading = By.xpath("//*[@id='form']/div/div[1]/div/h2/b");

    // Initialize API locators
    public void initApiLocators() {
        apiLocators.clear();
        for (int i = 2; i <= 15; i++) {
            String css = "#form > div > div:nth-child(" + i + ") > div > div.panel-heading > h4 > a";
            apiLocators.add(By.cssSelector(css));
        }
    }

    // 🔹 Get the page heading text
    public String getPageHeading() {
        return driver.findElement(pageHeading).getText().trim();
    }

    // 🔹 Click an API link and check if panel expanded
    public boolean clickAndValidateApi(int index) {
        try {
            By locator = apiLocators.get(index);
            WebElement apiLink = driver.findElement(locator);

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", apiLink);

            // Click
            apiLink.click();
            Thread.sleep(500);

            // After click, panel body should be visible
            String panelCss = "#form > div > div:nth-child(" + (index + 2) + ") > div > div.panel-collapse";
            WebElement panelBody = driver.findElement(By.cssSelector(panelCss));

            boolean expanded = panelBody.getAttribute("class").contains("in"); // "in" = expanded in Bootstrap
            return expanded;

        } catch (Exception e) {
            System.out.println("API click failed at index: " + index + " - " + e.getMessage());
            return false;
        }
    }

    public int getApiCount() {
        return apiLocators.size();
    }
}
