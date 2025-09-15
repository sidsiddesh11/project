package com.project.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Subscription_field {
    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By subscriptionEmail = By.id("susbscribe_email");   // input field
    By subscribeButton = By.id("subscribe");            // button
    By successMessage = By.cssSelector(".alert-success.alert"); // success toast
    By errorMessage = By.cssSelector(".alert-danger.alert");    // error toast

    public Subscription_field(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ✅ Enter email into subscription field
    public void enterEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(subscriptionEmail));
        emailField.clear();
        emailField.sendKeys(email);
    }

    // ✅ Click subscribe button
    public void clickSubscribe() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(subscribeButton));
        button.click();
    }

    // ✅ Scroll to subscription section
    public void scrollToSubscription() {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(subscriptionEmail));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", emailField);
    }

    // ✅ Get text entered in email field
    public String getEnteredEmail() {
        try {
            WebElement emailField = driver.findElement(subscriptionEmail);
            return emailField.getAttribute("value");
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    // ✅ Success message visible?
    public boolean isSuccessMessageVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return driver.findElement(successMessage).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    // ✅ Error message visible?
    public boolean isErrorMessageVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return driver.findElement(errorMessage).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    // ✅ Get success message text
    public String getSuccessMessage() {
        try {
            return driver.findElement(successMessage).getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    // ✅ Get error message text
    public String getErrorMessage() {
        try {
            return driver.findElement(errorMessage).getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    // ✅ Check if subscription email field is displayed
    public boolean isEmailFieldDisplayed() {
        try {
            return driver.findElement(subscriptionEmail).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // ✅ Check if subscribe button is enabled
    public boolean isSubscribeButtonEnabled() {
        try {
            return driver.findElement(subscribeButton).isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
