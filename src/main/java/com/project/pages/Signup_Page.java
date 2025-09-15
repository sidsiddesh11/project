package com.project.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Signup_Page {
    private WebDriver driver;
    private WebDriverWait wait;

    public Signup_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Locators
    private By signupLoginLink = By.xpath("//a[normalize-space()='Signup / Login']");
    private By signupName = By.xpath("//input[@data-qa='signup-name']");
    private By signupEmail = By.xpath("//input[@data-qa='signup-email']");
    private By signupBtn = By.xpath("//button[@data-qa='signup-button']");

    private By mrRadio = By.id("id_gender1");
    private By mrsRadio = By.id("id_gender2");
    private By passwordField = By.id("password");
    private By daysDropdown = By.id("days");
    private By monthsDropdown = By.id("months");
    private By yearsDropdown = By.id("years");

    private By firstName = By.id("first_name");
    private By lastName = By.id("last_name");
    private By company = By.id("company");
    private By address1 = By.id("address1");
    private By address2 = By.id("address2");
    private By countryDropdown = By.id("country");
    private By state = By.id("state");
    private By city = By.id("city");
    private By zipcode = By.id("zipcode");
    private By mobileNumber = By.id("mobile_number");

    private By createAccountBtn = By.xpath("//button[@data-qa='create-account']");
    private By accountCreatedMessage = By.xpath("//b[normalize-space()='Account Created!']");

    // Actions
    public void clickSignupLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(signupLoginLink)).click();
    }

    public void enterSignupDetails(String name, String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(signupName)).sendKeys(name);
        driver.findElement(signupEmail).sendKeys(email);
        driver.findElement(signupBtn).click();
    }

    public void fillAccountInfo(String title, String pwd, String day, String month, String year) {
        if (title.equalsIgnoreCase("Mr")) {
            wait.until(ExpectedConditions.elementToBeClickable(mrRadio)).click();
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(mrsRadio)).click();
        }

        driver.findElement(passwordField).sendKeys(pwd);

        new Select(driver.findElement(daysDropdown)).selectByVisibleText(day);
        new Select(driver.findElement(monthsDropdown)).selectByVisibleText(month);
        new Select(driver.findElement(yearsDropdown)).selectByVisibleText(year);

        // Wait until first address field appears (ensures page loaded fully)
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
    }

    public void fillAddress(String fname, String lname, String comp, String addr1, String addr2,
                            String country, String st, String cty, String zip, String mobile) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).sendKeys(fname);
        driver.findElement(lastName).sendKeys(lname);
        driver.findElement(company).sendKeys(comp);
        driver.findElement(address1).sendKeys(addr1);
        driver.findElement(address2).sendKeys(addr2);

        new Select(driver.findElement(countryDropdown)).selectByVisibleText(country);

        driver.findElement(state).sendKeys(st);
        driver.findElement(city).sendKeys(cty);
        driver.findElement(zipcode).sendKeys(zip);
        driver.findElement(mobileNumber).sendKeys(mobile);
    }

    public void clickCreateAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(createAccountBtn)).click();
    }

    public boolean isAccountCreated() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(accountCreatedMessage)).isDisplayed();
    }
}
