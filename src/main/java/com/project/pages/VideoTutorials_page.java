package com.project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class VideoTutorials_page {
    WebDriver driver;

    // Locator for Video Tutorials link
    private By videoTutorialsLink = By.xpath("//a[contains(text(),'Video Tutorials')]");

    // Constructor
    public VideoTutorials_page(WebDriver driver) {
        this.driver = driver;
    }

    // Click Video Tutorials button
    public void clickVideoTutorials() {
        driver.findElement(videoTutorialsLink).click();
    }
}