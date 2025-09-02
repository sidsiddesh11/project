package com.project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Homepage {
    private WebDriver driver;

    // ---------- Locators ----------
    private By logo = By.xpath("//img[@alt='Website for automation practice']");
    private By homeLink = By.xpath("//a[contains(text(),'Home')]");
    private By productsLink = By.xpath("//a[@href='/products']");
    private By signupLoginLink = By.xpath("//a[@href='/login']");
    private By testCasesLink = By.xpath("//a[@href='/test_cases']");
    private By apiTestingLink = By.xpath("//a[@href='/api_list']");
    private By contactUsLink = By.xpath("//a[@href='/contact_us']");
    private By cartLink = By.xpath("//a[@href='/view_cart']");

    // ---------- Constructor ----------
    public Homepage(WebDriver driver) {
        this.driver = driver;
    }

    // ---------- Actions ----------
    public boolean isLogoDisplayed() {
        return driver.findElement(logo).isDisplayed();
    }

    public void clickHome() {
        driver.findElement(homeLink).click();
    }

    public void clickProducts() {
        driver.findElement(productsLink).click();
    }

    public void clickSignupLogin() {
        driver.findElement(signupLoginLink).click();
    }

    public void clickTestCases() {
        driver.findElement(testCasesLink).click();
    }

    public void clickApiTesting() {
        driver.findElement(apiTestingLink).click();
    }

    public void clickContactUs() {
        driver.findElement(contactUsLink).click();
    }

    public void clickCart() {
        driver.findElement(cartLink).click();
    }

    // ---------- Validation ----------
    public String getHomePageTitle() {
        return driver.getTitle();
    }
}
