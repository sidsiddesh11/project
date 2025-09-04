package com.project.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Contactuspage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public Contactuspage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators 
    private By headingGetInTouch= By.xpath("//h2[normalize-space()='Get In Touch' or normalize-space()='GET IN TOUCH']");
    private By labelFeedbackForUs= By.xpath("//*[contains(normalize-space(),'Feedback For Us') or contains(normalize-space(),'Feedback for us')]");
    private By nameField= By.cssSelector("input[name='name']");
    private By emailField= By.cssSelector("input[name='email']");
    private By subjectField= By.cssSelector("input[name='subject']");
    private By messageField= By.cssSelector("textarea#message");
    private By uploadFile= By.cssSelector("input[name='upload_file']");
    private By submitButton= By.cssSelector("input[name='submit']");
    private By successMsg= By.cssSelector("div.status.alert.alert-success");
    private By homeButtonAfterSubmit= By.xpath("//a[normalize-space()='Home']");


    public boolean isGetInTouchVisible() {
        try { return wait.until(ExpectedConditions.visibilityOfElementLocated(headingGetInTouch)).isDisplayed(); }
        catch (TimeoutException e) { return false; }
    }

    public boolean isFeedbackForUsVisible() {
        try { return wait.until(ExpectedConditions.visibilityOfElementLocated(labelFeedbackForUs)).isDisplayed(); }
        catch (TimeoutException e) { return false; }
    }

    public void enterName(String v)    { WebElement e = driver.findElement(nameField);    e.clear(); e.sendKeys(v); }
    public void enterEmail(String v)   { WebElement e = driver.findElement(emailField);   e.clear(); e.sendKeys(v); }
    public void enterSubject(String v) { WebElement e = driver.findElement(subjectField); e.clear(); e.sendKeys(v); }
    public void enterMessage(String v) { WebElement e = driver.findElement(messageField); e.clear(); e.sendKeys(v); }
    public void uploadFile(String path){ driver.findElement(uploadFile).sendKeys(path); }


    public boolean clickSubmitAndAcceptIfAlert(int waitSecondsForAlert) {
        driver.findElement(submitButton).click();
        try {
            Alert a = new WebDriverWait(driver, Duration.ofSeconds(waitSecondsForAlert)).until(ExpectedConditions.alertIsPresent());
            a.accept();
            return true;
        } catch (TimeoutException te) {
            return false;
        }
    }

    public boolean isSuccessMessageDisplayed() {
        try { return wait.until(ExpectedConditions.visibilityOfElementLocated(successMsg)).isDisplayed(); }
        catch (TimeoutException e) { return false; }
    }

    public void clickHomeButton() {
        wait.until(ExpectedConditions.elementToBeClickable(homeButtonAfterSubmit)).click();
    }

  
    private boolean isRequired(By locator) {
        String val = driver.findElement(locator).getAttribute("required");
        return val != null && val.equalsIgnoreCase("true");
    }
    public boolean isNameRequired()    { return isRequired(nameField); }
    public boolean isEmailRequired()   { return isRequired(emailField); }
    public boolean isSubjectRequired() { return isRequired(subjectField); }
    public boolean isMessageRequired() { return isRequired(messageField); }


    public boolean isFileChosen() {
        String v = driver.findElement(uploadFile).getAttribute("value");
        return v != null && !v.trim().isEmpty();
    }
}
