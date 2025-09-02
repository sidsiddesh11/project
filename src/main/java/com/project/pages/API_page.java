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

    // Store all API locators dynamically (clickable <a> element)
    private List<By> apiLocators = new ArrayList<>();

    public void initApiLocators() {
        for (int i = 2; i <= 15; i++) {
            String css = "#form > div > div:nth-child(" + i + ") > div > div.panel-heading > h4 > a";
            apiLocators.add(By.cssSelector(css));
        }
    }

    // 🔹 Updated clickAllApis() using scroll + JS click
    public void clickAllApis() {
        for (By locator : apiLocators) {
            try {
                WebElement apiLink = driver.findElement(locator);

                // Scroll into view
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", apiLink);

                // Normal click instead of JS
                apiLink.click();

                System.out.println("Clicked: " + locator.toString());

                Thread.sleep(1000); // 1 second delay to see the click
            } catch (Exception e) {
                System.out.println("Could not click: " + locator.toString() + " - " + e.getMessage());
            }
        }
    }}