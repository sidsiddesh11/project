package com.project.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.project.base.Basetest;
import com.project.pages.productpage;
import com.project.utilities.ScreenshotUtilities;

import io.github.bonigarcia.wdm.WebDriverManager;

import com.aventstack.extentreports.Status;

public class productpageTest extends Basetest {
    private productpage productPage;

    @BeforeMethod
    public void setUpPage() {
    	WebDriverManager.chromedriver().setup();

        driver.get("https://automationexercise.com/products");   
        productPage = new productpage(driver);
    }

    @Test
    public void verifyProductsDisplayed() throws IOException {
        test = extent.createTest("TC_ECOM_Products_01 - Verify Products Displayed");
        boolean result = productPage.areProductsDisplayed();
        if (result) {
            test.log(Status.INFO, "Checking if products are displayed");
            Assert.assertTrue(result, "Products are not displayed");
            test.log(Status.PASS, "Products displayed successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ProductsDisplayed"));
        } else {
            test.log(Status.FAIL, "Products not displayed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ProductsNotDisplayed"));
            Assert.fail("Products are not displayed");
        }
    }

    @Test
    public void verifyValidProductSearch() throws IOException {
        test = extent.createTest("TC_ECOM_Products_02 - Verify Valid Product Search");
        boolean result = productPage.searchProduct("Dress");
        if (result) {
            test.log(Status.INFO, "Searching for valid product: Dress");
            Assert.assertTrue(result, "Valid product not found in search");
            test.log(Status.PASS, "Valid product search successful")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ValidSearch"));
        } else {
            test.log(Status.FAIL, "Valid product search failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ValidSearchFail"));
            Assert.fail("Valid product not found in search");
        }
    }

    @Test
    public void verifyInvalidProductSearch() throws IOException {
        test = extent.createTest("TC_ECOM_Products_03 - Verify Invalid Product Search");
        boolean result = productPage.searchProduct("xyz123");
        if (!result) {
            test.log(Status.INFO, "Searching for invalid product: xyz123");
            Assert.assertFalse(result, "Invalid product should not be found");
            test.log(Status.PASS, "Invalid product search verified successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "InvalidSearch"));
        } else {
            test.log(Status.FAIL, "Invalid product search failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "InvalidSearchFail"));
            Assert.fail("Invalid product should not be found but it was displayed");
        }
    }

    @Test
    public void verifyAddToCart() throws IOException {
        test = extent.createTest("TC_ECOM_Products_04 - Verify Add to Cart");
        boolean result = productPage.addFirstProductToCart();
        if (result) {
            test.log(Status.INFO, "Adding first product to cart");
            Assert.assertTrue(result, "Add to cart modal not displayed");
            test.log(Status.PASS, "Product added to cart successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "AddToCart"));
        } else {
            test.log(Status.FAIL, "Add to cart failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "AddToCartFail"));
            Assert.fail("Add to cart modal not displayed");
        }
    }

    @Test
    public void verifyContinueShoppingButton() throws IOException {
        test = extent.createTest("TC_ECOM_Products_05 - Verify Continue Shopping Button");
        productPage.addFirstProductToCart();
        boolean result = productPage.clickContinueShopping();
        if (result) {
            test.log(Status.INFO, "Clicking Continue Shopping button");
            Assert.assertTrue(result, "Continue shopping failed");
            test.log(Status.PASS, "Continue shopping successful")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ContinueShopping"));
        } else {
            test.log(Status.FAIL, "Continue shopping button failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ContinueShoppingFail"));
            Assert.fail("Continue shopping button not working");
        }
    }

    @Test
    public void verifyViewCartButton() throws IOException {
        test = extent.createTest("TC_ECOM_Products_06 - Verify View Cart Button");
        productPage.addFirstProductToCart();
        boolean result = productPage.clickViewCart();
        if (result) {
            test.log(Status.INFO, "Clicking View Cart button");
            Assert.assertTrue(result, "View cart button failed");
            test.log(Status.PASS, "View Cart button clicked successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ViewCart"));
        } else {
            test.log(Status.FAIL, "View Cart button failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ViewCartFail"));
            Assert.fail("View Cart button not working");
        }
    }

    @Test
    public void verifyViewFirstProduct() throws IOException {
        test = extent.createTest("TC_ECOM_Products_07 - Verify View First Product");
        boolean result = productPage.viewFirstProduct();
        if (result) {
            test.log(Status.INFO, "Clicking first product to view details");
            Assert.assertTrue(result, "View product navigation failed");
            test.log(Status.PASS, "First product viewed successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ViewProduct"));
        } else {
            test.log(Status.FAIL, "View first product failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "ViewProductFail"));
            Assert.fail("View first product failed");
        }
    }

    @Test
    public void verifyWomenCategory() throws IOException {
        test = extent.createTest("TC_ECOM_Products_08 - Verify Women Category");
        boolean result = productPage.clickWomenCategory();
        if (result) {
            test.log(Status.INFO, "Clicking Women category");
            Assert.assertTrue(result, "Women category failed");
            test.log(Status.PASS, "Women category clicked successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "WomenCategory"));
        } else {
            test.log(Status.FAIL, "Women category failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "WomenCategoryFail"));
            Assert.fail("Women category failed");
        }
    }

    @Test
    public void verifyMenCategory() throws IOException {
        test = extent.createTest("TC_ECOM_Products_09 - Verify Men Category");
        boolean result = productPage.clickMenCategory();
        if (result) {
            test.log(Status.INFO, "Clicking Men category");
            Assert.assertTrue(result, "Men category failed");
            test.log(Status.PASS, "Men category clicked successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "MenCategory"));
        } else {
            test.log(Status.FAIL, "Men category failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "MenCategoryFail"));
            Assert.fail("Men category failed");
        }
    }

    @Test
    public void verifyKidsCategory() throws IOException {
        test = extent.createTest("TC_ECOM_Products_10 - Verify Kids Category");
        boolean result = productPage.clickKidsCategory();
        if (result) {
            test.log(Status.INFO, "Clicking Kids category");
            Assert.assertTrue(result, "Kids category failed");
            test.log(Status.PASS, "Kids category clicked successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "KidsCategory"));
        } else {
            test.log(Status.FAIL, "Kids category failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "KidsCategoryFail"));
            Assert.fail("Kids category failed");
        }
    }

    // ✅ Brand Tests
    @Test
    public void verifyPoloBrand() throws IOException {
        test = extent.createTest("TC_ECOM_Products_11 - Verify Polo Brand");
        boolean result = productPage.clickBrand("Polo");
        if (result) {
            test.log(Status.INFO, "Clicking Polo brand");
            Assert.assertTrue(result, "Navigation failed for Polo brand");
            test.log(Status.PASS, "Polo brand page opened successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "Brand_Polo"));
        } else {
            test.log(Status.FAIL, "Polo brand navigation failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "Brand_Polo_Fail"));
            Assert.fail("Polo brand navigation failed");
        }
    }

    @Test
    public void verifyHMBrand() throws IOException {
        test = extent.createTest("TC_ECOM_Products_12 - Verify H&M Brand");
        boolean result = productPage.clickBrand("H&M");
        if (result) {
            test.log(Status.INFO, "Clicking H&M brand");
            Assert.assertTrue(result, "Navigation failed for H&M brand");
            test.log(Status.PASS, "H&M brand page opened successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "Brand_HM"));
        } else {
            test.log(Status.FAIL, "H&M brand navigation failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "Brand_HM_Fail"));
            Assert.fail("H&M brand navigation failed");
        }
    }

    @Test
    public void verifyMadameBrand() throws IOException {
        test = extent.createTest("TC_ECOM_Products_13 - Verify Madame Brand");
        boolean result = productPage.clickBrand("Madame");
        if (result) {
            test.log(Status.INFO, "Clicking Madame brand");
            Assert.assertTrue(result, "Navigation failed for Madame brand");
            test.log(Status.PASS, "Madame brand page opened successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "Brand_Madame"));
        } else {
            test.log(Status.FAIL, "Madame brand navigation failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "Brand_Madame_Fail"));
            Assert.fail("Madame brand navigation failed");
        }
    }

    @Test
    public void verifyBabyhugBrand() throws IOException {
        test = extent.createTest("TC_ECOM_Products_14 - Verify Babyhug Brand");
        boolean result = productPage.clickBrand("Babyhug");
        if (result) {
            test.log(Status.INFO, "Clicking Babyhug brand");
            Assert.assertTrue(result, "Navigation failed for Babyhug brand");
            test.log(Status.PASS, "Babyhug brand page opened successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "Brand_Babyhug"));
        } else {
            test.log(Status.FAIL, "Babyhug brand navigation failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "Brand_Babyhug_Fail"));
            Assert.fail("Babyhug brand navigation failed");
        }
    }

    @Test
    public void verifyAllenSollyBrand() throws IOException {
        test = extent.createTest("TC_ECOM_Products_15 - Verify Allen Solly Brand");
        boolean result = productPage.clickBrand("Allen Solly");
        if (result) {
            test.log(Status.INFO, "Clicking Allen Solly brand");
            Assert.assertTrue(result, "Navigation failed for Allen Solly brand");
            test.log(Status.PASS, "Allen Solly brand page opened successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "Brand_AllenSolly"));
        } else {
            test.log(Status.FAIL, "Allen Solly brand navigation failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "Brand_AllenSolly_Fail"));
            Assert.fail("Allen Solly brand navigation failed");
        }
    }

    @Test
    public void verifyKookieKidsBrand() throws IOException {
        test = extent.createTest("TC_ECOM_Products_16 - Verify Kookie Kids Brand");
        boolean result = productPage.clickBrand("Kookie Kids");
        if (result) {
            test.log(Status.INFO, "Clicking Kookie Kids brand");
            Assert.assertTrue(result, "Navigation failed for Kookie Kids brand");
            test.log(Status.PASS, "Kookie Kids brand page opened successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "Brand_KookieKids"));
        } else {
            test.log(Status.FAIL, "Kookie Kids brand navigation failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "Brand_KookieKids_Fail"));
            Assert.fail("Kookie Kids brand navigation failed");
        }
    }

    @Test
    public void verifyBibaBrand() throws IOException {
        test = extent.createTest("TC_ECOM_Products_17 - Verify Biba Brand");
        boolean result = productPage.clickBrand("Biba");
        if (result) {
            test.log(Status.INFO, "Clicking Biba brand");
            Assert.assertTrue(result, "Navigation failed for Biba brand");
            test.log(Status.PASS, "Biba brand page opened successfully")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "Brand_Biba"));
        } else {
            test.log(Status.FAIL, "Biba brand navigation failed")
                .addScreenCaptureFromPath(ScreenshotUtilities.Capture(driver, "Brand_Biba_Fail"));
            Assert.fail("Biba brand navigation failed");
        }
    }
}
