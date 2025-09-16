package com.project.tests;

import com.project.base.Basetest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

public class UI_Test_Case extends Basetest {
    private void info(String msg) { test.log(Status.INFO, msg); }
    private void pass(String msg) { test.log(Status.PASS, "✅ " + msg); }

    @BeforeMethod(alwaysRun = true)
    public void createNode(Method m) {
        test = extent.createTest("TC_UI_AllPages : " + m.getName());
    }

    // 1) HOME
    @Test(priority = 1)
    public void Home_VerifyUrl() {
        info("Opening Home page");
        driver.get("https://automationexercise.com/");
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("automationexercise.com"), "Home URL mismatch: " + actualUrl);
        pass("Home URL verified → " + actualUrl);
    }

    @Test(priority = 2)
    public void Home_VerifyHeaderButtons() {
        info("Checking header buttons on Home");
        driver.get("https://automationexercise.com/");
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[1]/a")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[2]/a")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[3]/a")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[4]/a")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[5]/a")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[6]/a")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[7]/a")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[8]/a")).isDisplayed());
        pass("Home Header- is shown");
        pass("Product Header - shown");
        pass("Cart Header - shown");
        pass("Signup/Login Header - shown");
        pass("Test Cases Header - shown");
        pass("API Testing Header - shown");
        pass("Video Tutorials Header - shown");
        pass("Contact Us Header - shown");
    }

    //  2) PRODUCTS

    @Test(priority = 3)
    public void Products_VerifyUrl() {
        info("Opening Products page");
        driver.get("https://automationexercise.com/products");
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("/products"), "Expected /products but got: " + actualUrl);
        pass("Products URL verified → " + actualUrl);
    }
    
    @Test(priority = 4)
    public void VerifyLogo() {
        info("Checking for logo");
        driver.get("https://automationexercise.com/");
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div/div[1]/div/a/img")).isDisplayed(),
                "Logo should be visible");
        pass("Logo is visible");
    }
  

    @Test(priority = 5)
    public void Products_VerifyCardsPresent() {
        info("Check at least one product card");
        driver.get("https://automationexercise.com/products");
            List<WebElement> cards = driver.findElements(By.cssSelector(".product-image-wrapper"));
            Assert.assertTrue(cards.size() > 0, "At least one product card should be visible");
        pass("Product cards present: " + cards.size());
    }
    
    
    @Test(priority = 6)
    public void VerifyViewProductName() {
        // Click "View Product"
        driver.findElement(By.cssSelector("a[href='/product_details/1']")).click();

        // Product name
        Assert.assertTrue(driver.findElement(By.xpath("//*[normalize-space()='Blue Top']")).isDisplayed(),
                "Product name should be visible");
    }
    @Test(priority = 7)
    public void VerifyViewProductPrice() {
        // Click "View Product"
    	driver.findElement(By.cssSelector("a[href='/product_details/1']")).click();

        // Product price
        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(normalize-space(),'Rs. 500')]")).isDisplayed(),
                "Product price should be visible");
    }
    @Test(priority = 8)
    public void VerifyViewProductQuantity() {
        // Click "View Product"
    	driver.findElement(By.cssSelector("a[href='/product_details/1']")).click();

        // Product Quantity
        Assert.assertTrue(driver.findElement(By.id("quantity")).isDisplayed(),
                "Quantity input should be visible");
    }
    @Test(priority = 9)
    public void VerifyViewProductAddtoCart() {
        // Click "View Product"
    	driver.findElement(By.cssSelector("a[href='/product_details/1']")).click();

        // Product Add to Cart
        Assert.assertTrue(driver.findElement(By.xpath("//button[normalize-space()='Add to cart']")).isDisplayed(),
                "Add to cart button should be visible");
    }

    @Test(priority = 10)
    public void VerifyShortDescriptionVisible() {
    	driver.findElement(By.cssSelector("a[href='/product_details/1']")).click();

        // Availability, Condition, Brand
        Assert.assertTrue(driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[2]/div[2]/div/p[2]")).isDisplayed(),
                "Availability should be visible");
        Assert.assertTrue(driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[2]/div[2]/div/p[3]")).isDisplayed(),
                "Condition should be visible");
        Assert.assertTrue(driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[2]/div[2]/div/p[4]")).isDisplayed(),
                "Brand should be visible"); 
    }
    @Test(priority = 11)
    public void VerifyReviewSectionDetails() {
    	driver.findElement(By.cssSelector("a[href='/product_details/1']")).click();

        // Review section
        Assert.assertTrue(driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[3]/div[1]")).isDisplayed(),"Review section header should be visible");
        pass("Write your review is visible");
    }
    
    // 3) CART 
    
    @Test(priority = 12)
    public void Cart_VerifyUrl() {
        info("Opening Cart page");
        driver.get("https://automationexercise.com/view_cart");
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("/view_cart"), "Expected /view_cart but got: " + actualUrl);
        pass("Cart URL verified → " + actualUrl);
    }
    
    @Test(priority = 13)
    public void Cart_VerifyUI() {
        info("Opening Cart page");
        driver.get("https://automationexercise.com/view_cart");
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"cart_items\"]/div/div[1]/ol/li[2]")).isDisplayed(), "Cart heading");
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"empty_cart\"]/p/b")).isDisplayed(), "Cart table");
        pass("Cart UI Verified");
        pass("Cart Table is Verified");
    }

    // 4) SIGNUP / LOGIN 
    
    @Test(priority = 14)
    public void SignupLogin_VerifyUrl() {
        info("Opening SignUp/Login page");
        driver.get("https://automationexercise.com/login");
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("/login"), "Expected /login but got: " + actualUrl);
        pass("Sign Up / Login URL verified → " + actualUrl);
    }
    
    @Test(priority = 15)
    public void SignupLogin_VerifyUI() {
        info("Opening Signup/Login page");
        driver.get("https://automationexercise.com/login");
        Assert.assertTrue(driver.findElement(By.xpath("//h2[normalize-space()='New User Signup!']")).isDisplayed(), "New User heading");
        Assert.assertTrue(driver.findElement(By.cssSelector("input[data-qa='signup-name']")).isDisplayed(), "Signup name");
        Assert.assertTrue(driver.findElement(By.cssSelector("input[data-qa='signup-email']")).isDisplayed(), "Signup email");
        Assert.assertTrue(driver.findElement(By.cssSelector("button[data-qa='signup-button']")).isDisplayed(), "Signup button");
        Assert.assertTrue(driver.findElement(By.xpath("//h2[normalize-space()='Login to your account']")).isDisplayed(), "Login heading");
        Assert.assertTrue(driver.findElement(By.cssSelector("input[data-qa='login-email']")).isDisplayed(), "Login email");
        Assert.assertTrue(driver.findElement(By.cssSelector("input[data-qa='login-password']")).isDisplayed(), "Login password");
        Assert.assertTrue(driver.findElement(By.cssSelector("button[data-qa='login-button']")).isDisplayed(), "Login button");
        pass("Signup/Login UI verified");
    }

    // 5) TEST CASES 
    
    @Test(priority = 16)
    public void TestCase_VerifyUrl() {
        info("Opening Test Cases page");
        driver.get("https://automationexercise.com/test_cases");
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("/test_cases"), "Expected /test_cases but got: " + actualUrl);
        pass("Test Case URL verified → " + actualUrl);
    }
    
    @Test(priority = 17)
    public void TestCases_VerifyUI() {
        info("Opening Test Cases page");
        driver.get("https://automationexercise.com/test_cases");
        Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(normalize-space(),'Test Cases')]")).isDisplayed(), "Test Cases heading");
        pass("Test Cases UI verified");
    }

    // 6) API TESTING 
    
    @Test(priority = 18)
    public void APITesting_VerifyUrl() {
        info("Opening API Testing page");
        driver.get("https://automationexercise.com/api_list");
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("/api_list"), "Expected /api_list but got: " + actualUrl);
        pass("API Testing URL verified → " + actualUrl);
    }
    
    @Test(priority = 19)
    public void APITesting_VerifyUI() {
        info("Opening API Testing page");
        driver.get("https://automationexercise.com/api_list");
        Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(normalize-space(),'APIs List')]")).isDisplayed(), "APIs List heading");
        Assert.assertTrue(driver.findElement(By.xpath("(//div[contains(@class,'panel')])[1]")).isDisplayed(), "At least one API panel");
        pass("API Testing UI verified");
    }

    //  7) VIDEO TUTORIAL 
    @Test(priority = 20)

    public void VideoTutorial_VerifyUrl() {
        info("Opening Contact Us page");
        driver.get("https://www.youtube.com/c/AutomationExercise");
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("/AutomationExercise"), "Expected /AutomationExercise but got: " + actualUrl);
        pass("Video Tutorial URL verified → " + actualUrl);
    }

    //  8) CONTACT US 
    @Test(priority = 21)
    public void ContactUs_VerifyUrl() {
        info("Opening Contact Us page");
        driver.get("https://automationexercise.com/contact_us");
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("/contact_us"), "Expected /contact_us but got: " + actualUrl);
        pass("Contact Us URL verified → " + actualUrl);
    }
}
