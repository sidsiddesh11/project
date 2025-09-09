package com.project.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class productpage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By productGrid = By.cssSelector(".features_items .product-image-wrapper");
    private By searchBox = By.id("search_product");
    private By searchButton = By.id("submit_search");
    private By addToCartBtn = By.cssSelector(".add-to-cart");
    private By viewProductBtn = By.xpath("(//a[contains(text(),'View Product')])[1]");
    private By cartSuccessModal = By.id("cartModal");
    private By continueShoppingBtn = By.cssSelector(".btn-success.close-modal.btn-block");
    private By viewCartBtn = By.xpath("//u[text()='View Cart']");
    private By womenCategory = By.xpath("//div[@class='panel-heading']//a[contains(text(),'Women')]");
    private By menCategory   = By.xpath("//*[@id=\"accordian\"]/div[2]/div[1]/h4/a");
    private By kidsCategory  = By.xpath("//*[@id=\"accordian\"]/div[3]/div[1]/h4/a");

    public productpage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(productGrid));
    }

    // Scroll helper
    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // ✅ Verify product grid
    public boolean areProductsDisplayed() {
        List<WebElement> products = driver.findElements(productGrid);
        return products.size() > 0;
    }

    // ✅ Search product
    public boolean searchProduct(String productName) {
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(productName);
        driver.findElement(searchButton).click();

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".features_items .product-image-wrapper")));
            return true; // products found
        } catch (TimeoutException e) {
            return driver.findElement(By.xpath("//p[contains(text(),'not found')]")).isDisplayed();
        }
    }
 // ✅ Click a brand by name
    public boolean clickBrand(String brandName) {
        List<WebElement> brands = driver.findElements(By.cssSelector(".brands_products .nav-stacked li a"));
        for (WebElement brand : brands) {
            if (brand.getText().trim().toLowerCase().contains(brandName.toLowerCase())) {
                scrollToElement(brand);
                brand.click();
                return driver.getCurrentUrl().contains("brand_products");
            }
        }
        return false;
    }


    // ✅ Add first product to cart
    public boolean addFirstProductToCart() {
        WebElement firstProduct = driver.findElements(addToCartBtn).get(0);
        scrollToElement(firstProduct);
        firstProduct.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartSuccessModal));
        return driver.findElement(cartSuccessModal).isDisplayed();
    }

    public boolean clickContinueShopping() {
        driver.findElement(continueShoppingBtn).click();
        return true;
    }

    public boolean clickViewCart() {
        driver.findElement(viewCartBtn).click();
        return driver.getCurrentUrl().contains("view_cart");
    }

    // ✅ View Product
    public boolean viewFirstProduct() {
        WebElement viewBtn = driver.findElement(viewProductBtn);
        scrollToElement(viewBtn);
        viewBtn.click();
        return driver.getCurrentUrl().contains("product_details");
    }

    // ✅ Categories
    public boolean clickWomenCategory() {
        WebElement women = driver.findElement(womenCategory);
        scrollToElement(women);
        women.click();
        return driver.getCurrentUrl().contains("category_products");
    }

    public boolean clickMenCategory() {
        WebElement men = driver.findElement(menCategory);
        scrollToElement(men);
        men.click();
        return driver.getCurrentUrl().contains("category_products");
    }

    public boolean clickKidsCategory() {
        WebElement kids = driver.findElement(kidsCategory);
        scrollToElement(kids);
        kids.click();
        return driver.getCurrentUrl().contains("category_products");
    }
}
