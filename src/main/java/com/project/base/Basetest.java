package com.project.base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.project.utilities.ExtentManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Basetest {
	protected WebDriver driver;
	protected ExtentReports extent;
	protected ExtentTest test;
	@BeforeSuite
	public void setupreport()
	{
		extent=ExtentManager.getinstance();
	}
	
	
	@BeforeMethod
	  public void setup() {
		  System.out.println("Before method");
		  WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
			driver.get("https://automationexercise.com/");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	  }
	  @AfterMethod
	  public void teardown() {
		  System.out.println("After method");
		  driver.quit();
	  }
	
	@AfterSuite
public void flushreport()

{
	extent.flush();
}
}