package com.project.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.project.base.Basetest;
import com.project.pages.Homepage;
import com.project.pages.productpage;

public class productpageTest extends Basetest {
    private productpage productPage;
    private Homepage homepage;

    @BeforeMethod
    public void setUpPage() {
        homepage = new Homepage(driver);
        homepage.clickProducts();
        productPage = new productpage(driver);
    }

    // ---------- Product Display ----------
    @Test
    public void verifyProductsDisplayed() {
        Assert.assertTrue(productPage.areProductsDisplayed(), "Products are not displayed");
    }

    // ---------- Search ----------
    @Test
    public void verifyValidProductSearch() {
        Assert.assertTrue(productPage.searchProduct("Dress"), "Valid product not found in search");
    }

    @Test
    public void verifyInvalidProductSearch() {
        Assert.assertFalse(productPage.searchProduct("xyz123"), "Invalid product should not be found");
    }

    // ---------- Add to Cart ----------
    @Test
    public void verifyAddToCart() {
        Assert.assertTrue(productPage.addFirstProductToCart(), "Add to cart modal not displayed");
    }

    @Test
    public void verifyContinueShoppingButton() {
        productPage.addFirstProductToCart();
        Assert.assertTrue(productPage.clickContinueShopping(), "Continue shopping failed");
    }

    @Test
    public void verifyViewCartButton() {
        productPage.addFirstProductToCart();
        Assert.assertTrue(productPage.clickViewCart(), "View cart button failed");
    }

    // ---------- View Product ----------
    @Test
    public void verifyViewFirstProduct() {
        Assert.assertTrue(productPage.viewFirstProduct(), "View product navigation failed");
    }

    // ---------- Categories ----------
    @Test
    public void verifyWomenCategory() {
        Assert.assertTrue(productPage.clickWomenCategory(), "Women category failed");
    }

    @Test
    public void verifyMenCategory() {
        Assert.assertTrue(productPage.clickMenCategory(), "Men category failed");
    }

    @Test
    public void verifyKidsCategory() {
        Assert.assertTrue(productPage.clickKidsCategory(), "Kids category failed");
    }
}
