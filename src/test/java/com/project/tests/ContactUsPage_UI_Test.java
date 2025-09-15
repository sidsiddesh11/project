package com.project.tests;

import com.project.base.Basetest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class ContactUsPage_UI_Test extends Basetest {
    private void info(String msg) { test.log(Status.INFO, msg); }
    private void pass(String msg) { test.log(Status.PASS, "✅ " + msg); }

    @BeforeMethod(alwaysRun = true)
    public void openContactUs(Method m) {
        test = extent.createTest("TC_UI_ContactUs_UI: " + m.getName());
        info("Opening Contact Us page");
        driver.get("https://automationexercise.com/contact_us");
        pass("Contact Us page opened successfully");
    }
    @Test
    public void VerifyUrl() {
        info("Verifying page URL contains '/contact_us'");
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("/contact_us"), "Expected URL to contain '/contact_us' but found: " + actualUrl);
        pass("Page URL verified → " + actualUrl);
    }
    @Test
    public void VerifyLogo() {
        info("Checking 'Contact Us' heading is visible");
        Assert.assertTrue(driver.findElement(By.xpath("//*[normalize-space()='Contact Us']")).isDisplayed(),"Contact Us heading should be visible");
        pass("'Contact Us' heading is visible");
    }
    @Test
    public void VerifyHeaderButtons() {
        info("Checking visibility of all header buttons");
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[1]/a")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[2]/a")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[3]/a")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[4]/a")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[5]/a")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[6]/a")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[7]/a")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[8]/a")).isDisplayed());
        pass("All header buttons are visible");
    }
    @Test
    public void VerifyGetinTouch() {
        info("Checking 'GET IN TOUCH' heading");
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='contact-page']/div[2]/div[1]/div/h2")).isDisplayed(), "GET IN TOUCH heading should be visible");
        pass("'GET IN TOUCH' heading is visible");
    }
    @Test
    public void VerifyFeedbackforUs() {
        info("Checking 'FEEDBACK FOR US' heading");
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='contact-page']/div[2]/div[2]/div/h2")).isDisplayed(),"FEEDBACK FOR US heading should be visible");
        pass("'FEEDBACK FOR US' heading is visible");
    }
    @Test
    public void VerifyNameField() {
        info("Checking Name field placeholder");
        Assert.assertEquals(driver.findElement(By.cssSelector("input[data-qa='name']")).getAttribute("placeholder"),"Name", "Name placeholder mismatch");
        pass("Name field placeholder is correct");
    }
    @Test
    public void VerifyEmailField() {
        info("Checking Email field placeholder");
        Assert.assertEquals(driver.findElement(By.cssSelector("input[data-qa='email']")).getAttribute("placeholder"),"Email", "Email placeholder mismatch");
        pass("Email field placeholder is correct");
    }
    @Test
    public void VerifySubjectField() {
        info("Checking Subject field placeholder");
        Assert.assertEquals(driver.findElement(By.cssSelector("input[data-qa='subject']")).getAttribute("placeholder"),"Subject", "Subject placeholder mismatch");
        pass("Subject field placeholder is correct");
    }

    @Test
    public void VerifyYourMessageField() {
        info("Checking Message field placeholder");
        Assert.assertEquals(driver.findElement(By.cssSelector("textarea[data-qa='message']")).getAttribute("placeholder"), "Your Message Here", "Message placeholder mismatch");
        pass("Message field placeholder is correct");
    }

    @Test
    public void VerifyFileUploadField() {
        info("Checking file upload input is visible");
        Assert.assertTrue(driver.findElement(By.cssSelector("input[type='file']")).isDisplayed(), "Choose File should be visible");
        pass("File upload input is visible");
    }

    @Test
    public void VerifySubmitButtonField() {
        info("Checking Submit button is visible");
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='contact-us-form']/div[6]/input")).isDisplayed(),"Submit Button should be visible");
        pass("Submit button is visible");
    }
    @Test
    public void VerifyFeedbackEmailLinkPresent() {
        info("Checking feedback email link is visible");
        Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@href,'feedback@automationexercise.com')]")).isDisplayed(),"Feedback email link should be visible");
        pass("Feedback email link is visible");
    }
}
