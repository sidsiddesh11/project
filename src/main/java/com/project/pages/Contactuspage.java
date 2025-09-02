package com.project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Contactuspage {
    private WebDriver driver;

   
    private By nameField = By.xpath("//input[@name='name']");
    private By emailField = By.xpath("//input[@name='email']");
    private By subjectField = By.xpath("//input[@name='subject']");
    private By messageField = By.xpath("//textarea[@id='message']");
    private By uploadFile = By.xpath("//input[@name='upload_file']");
    private By submitButton = By.xpath("//input[@name='submit']");
    private By successMsg = By.xpath("//div[@class='status alert alert-success']");

   
    public Contactuspage(WebDriver driver) {
        this.driver = driver;
    }

 
    public void enterName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterSubject(String subject) {
        driver.findElement(subjectField).sendKeys(subject);
    }

    public void enterMessage(String message) {
        driver.findElement(messageField).sendKeys(message);
    }

    public void uploadFile(String filePath) {
        driver.findElement(uploadFile).sendKeys(filePath);
    }

    public void clickSubmit() throws InterruptedException {
        driver.findElement(submitButton).click();
        driver.switchTo().alert().accept(); 
        Thread.sleep(3000);
    }

 
    public boolean isSuccessMessageDisplayed() {
        return driver.findElement(successMsg).isDisplayed();
        
        
    }
    
}
