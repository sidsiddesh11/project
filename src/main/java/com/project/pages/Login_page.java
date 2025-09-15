package com.project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login_page {
    WebDriver driver;

    public Login_page(WebDriver driver) {
        this.driver = driver;
    }

    // --------- Common Locators ---------
    private By signupLoginLink = By.xpath("//a[contains(text(),'Signup / Login')]");
    private By loggedInUser = By.xpath("//a[contains(text(),'Logged in as')]");
    private By logoutLink = By.xpath("//a[contains(text(),'Logout')]");

    // --------- Signup Locators ---------
    private By nameInput = By.xpath("//input[@name='name']");
    private By emailInput = By.xpath("//input[@data-qa='signup-email']");
    private By signupButton = By.xpath("//button[text()='Signup']");
    private By existingEmailError = By.xpath("//p[contains(text(),'Email Address already exist!')]");

    // --------- Login Locators ---------
    private By loginEmailInput = By.xpath("//input[@data-qa='login-email']");
    private By loginPasswordInput = By.xpath("//input[@data-qa='login-password']");
    private By loginButton = By.xpath("//button[text()='Login']");
    private By loginError = By.xpath("//p[contains(text(),'Your email or password is incorrect!')]");

    // --------- Actions ---------
    public void clickSignupLogin() {
        driver.findElement(signupLoginLink).click();
    }

    // ---- Signup ----
    public void enterSignup(String name, String email) {
        driver.findElement(nameInput).clear();
        driver.findElement(nameInput).sendKeys(name);
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(signupButton).click();
    }

    public boolean isExistingEmailErrorVisible() {
        return driver.findElement(existingEmailError).isDisplayed();
    }

    // ---- Login ----
    public void login(String email, String password) {
        driver.findElement(loginEmailInput).clear();
        driver.findElement(loginEmailInput).sendKeys(email);
        driver.findElement(loginPasswordInput).clear();
        driver.findElement(loginPasswordInput).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public boolean isLoginErrorVisible() {
        return driver.findElement(loginError).isDisplayed();
    }

    public boolean isLoggedInAsVisible() {
        return driver.findElement(loggedInUser).isDisplayed();
    }

    public void logout() {
        driver.findElement(logoutLink).click();
    }

    public boolean isAtLoginPage() {
        return driver.findElement(loginButton).isDisplayed();
    }
}
