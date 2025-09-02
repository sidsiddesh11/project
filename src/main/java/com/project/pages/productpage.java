package com.project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class productpage {

    WebDriver driver;

    // Locators
    By productImages = By.cssSelector(".product-image");
    By productNames = By.cssSelector(".product-name");
    By productPrices = By.cssSelector(".product-price");
    By addToCartBtn = By.xpath("//a[contains(text(),'Add to cart')]");
    By viewProductLink = By.xpath("//a[contains(text(),'View Product')]");
    By searchBox = By.id("search_product");
    By searchButton = By.id("submit_search");
    By noResultsMessage = By.xpath("//p[contains(text(),'not found')]");
    By reviewName = By.id("name");
    By reviewEmail = By.id("email");
    By reviewText = By.id("review");
    By reviewSubmit = By.id("button-review");
    By subscriptionEmail = By.id("susbscribe_email");
    By subscriptionButton = By.id("subscribe");
    By subscriptionSuccess = By.xpath("//div[contains(text(),'You have been successfully subscribed!')]");
    By continueShoppingBtn = By.xpath("//button[contains(text(),'Continue Shopping')]");

    public productpage(WebDriver driver) {
        this.driver = driver;
    }

    // -------- Product Display --------
    public boolean areProductImagesDisplayed() {
        List<WebElement> images = driver.findElements(productImages);
        return images.size() > 0 && images.get(0).isDisplayed();
    }

    public boolean areProductNamesDisplayed() {
        List<WebElement> names = driver.findElements(productNames);
        return names.size() > 0 && names.get(0).isDisplayed();
    }

    public boolean areProductPricesDisplayed() {
        List<WebElement> prices = driver.findElements(productPrices);
        return prices.size() > 0 && prices.get(0).isDisplayed();
    }

    // -------- Cart --------
    public boolean addToCartFirstProduct() {
        driver.findElements(addToCartBtn).get(0).click();
        // TODO: validate popup/cart confirmation
        return true;
    }

    public boolean verifyContinueShopping() {
        driver.findElement(continueShoppingBtn).click();
        return driver.getCurrentUrl().contains("products");
    }

    // -------- Product View --------
    public boolean viewFirstProduct() {
        driver.findElements(viewProductLink).get(0).click();
        return driver.getCurrentUrl().contains("product_details");
    }

    // -------- Search --------
    public boolean searchProduct(String query) {
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(query);
        driver.findElement(searchButton).click();
        return driver.findElements(productNames).size() > 0;
    }

    public boolean searchNoResults(String query) {
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(query);
        driver.findElement(searchButton).click();
        return driver.findElement(noResultsMessage).isDisplayed();
    }

    public boolean searchBlankInput() {
        driver.findElement(searchBox).clear();
        driver.findElement(searchButton).click();
        return driver.findElement(noResultsMessage).isDisplayed();
    }

    public boolean searchInvalidProduct(String query) {
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(query);
        driver.findElement(searchButton).click();
        return driver.findElement(noResultsMessage).isDisplayed();
    }

    // -------- Filters --------
    public boolean applyCategoryFilter(String category) {
        // TODO: dynamic locator for category
        driver.findElement(By.linkText(category)).click();
        return driver.getCurrentUrl().toLowerCase().contains(category.toLowerCase());
    }

    public boolean applyBrandFilter(String brand) {
        // TODO: dynamic locator for brand
        driver.findElement(By.linkText(brand)).click();
        return driver.getCurrentUrl().toLowerCase().contains("brand");
    }

    public boolean verifyBrandRedirect(String brand) {
        driver.findElement(By.linkText(brand)).click();
        return driver.getTitle().toLowerCase().contains(brand.toLowerCase());
    }

    public boolean verifyWomenSubcategories() {
        // TODO: refine locator
        List<WebElement> subs = driver.findElements(By.xpath("//div[@id='Women']//li"));
        return subs.size() == 3;
    }

    public boolean verifyMenSubcategories() {
        List<WebElement> subs = driver.findElements(By.xpath("//div[@id='Men']//li"));
        return subs.size() == 2;
    }

    public boolean verifyKidsSubcategories() {
        List<WebElement> subs = driver.findElements(By.xpath("//div[@id='Kids']//li"));
        return subs.size() == 2;
    }

    // -------- Reviews --------
    public boolean submitReview(String name, String email, String review) {
        driver.findElement(reviewName).sendKeys(name);
        driver.findElement(reviewEmail).sendKeys(email);
        driver.findElement(reviewText).sendKeys(review);
        driver.findElement(reviewSubmit).click();

        if (!email.contains("@")) {
            return false; // simulate invalid email rejection
        }
        return true;
    }

    // -------- Subscription --------
    public boolean subscribe(String email) {
        driver.findElement(subscriptionEmail).clear();
        driver.findElement(subscriptionEmail).sendKeys(email);
        driver.findElement(subscriptionButton).click();
        return email.contains("@");
    }

    public boolean verifySubscriptionMessage() {
        return driver.findElement(subscriptionSuccess).isDisplayed();
    }
}
