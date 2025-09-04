package com.project.tests;

import com.project.base.Basetest;
import com.project.pages.CartPage;
import com.project.utilities.ScreenshotUtilities;
import com.project.utilities.ExtentManager;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.lang.reflect.Method;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartPageTest extends Basetest {

    private static boolean cartReporterAttached = false;

    @BeforeMethod(alwaysRun = true)
    public void startExtent(Method method) {
        if (extent == null) {
            extent = ExtentManager.getinstance();
        }
        if (!cartReporterAttached) {
            String reportPath = Paths.get(
                    System.getProperty("user.dir"),
                    "src", "test", "resources", "Reports", "Report_CartPage.html"
            ).toString();
            ExtentSparkReporter cartSpark = new ExtentSparkReporter(reportPath);
            cartSpark.config().setReportName("Report_CartPage");
            cartSpark.config().setDocumentTitle("Report_CartPage");
            cartSpark.config().setTheme(Theme.STANDARD);
            extent.attachReporter(cartSpark);
            cartReporterAttached = true;
        }
        test = extent.createTest(method.getName()).assignCategory("Cart");
    }

    @Test(priority = 1)
    public void verifyEmptyCartHasHereLink() throws Exception {
        CartPage cart = new CartPage(driver);
        test.info("Navigate to home, then cart page");
        cart.openHome().openCartDirect();

        boolean emptyVisible = cart.isEmptyCartMessageVisible();
        boolean hereVisible  = cart.isHereLinkVisibleOnEmptyCart();

        String shot = ScreenshotUtilities.Capture(driver, "P1_EmptyCart_UI");
        test.info("Empty cart UI", MediaEntityBuilder.createScreenCaptureFromPath(shot).build());

        Assert.assertTrue(emptyVisible, "Expected 'Cart is empty!' message to be visible.");
        Assert.assertTrue(hereVisible,  "Expected 'here' link to be visible on empty cart.");
    }

    @Test(priority = 2)
    public void verifyHereLinkRedirectsToProducts() throws Exception {
        CartPage cart = new CartPage(driver);
        test.info("Navigate to home, then cart page");
        cart.openHome().openCartDirect();

        test.info("Click 'here' to navigate to Products");
        cart.clickHereLinkToProducts();

        String url = driver.getCurrentUrl();
        String shot = ScreenshotUtilities.Capture(driver, "P2_HereLink_Products");
        test.info("After clicking 'here'", MediaEntityBuilder.createScreenCaptureFromPath(shot).build());

        Assert.assertTrue(url.contains("/products"),
                "Expected current URL to contain '/products' after clicking 'here'.");
    }

    @Test(priority = 3)
    public void addShirtFromProducts_thenVerifyInCart() throws Exception {
        CartPage cart = new CartPage(driver);
        test.info("Navigate to home, then cart page");
        cart.openHome().openCartDirect();

        test.info("Click 'here' to go to Products");
        cart.clickHereLinkToProducts();

        test.info("Search 'Shirt' and add first result to cart");
        cart.searchProduct("Shirt").addFirstResultToCart();

        test.info("Go to cart from modal");
        cart.goToCartFromModal();

        boolean hasShirt = cart.isProductWithNamePresentInCart("Shirt");
        int lineCount    = cart.getCartLineItemCount();

        String shot = ScreenshotUtilities.Capture(driver, "P3_Cart_After_Adding_Shirt");
        test.info("Cart after adding 'Shirt'", MediaEntityBuilder.createScreenCaptureFromPath(shot).build());

        Assert.assertTrue(lineCount >= 1, "Expected at least one line item in cart.");
        Assert.assertTrue(hasShirt, "Expected cart to contain an item with name including 'Shirt'.");
    }

    @Test(priority = 4)
    public void proceedToCheckout_handlesAuth_thenLandsOnCheckout() throws Exception {
        String email    = "akashmangond6656@gmail.com";
        String password = "NrXqTP9VxF@Yt";

        CartPage cart = new CartPage(driver);

        test.info("Home -> Cart -> 'here' -> Products");
        cart.openHome()
            .openCartDirect()
            .clickHereLinkToProducts();

        test.info("Search 'Shirt' and add first result");
        cart.searchProduct("Shirt").addFirstResultToCart();

        test.info("Go to cart from modal");
        cart.goToCartFromModal();

        int lineCount = cart.getCartLineItemCount();
        Assert.assertTrue(lineCount >= 1, "Precondition failed: cart should contain at least one item.");

        test.info("Click 'Proceed To Checkout'");
        cart.clickProceedToCheckout();

        if (cart.isOnAuthGate()) {
            test.info("Auth gate detected — logging in");
            cart.loginFromAuthGate(email, password);
        }

        // Ensure we end up on checkout, even if login redirected elsewhere
        boolean onCheckout = cart.ensureOnCheckoutViaCart();

        // Log URL and title for diagnosis if it ever fails again
        String url = driver.getCurrentUrl();
        String title = driver.getTitle();
        test.info("URL: " + url);
        test.info("Title: " + title);

        String shot = ScreenshotUtilities.Capture(driver, "P4_ProceedToCheckout_Final");
        test.info("After proceeding to checkout",
                MediaEntityBuilder.createScreenCaptureFromPath(shot).build());

        Assert.assertTrue(onCheckout,
            "Expected to land on the checkout page after handling auth and clicking 'Proceed To Checkout'.");
    }
}
