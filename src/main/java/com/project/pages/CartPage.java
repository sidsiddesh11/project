package com.project.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js     = (JavascriptExecutor) driver;
    }

    // ======== Navigation ========
    public CartPage openHome() {
        driver.get("https://automationexercise.com/");
        waitUntilDocumentReady();
        return this;
    }

    public CartPage openCartDirect() {
        driver.get("https://automationexercise.com/view_cart");
        waitUntilDocumentReady();
        return this;
    }

    public CartPage goToProducts() {
        waitUntilDocumentReady();
        dismissPossibleOverlayOrCookieSilently();

        final By productsLink = By.xpath("//a[@href='/products' or normalize-space()='Products']");
        final By topNav       = By.cssSelector("header, .navbar, #header");
        final By resultsGrid  = By.cssSelector(".features_items .product-image-wrapper");

        wait.until(ExpectedConditions.visibilityOfElementLocated(topNav));

        try {
            WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(productsLink));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", link);
            wait.ignoring(ElementClickInterceptedException.class)
                .until(ExpectedConditions.elementToBeClickable(link)).click();
        } catch (Exception first) {
            try {
                WebElement link = driver.findElement(productsLink);
                js.executeScript("arguments[0].click();", link);
            } catch (Exception second) {
                driver.navigate().to("https://automationexercise.com/products");
            }
        }

        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/products"),
                ExpectedConditions.visibilityOfElementLocated(resultsGrid)
            ));
        } catch (TimeoutException te) {
            driver.navigate().to("https://automationexercise.com/products");
        }

        waitUntilDocumentReady();
        dismissPossibleOverlayOrCookieSilently();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(resultsGrid, 0));
        return this;
    }

    // ======== Empty cart verifications ========
    public boolean isEmptyCartMessageVisible() {
        By emptyMsg = By.xpath("//*[contains(.,'Cart is empty!')]");
        return !driver.findElements(emptyMsg).isEmpty()
            && driver.findElement(emptyMsg).isDisplayed();
    }

    public boolean isHereLinkVisibleOnEmptyCart() {
        By hereLink = By.linkText("here");
        return !driver.findElements(hereLink).isEmpty()
            && driver.findElement(hereLink).isDisplayed();
    }

    public CartPage clickHereLinkToProducts() {
        By hereLink = By.linkText("here");
        wait.until(ExpectedConditions.elementToBeClickable(hereLink)).click();
        waitUntilDocumentReady();
        return this;
    }

    // ======== Search & Add-to-cart ========
    public CartPage searchProduct(String query) {
        By searchBox = By.id("search_product");
        By searchBtn = By.id("submit_search");

        waitUntilDocumentReady();
        dismissPossibleOverlayOrCookieSilently();
        hideStickyOverlays();

        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        box.clear();
        box.sendKeys(query);

        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(searchBtn));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
            try {
                btn.click();
            } catch (ElementClickInterceptedException ice) {
                js.executeScript("arguments[0].click();", btn);
            }
        } catch (Exception any) {
            // Fallback: ENTER submit
            box.sendKeys(Keys.ENTER);
        }

        By resultsGrid = By.cssSelector(".features_items .product-image-wrapper");
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(resultsGrid, 0));
        return this;
    }

    /** Adds the first product from the (search) results to the cart. */
    public CartPage addFirstResultToCart() {
        By firstCard = By.cssSelector(".features_items .product-image-wrapper");
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstCard));
        WebElement card = driver.findElements(firstCard).get(0);

        By addToCartBtnInCard = By.xpath(".//a[contains(@class,'add-to-cart') or contains(.,'Add to cart')]");
        try {
            new Actions(driver).moveToElement(card).pause(Duration.ofMillis(200)).perform();
            WebElement btn = card.findElement(addToCartBtnInCard);
            wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
        } catch (Exception e) {
            List<WebElement> allAddButtons = driver.findElements(By.xpath("//a[contains(@class,'add-to-cart')]"));
            if (allAddButtons.isEmpty()) throw e;
            js.executeScript("arguments[0].click();", allAddButtons.get(0));
        }

        By successModal = By.xpath("//*[contains(@class,'modal') and contains(.,'Added')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(successModal));
        return this;
    }

    public CartPage goToCartFromModal() {
        By viewCart = By.xpath("//a[normalize-space()='View Cart' or .//u[normalize-space()='View Cart']]");
        wait.until(ExpectedConditions.elementToBeClickable(viewCart)).click();
        waitUntilDocumentReady();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart_info")));
        return this;
    }

    // ======== Proceed to Checkout & Auth handling ========
    public CartPage clickProceedToCheckout() {
        By proceedBtn = By.xpath("//a[normalize-space()='Proceed To Checkout' or contains(@class,'check_out')]");
        dismissPossibleOverlayOrCookieSilently();
        hideStickyOverlays();
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(proceedBtn));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
        try {
            btn.click();
        } catch (ElementClickInterceptedException ice) {
            js.executeScript("arguments[0].click();", btn);
        }
        waitUntilDocumentReady();
        return this;
    }

    /** Strong wait (up to ~12s) for the real checkout page. */
    public boolean waitUntilOnCheckoutPage() {
        By checkoutHeader = By.xpath("//*[self::h1 or self::h2][contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'checkout')]");
        By addressDetails = By.xpath("//*[self::h2 or self::h3][contains(.,'Address Details')]");
        By reviewOrder    = By.xpath("//*[self::h2 or self::h3][contains(.,'Review Your Order')]");
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(12));
        try {
            shortWait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/checkout"),
                ExpectedConditions.titleContains("Checkout"),
                ExpectedConditions.visibilityOfElementLocated(checkoutHeader),
                ExpectedConditions.visibilityOfElementLocated(addressDetails),
                ExpectedConditions.visibilityOfElementLocated(reviewOrder)
            ));
            return true;
        } catch (TimeoutException te) {
            return false;
        }
    }

    public boolean isOnCheckoutPage() {
        String url = driver.getCurrentUrl();
        if (url != null && url.contains("/checkout")) return true;
        String title = driver.getTitle();
        if (title != null && title.toLowerCase().contains("checkout")) return true;
        By checkoutHeader = By.xpath("//*[self::h1 or self::h2][contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'checkout')]");
        By addressDetails = By.xpath("//*[self::h2 or self::h3][contains(.,'Address Details')]");
        By reviewOrder    = By.xpath("//*[self::h2 or self::h3][contains(.,'Review Your Order')]");
        return isVisible(checkoutHeader) || isVisible(addressDetails) || isVisible(reviewOrder);
    }

    public boolean isOnAuthGate() {
        String url = driver.getCurrentUrl();
        if (url != null && url.contains("/login")) return true;
        By emailField    = By.cssSelector("input[data-qa='login-email']");
        By passwordField = By.cssSelector("input[data-qa='login-password']");
        By loginBtn      = By.cssSelector("button[data-qa='login-button']");
        return isVisible(emailField) && isVisible(passwordField) && isVisible(loginBtn);
    }

    public CartPage loginFromAuthGate(String email, String password) {
        By emailField    = By.cssSelector("input[data-qa='login-email']");
        By passwordField = By.cssSelector("input[data-qa='login-password']");
        By loginBtn      = By.cssSelector("button[data-qa='login-button']");
        By loggedInAs    = By.xpath("//a[contains(.,'Logged in as')]");

        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).clear();
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginBtn).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(loggedInAs));
        return this;
    }

    /** Ensures we end up on checkout: reopen cart, click proceed (with retries), and wait. */
    public boolean ensureOnCheckoutViaCart() {
        openCartDirect();
        for (int attempt = 0; attempt < 2; attempt++) {
            clickProceedToCheckout();
            if (waitUntilOnCheckoutPage()) return true;
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        }
        return isOnCheckoutPage();
    }

    // ======== Cart assertions ========
    public int getCartLineItemCount() {
        List<WebElement> rows = driver.findElements(By.cssSelector("#cart_info_table tbody tr"));
        return rows.size();
    }

    public boolean isProductWithNamePresentInCart(String keyword) {
        List<WebElement> rows = driver.findElements(By.cssSelector("#cart_info_table tbody tr"));
        String needle = keyword.toLowerCase();
        for (WebElement r : rows) {
            String text = r.getText();
            if (text != null && text.toLowerCase().contains(needle)) return true;
        }
        return false;
    }

    // ======== Utils ========
    private boolean isVisible(By locator) {
        List<WebElement> els = driver.findElements(locator);
        return !els.isEmpty() && els.get(0).isDisplayed();
    }

    private void waitUntilDocumentReady() {
        wait.until(wd -> "complete".equals(js.executeScript("return document.readyState")));
    }

    private void dismissPossibleOverlayOrCookieSilently() {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

            List<By> candidates = java.util.Arrays.asList(
                By.cssSelector("button#closeCookie, .cookie-accept, .cc-allow, .cc-allow-all, .accept-cookie, .cookie-consent-accept"),
                By.xpath("//button[contains(.,'Accept') and contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'cookie')]"),
                By.cssSelector(".modal.show .close, .modal.show [data-dismiss='modal'], .modal .close"),
                By.xpath("//*[contains(@class,'close') and (self::button or self::a)]")
            );

            for (By c : candidates) {
                List<WebElement> els = driver.findElements(c);
                for (WebElement e : els) {
                    if (e.isDisplayed()) {
                        js.executeScript("arguments[0].click();", e);
                    }
                }
            }

            dismissPossibleIframeAdSilently();
        } catch (Exception ignore) {
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }

    /** Hide common high-z-index overlays that intercept clicks (e.g. 'grippy-host'). */
    private void hideStickyOverlays() {
        try {
            js.executeScript(
                "document.querySelectorAll("
              + "'.grippy-host, .grippy-outer, .grecaptcha-badge, [id*=\"google_ads\"], [class*=\"ads\"], [style*=\"z-index: 2147483647\"]'"
              + ").forEach(e=>{e.style.pointerEvents='none'; e.style.display='none';});"
            );
        } catch (Exception ignore) {}
    }

    private void dismissPossibleIframeAdSilently() {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            for (WebElement f : iframes) {
                try {
                    driver.switchTo().frame(f);
                    List<By> closers = java.util.Arrays.asList(
                        By.cssSelector("[aria-label='Close'], .close, .close-button, .dismiss"),
                        By.xpath("//*[contains(@class,'close') or contains(.,'Close')]")
                    );
                    for (By c : closers) {
                        List<WebElement> xs = driver.findElements(c);
                        if (!xs.isEmpty() && xs.get(0).isDisplayed()) {
                            js.executeScript("arguments[0].click();", xs.get(0));
                            break;
                        }
                    }
                } catch (Exception ignore) {
                } finally {
                    driver.switchTo().defaultContent();
                }
            }
        } catch (Exception ignore) {
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }
}
