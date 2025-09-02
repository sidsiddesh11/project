package com.project.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SignUpPage_newuser
{
	public static void main(String[] args) {
        // Setup WebDriver and launch the browser
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // Set implicit wait for elements
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Navigate to the URL
        driver.get("https://automationexercise.com/signup");

        //TEST1
        //CHECKING TITLKE OF THE PAGE
        System.out.println("The title is: " + driver.getTitle());
        
        
        //TEST2 WITH VALID INPUTS SIGNUP
        driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[3]/div/form/input[2]")).sendKeys("SID");
        driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[3]/div/form/input[3]")).sendKeys("sid7@gmail.com");
        
        driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[3]/div/form/button")).click();
        
        //WebElement accountCreatedMessage = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[3]/div/form/button"));
        
        //new page
        WebElement mrRadio = driver.findElement(By.xpath("//input[@id='id_gender1']"));
        mrRadio.click();
      
        //password
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("sid_22544");
        
        Select daySelect = new Select(driver.findElement(By.id("days")));
        daySelect.selectByValue("15");

        Select monthSelect = new Select(driver.findElement(By.id("months")));
        monthSelect.selectByValue("5");

        Select yearSelect = new Select(driver.findElement(By.id("years")));
        yearSelect.selectByValue("1995");

       //checkboxes 
        
        WebElement newsletter = driver.findElement(By.id("newsletter"));
        newsletter.click();

        WebElement offers = driver.findElement(By.id("optin"));
        offers.click();
        
        
        //  Fill First Name 
        driver.findElement(By.id("first_name")).sendKeys("SIDDHART");

        // Fill Last Name 
        driver.findElement(By.id("last_name")).sendKeys("JOSHI");

        // Fill Company (optional) 
        driver.findElement(By.id("company")).sendKeys("CGI");

        // Fill Address
        driver.findElement(By.id("address1")).sendKeys("123 Main Street");

        // Fill Address 2 (optional) 
        driver.findElement(By.id("address2")).sendKeys("Apt 4B");

        //  Select Country 
        Select countrySelect = new Select(driver.findElement(By.id("country")));
        countrySelect.selectByVisibleText("India");

        // Fill State 
        driver.findElement(By.id("state")).sendKeys("Karnataka");

        // Fill City 
        driver.findElement(By.id("city")).sendKeys("Bangalore");

        //Fill Zipcode 
        driver.findElement(By.id("zipcode")).sendKeys("560001");

        //  Fill Mobile Number
        driver.findElement(By.id("mobile_number")).sendKeys("9876543210");

        //  Click Create Account Button
        driver.findElement(By.xpath("//button[text()='Create Account']")).click();


        driver.get("https://automationexercise.com/signup");
        //TEST2.0
        //driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/input[2]")).sendKeys("sid1@gmail.com");
        //driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/input[3]")).sendKeys("sid22544");
        
        //driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/button")).click();
        
       // WebElement loginCreatedMessage = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/button"));
        //System.out.println("login is done: " + loginCreatedMessage.getText());
        
        driver.quit();
	
}
}
