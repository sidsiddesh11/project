package com.project.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.project.base.Basetest;
import com.project.pages.Homepage;
import com.project.pages.productpage;
import com.project.utilities.ScreenshotUtilities;   // ✅ added
import com.aventstack.extentreports.Status;        // ✅ added

public class productpageTest extends Basetest {
    private productpage productPage;
    private Homepage homepage;

    @BeforeMethod
    public void setUpPage() {
        homepage = new Homepage(driver);
        homepage.clickProducts();
        productPage = new productpage(driver);
    }

    @Test
    public void verifyProductsDisplayed() throws IOException {
        test = extent.createTest("TC_ECOM_Products_01 - Verify Products Displayed");
        try {
            Assert.assertTrue(productPage.areProductsDisplayed(), "Products are not displayed");
            test.log(Status.PASS, "Products displayed successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ProductsDisplayed"));
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Products not displayed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ProductsNotDisplayed"));
            throw e;
        }
    }

    @Test
    public void verifyValidProductSearch() throws IOException {
        test = extent.createTest("TC_ECOM_Products_02 - Verify Valid Product Search");
        try {
            Assert.assertTrue(productPage.searchProduct("Dress"), "Valid product not found in search");
            test.log(Status.PASS, "Valid product search successful")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ValidSearch"));
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Valid product search failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ValidSearchFail"));
            throw e;
        }
    }

    @Test
    public void verifyInvalidProductSearch() throws IOException {
        test = extent.createTest("TC_ECOM_Products_03 - Verify Invalid Product Search");
        try {
            Assert.assertFalse(productPage.searchProduct("xyz123"), "Invalid product should not be found");
            test.log(Status.PASS, "Invalid product search verified successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "InvalidSearch"));
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Invalid product search failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "InvalidSearchFail"));
            throw e;
        }
    }

    @Test
    public void verifyAddToCart() throws IOException {
        test = extent.createTest("TC_ECOM_Products_04 - Verify Add to Cart");
        try {
            Assert.assertTrue(productPage.addFirstProductToCart(), "Add to cart modal not displayed");
            test.log(Status.PASS, "Product added to cart successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "AddToCart"));
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Add to cart failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "AddToCartFail"));
            throw e;
        }
    }

    @Test
    public void verifyContinueShoppingButton() throws IOException {
        test = extent.createTest("TC_ECOM_Products_05 - Verify Continue Shopping Button");
        productPage.addFirstProductToCart();
        try {
            Assert.assertTrue(productPage.clickContinueShopping(), "Continue shopping failed");
            test.log(Status.PASS, "Continue shopping successful")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ContinueShopping"));
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Continue shopping button failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ContinueShoppingFail"));
            throw e;
        }
    }

    @Test
    public void verifyViewCartButton() throws IOException {
        test = extent.createTest("TC_ECOM_Products_06 - Verify View Cart Button");
        productPage.addFirstProductToCart();
        try {
            Assert.assertTrue(productPage.clickViewCart(), "View cart button failed");
            test.log(Status.PASS, "View Cart button clicked successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ViewCart"));
        } catch (AssertionError e) {
            test.log(Status.FAIL, "View Cart button failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ViewCartFail"));
            throw e;
        }
    }

    @Test
    public void verifyViewFirstProduct() throws IOException {
        test = extent.createTest("TC_ECOM_Products_07 - Verify View First Product");
        try {
            Assert.assertTrue(productPage.viewFirstProduct(), "View product navigation failed");
            test.log(Status.PASS, "First product viewed successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ViewProduct"));
        } catch (AssertionError e) {
            test.log(Status.FAIL, "View first product failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ViewProductFail"));
            throw e;
        }
    }

    @Test
    public void verifyWomenCategory() throws IOException {
        test = extent.createTest("TC_ECOM_Products_08 - Verify Women Category");
        try {
            Assert.assertTrue(productPage.clickWomenCategory(), "Women category failed");
            test.log(Status.PASS, "Women category clicked successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "WomenCategory"));
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Women category failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "WomenCategoryFail"));
            throw e;
        }
    }

    @Test
    public void verifyMenCategory() throws IOException {
        test = extent.createTest("TC_ECOM_Products_09 - Verify Men Category");
        try {
            Assert.assertTrue(productPage.clickMenCategory(), "Men category failed");
            test.log(Status.PASS, "Men category clicked successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "MenCategory"));
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Men category failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "MenCategoryFail"));
            throw e;
        }
    }

    @Test
    public void verifyKidsCategory() throws IOException {
        test = extent.createTest("TC_ECOM_Products_10 - Verify Kids Category");
        try {
            Assert.assertTrue(productPage.clickKidsCategory(), "Kids category failed");
            test.log(Status.PASS, "Kids category clicked successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "KidsCategory"));
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kids category failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "KidsCategoryFail"));
            throw e;
        }
    }
}
