package com.project.tests;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;

import com.project.base.Basetest;
import com.project.pages.CartPage;
import com.project.utilities.ScreenshotUtilities;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.lang.reflect.Method;

public class CartPageTest extends Basetest {

    private static final String REPORT_DIR = "src/test/resources/Reports";  // Updated report folder path
    private static ExtentReports extent;  // Use the same ExtentReports instance as in Basetest
    private static ExtentTest test;       // Use the same ExtentTest instance from Basetest

    private static final String EMAIL = "vivekpatil280803@gmail.com";
    private static final String PASSWORD = "abcd@123";
    private static final String PRODUCT1_ID = "1";
    private static final String PRODUCT1_NAME = "Blue Top";
    
    private static final String NAME = "John Doe";          // Name on the card
    private static final String CARD = "1234567812345678";  // Card number
    private static final String CVC = "123";                // Card CVC
    private static final String MM = "12";                  // Card expiry month
    private static final String YYYY = "25";                // Card expiry year

    @BeforeClass(alwaysRun = true)
    public void startExtent() {
        // Initialize ExtentReports only once and attach the reporter
        String reportPath = REPORT_DIR + "/CartPageTest.html";  // Updated report path

        // Ensure the report folder exists
        new File(REPORT_DIR).mkdirs();

        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setDocumentTitle("Automation Exercise");
            spark.config().setReportName("Cart Page Flow");
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Suite", "CartPage");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java", System.getProperty("java.version"));
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void createExtentTest(Method m) {
        // Create the ExtentTest instance for each test method
        test = extent.createTest(m.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void updateExtent(ITestResult result) {
        if (test == null) return;
        
        // Add the result of each test to the Extent report
        if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test passed");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test skipped: " + (result.getThrowable() == null ? "" : result.getThrowable().toString()));
        } else if (result.getStatus() == ITestResult.FAILURE) {
            test.fail("Test failed: " + result.getThrowable());
            snap("FAIL_" + result.getMethod().getMethodName());  // Capture a screenshot if test fails
        }
    }

    @AfterClass(alwaysRun = true)
    public void flushExtent() {
        if (extent != null) {
            extent.flush();  // Write the report to disk at the end of the test suite
        }
    }

    private ExtentTest log() {
        return test;
    }

    private void snap(String label) {
        try {
            String safe = label.replaceAll("[^a-zA-Z0-9._-]", "_");
            // Save screenshots in the desired folder (inside 'Reports/Screenshots/')
            String path = ScreenshotUtilities.Capture(driver, REPORT_DIR + "/Screenshots/" + safe);
            if (log() != null && path != null) log().addScreenCaptureFromPath(path);
        } catch (Exception ignored) {}
    }

    private CartPage startOnLogin() {
        CartPage cart = new CartPage(driver);
        driver.get("https://automationexercise.com/login");
        Reporter.log("Opened login page", true);
        if (log() != null) log().info("Opened login page");
        snap("01_login_page");
        return cart;
    }

    private CartPage login(CartPage cart) {
        cart.login(EMAIL, PASSWORD);
        Reporter.log("Logged in as: " + EMAIL, true);
        if (log() != null) log().info("Logged in as: " + EMAIL);
        snap("02_logged_in");
        return cart;
    }

    private CartPage goToProducts(CartPage cart) {
        driver.get("https://automationexercise.com/products");
        Reporter.log("Opened Products page", true);
        if (log() != null) log().info("Opened Products page");
        snap("03_products_page");
        return cart;
    }

    private CartPage addFirstProductAndOpenCart(CartPage cart) {
        cart.clickAddToCart(PRODUCT1_ID);
        if (log() != null) log().info("Clicked Add to Cart: id=" + PRODUCT1_ID);
        snap("04_add_to_cart");

        cart.clickViewCartInPopup();
        if (log() != null) log().info("Opened Cart page from popup");
        snap("05_view_cart");
        return cart;
    }

    private CartPage goToCheckout(CartPage cart) {
        cart.proceedToCheckout();
        if (log() != null) log().info("Clicked Proceed to Checkout");
        snap("06_proceed_to_checkout");
        return cart;
    }

    private CartPage goToPayment(CartPage cart) {
        cart.clickPlaceOrder();
        if (log() != null) log().info("Clicked Place Order");
        snap("07_place_order_payment_page");
        return cart;
    }

    @Test(priority = 1)
    public void t01_login() {
        CartPage cart = startOnLogin();
        login(cart);
        Assert.assertTrue(cart.isUserLoggedIn(), "Not logged in (Logout link not visible).");
    }

    @Test(priority = 2)
    public void t02_addProduct_and_viewCart() {
        CartPage cart = startOnLogin();
        login(cart);
        goToProducts(cart);
        addFirstProductAndOpenCart(cart);

        Assert.assertTrue(cart.isProductInCart(PRODUCT1_NAME), "Product not present in Cart: " + PRODUCT1_NAME);
        if (log() != null) log().pass("Verified product in cart: " + PRODUCT1_NAME);
    }

    @Test(priority = 3)
    public void t03_checkout_visible_from_cart() {
        CartPage cart = startOnLogin();
        login(cart);
        goToProducts(cart);
        addFirstProductAndOpenCart(cart);
        goToCheckout(cart);

        Assert.assertTrue(cart.isCheckoutPageDisplayed(), "Checkout page is not visible.");
        if (log() != null) log().pass("Checkout page is visible");
    }

    @Test(priority = 4)
    public void t04_place_order_opens_payment_page() {
        CartPage cart = startOnLogin();
        login(cart);
        goToProducts(cart);
        addFirstProductAndOpenCart(cart);
        goToCheckout(cart);
        Assert.assertTrue(cart.isCheckoutPageDisplayed(), "Checkout page is not visible.");
        goToPayment(cart);

        Assert.assertTrue(cart.isPaymentFormDisplayed(), "Payment form not visible.");
        if (log() != null) log().pass("Payment page is visible");
    }

    @Test(priority = 5)
    public void t05_complete_payment_and_confirm() {
        CartPage cart = startOnLogin();
        login(cart);
        goToProducts(cart);
        addFirstProductAndOpenCart(cart);
        goToCheckout(cart);
        Assert.assertTrue(cart.isCheckoutPageDisplayed(), "Checkout page is not visible.");
        goToPayment(cart);
        Assert.assertTrue(cart.isPaymentFormDisplayed(), "Payment form not visible.");

        cart.paymentFill(NAME, CARD, CVC, MM, YYYY);
        snap("08_payment_filled");

        cart.paymentClickPay().waitForOrderPlaced();
        snap("09_order_placed");

        Assert.assertTrue(cart.isOrderPlacedVisible(), "Order placed banner not visible.");
        cart.paymentClickDownloadInvoice();
        snap("10_invoice_clicked");

        cart.paymentClickContinue();
        snap("11_continue_to_home");

        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"),
                "Should navigate back to Home after Continue");
        if (log() != null) log().pass("Invoice attempted and continued to Home");
    }
}
