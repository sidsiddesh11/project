package com.project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.project.utilities.ScreenshotUtilities;

import java.time.Duration;
import java.util.List;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Top-level nav
    private static final By HOME_LINK       = By.xpath("//a[@href='/' ]");
    private static final By PRODUCTS_LINK   = By.xpath("//a[@href='/products']");
    private static final By LOGIN_LINK      = By.xpath("//a[@href='/login']");
    private static final By API_LIST_LINK   = By.xpath("//a[@href='/api_list']");
    private static final By TEST_CASES_LINK = By.xpath("//a[@href='/test_cases']");
    private static final By CONTACT_US_LINK = By.xpath("//a[@href='/contact_us']");
    private static final By VIEW_CART_ICON  = By.xpath("//a[@href='/view_cart']");
    private static final By SCROLL_UP_BTN   = By.id("scrollUp");

    // Cart page specific
    private static final By VIEW_CART_LINK      = By.linkText("View Cart");
    private static final By CART_TABLE          = By.id("cart_info");
    private static final By EMPTY_CART_LINK     = By.xpath("//*[@id='empty_cart']/p/a/u");

    private static final By PROCEED_TO_CHECKOUT =
            By.xpath("//*[@id='do_action']//a[contains(@href,'checkout')]");
    private static final By PROCEED_TO_CHECKOUT_ALT =
            By.xpath("//a[contains(.,'Proceed To Checkout') and contains(@class,'check_out')]");
    private static final By PROCEED_TO_CHECKOUT_BY_TEXT =
            By.xpath("//a[normalize-space()='Proceed To Checkout']");
    private static final By PROCEED_TO_CHECKOUT_BY_CSS =
            By.cssSelector("#do_action a.check_out");

    private static final By LOGIN_TO_CHECKOUT   = By.xpath("//*[@id='checkoutModal']//a[@href='/login']");
    private static final By COMMENT_BOX         = By.xpath("//*[@id='ordermsg']/textarea");

    // Checkout -> Place order
    private static final By PLACE_ORDER_AT_CHECKOUT = By.xpath("//*[@id='cart_items']/div/div[7]/a");
    private static final By PLACE_ORDER_BTN         = By.xpath("//a[@href='/payment']");

    // Checkout verification
    private static final By CHECKOUT_BREADCRUMB = By.xpath("//*[@id='cart_items']/div/div[1]/ol/li[2]");

    // Generic elements
    private static final By LOGO   = By.xpath("//div[@id='header']//a/img");
    private static final By HEADER = By.id("header");
    private static final By FOOTER = By.id("footer");

    // ====== Login state locators ======
    private static final By LOGGED_IN_AS = By.xpath("//a[contains(.,'Logged in as')]");
    private static final By LOGOUT_LINK  = By.xpath("//a[normalize-space()='Logout']");

    // ===================== PAYMENT (integrated) =====================
    private static final By PP_NAME_ON_CARD  = By.name("name_on_card");
    private static final By PP_CARD_NUMBER   = By.name("card_number");
    private static final By PP_CVC           = By.name("cvc");
    private static final By PP_EXPIRY_MONTH  = By.name("expiry_month");
    private static final By PP_EXPIRY_YEAR   = By.name("expiry_year");
    private static final By PP_PAY_CONFIRM   = By.xpath("//button[contains(.,'Pay and Confirm Order')]");

    private static final By PP_ORDER_PLACED  = By.xpath("//*[contains(normalize-space(),'ORDER PLACED')]");
    private static final By PP_CONFIRMED_TXT = By.xpath("//*[contains(.,\"Congratulations! Your order has been confirmed!\")]");

    private static final By PP_DOWNLOAD_BTN  = By.xpath("//*[@id='form']/div/div/div/a");
    private static final By PP_CONTINUE_BTN  = By.xpath("//*[@id='form']/div/div/div/div/a");
    // ====================================================================

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait  = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ---------- Navigation ----------
    public CartPage openHome() {
        driver.get("https://automationexercise.com/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(HEADER));
        return this;
    }

    public Login_page goToLogin() {
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(LOGIN_LINK));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginBtn);
        loginBtn.click();
        wait.until(ExpectedConditions.urlContains("/login"));
        return new Login_page(driver);
    }

    /** Robust login that tolerates overlays and slow "Logged in as" banner. */
    public CartPage login(String email, String password) {
        Login_page lp = this.goToLogin();
        lp.login(email, password);

        // Some pages load slow or ads overlay – handle either 'Logged in as' or 'Logout' or just header back on /
        try {
            new WebDriverWait(driver, Duration.ofSeconds(25)).until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(LOGGED_IN_AS),
                    ExpectedConditions.visibilityOfElementLocated(LOGOUT_LINK),
                    ExpectedConditions.urlContains("/"),
                    ExpectedConditions.visibilityOfElementLocated(HEADER)
            ));
        } catch (Exception ignored) {}

        nukeOverlays(); // make sure header is interactable
        return this;
    }

    public boolean isUserLoggedIn() {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.presenceOfElementLocated(LOGOUT_LINK))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public CartPage goToProductsPage() {
        wait.until(ExpectedConditions.elementToBeClickable(PRODUCTS_LINK)).click();
        wait.until(ExpectedConditions.urlContains("/products"));
        return this;
    }

    public CartPage openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(VIEW_CART_ICON)).click();
        wait.until(ExpectedConditions.urlContains("/view_cart"));
        return this;
    }

    // ---------- Cart ----------
    public CartPage clickAddToCart(String productId) {
        By addBtn = By.xpath(String.format("//a[@data-product-id='%s']", productId));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(addBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try { wait.until(ExpectedConditions.elementToBeClickable(element)).click(); }
        catch (ElementClickInterceptedException e) { ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element); }
        wait.until(ExpectedConditions.visibilityOfElementLocated(VIEW_CART_LINK));
        return this;
    }

    public CartPage clickViewCartInPopup() {
        wait.until(ExpectedConditions.elementToBeClickable(VIEW_CART_LINK)).click();
        wait.until(ExpectedConditions.urlContains("/view_cart"));
        return this;
    }

    public boolean isProductInCart(String productName) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(CART_TABLE))
                   .getText().contains(productName);
    }

    public CartPage removeCartItemByRowId(String rowProductId) {
        By remove = By.xpath(String.format("//*[@id='%s']/td[6]/a", rowProductId));
        wait.until(ExpectedConditions.elementToBeClickable(remove)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(remove));
        return this;
    }

    public CartPage clickEmptyCartHere() {
        wait.until(ExpectedConditions.elementToBeClickable(EMPTY_CART_LINK)).click();
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/products"),
                ExpectedConditions.visibilityOfElementLocated(HEADER),
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(.,'Subscription')]"))
        ));
        return this;
    }

    // ---------- Proceed to Checkout ----------
    public CartPage proceedToCheckout() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(CART_TABLE));
        waitForDocumentReady();

        WebElement btn = findFirstPresent(
                PROCEED_TO_CHECKOUT,
                PROCEED_TO_CHECKOUT_BY_CSS,
                PROCEED_TO_CHECKOUT_ALT,
                PROCEED_TO_CHECKOUT_BY_TEXT
        );

        if (btn == null) {
            driver.navigate().refresh();
            wait.until(ExpectedConditions.visibilityOfElementLocated(CART_TABLE));
            waitForDocumentReady();
            btn = findFirstPresent(
                    PROCEED_TO_CHECKOUT,
                    PROCEED_TO_CHECKOUT_BY_CSS,
                    PROCEED_TO_CHECKOUT_ALT,
                    PROCEED_TO_CHECKOUT_BY_TEXT
            );
        }

        if (btn != null) {
            nukeOverlays();
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
            } catch (Exception e1) {
                try { new Actions(driver).moveToElement(btn).pause(Duration.ofMillis(120)).click().perform(); }
                catch (Exception e2) { ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn); }
            }

            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/checkout"),
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//h2[contains(.,'Address Details')] | //h2[contains(.,'Review Your Order')]")
                    ),
                    ExpectedConditions.visibilityOfElementLocated(CHECKOUT_BREADCRUMB)
            ));
        }
        return this;
    }

    // ---------- Place Order on Checkout ----------
    public CartPage clickPlaceOrder() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/checkout"),
                    ExpectedConditions.visibilityOfElementLocated(CHECKOUT_BREADCRUMB)
            ));
        } catch (Exception ignored) {}

        WebElement btn = null;
        List<WebElement> list = driver.findElements(PLACE_ORDER_AT_CHECKOUT);
        if (!list.isEmpty()) btn = list.get(0);
        if (btn == null) {
            list = driver.findElements(PLACE_ORDER_BTN);
            if (!list.isEmpty()) btn = list.get(0);
        }

        if (btn == null) {
            driver.navigate().refresh();
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/checkout"),
                    ExpectedConditions.visibilityOfElementLocated(CHECKOUT_BREADCRUMB)
            ));
            list = driver.findElements(PLACE_ORDER_AT_CHECKOUT);
            if (!list.isEmpty()) btn = list.get(0);
            if (btn == null) {
                list = driver.findElements(PLACE_ORDER_BTN);
                if (!list.isEmpty()) btn = list.get(0);
            }
        }

        if (btn != null) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
            } catch (Exception e1) {
                try { new Actions(driver).moveToElement(btn).pause(Duration.ofMillis(120)).click().perform(); }
                catch (Exception e2) { ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn); }
            }
            wait.until(ExpectedConditions.urlContains("/payment"));
        }
        return this;
    }

    public CartPage clickPlaceOrderAndWait() { return clickPlaceOrder(); }

    public boolean isCheckoutPageDisplayed() {
        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/checkout"),
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[contains(.,'Address Details')] | //h2[contains(.,'Review Your Order')]")
                ),
                ExpectedConditions.visibilityOfElementLocated(CHECKOUT_BREADCRUMB)
            ));
            return true;
        } catch (Exception e) { return false; }
    }

    // ===================== PAYMENT HELPERS =====================
    public boolean isPaymentFormDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(PP_NAME_ON_CARD));
            return driver.findElement(PP_NAME_ON_CARD).isDisplayed()
                && driver.findElement(PP_CARD_NUMBER).isDisplayed()
                && driver.findElement(PP_CVC).isDisplayed()
                && driver.findElement(PP_EXPIRY_MONTH).isDisplayed()
                && driver.findElement(PP_EXPIRY_YEAR).isDisplayed();
        } catch (Exception e) { return false; }
    }

    public String paymentGetTitle() { return driver.getTitle(); }

    public CartPage paymentEnterName(String v)  { type(PP_NAME_ON_CARD, v);  return this; }
    public CartPage paymentEnterCard(String v)  { type(PP_CARD_NUMBER, v);   return this; }
    public CartPage paymentEnterCVC(String v)   { type(PP_CVC, v);           return this; }
    public CartPage paymentEnterMM(String v)    { type(PP_EXPIRY_MONTH, v);  return this; }
    public CartPage paymentEnterYYYY(String v)  { type(PP_EXPIRY_YEAR, v);   return this; }

    public CartPage paymentFill(String name, String card, String cvc, String mm, String yyyy) {
        return paymentEnterName(name)
                .paymentEnterCard(card)
                .paymentEnterCVC(cvc)
                .paymentEnterMM(mm)
                .paymentEnterYYYY(yyyy);
    }

    public CartPage paymentClickPay() {
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(PP_PAY_CONFIRM));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
        nukeOverlays();
        try { wait.until(ExpectedConditions.elementToBeClickable(btn)).click(); }
        catch (Exception e1) {
            try { new Actions(driver).moveToElement(btn).pause(Duration.ofMillis(100)).click().perform(); }
            catch (Exception e2) {
                try { ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn); }
                catch (Exception e3) {
                    try { ((JavascriptExecutor) driver).executeScript(
                            "var b=arguments[0]; var f=b.form || b.closest('form'); if(f){f.submit();}", btn); }
                    catch (Exception ignored) {}
                }
            }
        }

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/payment_done"),
                ExpectedConditions.visibilityOfElementLocated(PP_ORDER_PLACED),
                ExpectedConditions.visibilityOfElementLocated(PP_CONFIRMED_TXT)
        ));
        return this;
    }

    public boolean isOrderPlacedVisible() {
        return driver.getCurrentUrl().contains("/payment_done")
            || !driver.findElements(PP_ORDER_PLACED).isEmpty()
            || !driver.findElements(PP_CONFIRMED_TXT).isEmpty();
    }

    public CartPage waitForOrderPlaced() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/payment_done"),
                ExpectedConditions.visibilityOfElementLocated(PP_ORDER_PLACED),
                ExpectedConditions.visibilityOfElementLocated(PP_CONFIRMED_TXT)
        ));
        return this;
    }

    public CartPage paymentClickDownloadInvoice() {
        try {
            WebElement dl = wait.until(ExpectedConditions.elementToBeClickable(PP_DOWNLOAD_BTN));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dl);
            dl.click();
        } catch (Exception ignored) {}
        return this;
    }

    public CartPage paymentClickContinue() {
        try {
            WebElement cont = wait.until(ExpectedConditions.elementToBeClickable(PP_CONTINUE_BTN));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", cont);
            cont.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(HEADER));
        } catch (Exception ignored) {}
        return this;
    }
    // =======================================================================

    // ---------- Helpers ----------
    private WebElement findFirstPresent(By... locators) {
        for (By by : locators) {
            List<WebElement> list = driver.findElements(by);
            if (!list.isEmpty()) return list.get(0);
        }
        return null;
    }

    private void waitForDocumentReady() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(d ->
                "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState"))
            );
        } catch (Exception ignored) {}
    }

    private void type(By by, String v) {
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        e.clear();
        e.sendKeys(v);
    }

    /** Remove common ad iframes / overlays that block clicks. */
    private void nukeOverlays() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll(\"iframe[id^='aswift'],iframe[name^='aswift'],"
              + " [title='Advertisement'], [aria-label='Advertisement'], .adsbygoogle,"
              + " [style*='z-index: 2147483']\").forEach(e=>{try{e.remove()}catch(_){}});"
            );
        } catch (Exception ignored) {}
    }

    // ---------- Scrolling, header/footer, logo ----------
    public CartPage clickUpArrow() {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
            WebElement arrow = wait.until(ExpectedConditions.visibilityOfElementLocated(SCROLL_UP_BTN));
            wait.until(ExpectedConditions.elementToBeClickable(arrow)).click();
        } catch (Exception e) {
            try {
                WebElement arrow = driver.findElement(SCROLL_UP_BTN);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", arrow);
            } catch (Exception ignore) {}
        }
        return this;
    }

    public boolean isAddressDisplayed() {
        try {
            By addressSection = By.xpath("//h2[contains(text(),'Address Details')]");
            return wait.until(ExpectedConditions.visibilityOfElementLocated(addressSection)).isDisplayed();
        } catch (Exception e) { return false; }
    }

    public CartPage enterComment(String text) {
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(COMMENT_BOX));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", box);
        new Actions(driver).moveToElement(box).click().perform();
        box.clear();
        box.sendKeys(text);
        return this;
    }

    public boolean isCommentEntered() {
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(COMMENT_BOX));
        String val = box.getAttribute("value");
        return val != null && !val.trim().isEmpty();
    }

    public CartPage clickLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGO)).click();
        return this;
    }

    public boolean isLogoDisplayed() {
        try { return wait.until(ExpectedConditions.visibilityOfElementLocated(LOGO)).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public String captureLogoScreenshot(String screenshotName) {
        try { return ScreenshotUtilities.Capture(driver, screenshotName); }
        catch (Exception e) { e.printStackTrace(); return null; }
    }

    public String captueCartPageScreenshot(String screenshotName) {
        try { return ScreenshotUtilities.Capture(driver, screenshotName); }
        catch (Exception e) { e.printStackTrace(); return null; }
    }

    public CartPage scrollToFooter() {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(FOOTER));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
        return this;
    }

    public CartPage scrollToHeader() {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(HEADER));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
        return this;
    }

    public boolean isFooterDisplayed() {
        try { return wait.until(ExpectedConditions.visibilityOfElementLocated(FOOTER)).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public boolean isHeaderDisplayed() {
        try { return wait.until(ExpectedConditions.visibilityOfElementLocated(HEADER)).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public CartPage clickHome()      { wait.until(ExpectedConditions.elementToBeClickable(HOME_LINK)).click();      return this; }
    public CartPage clickProducts()  { wait.until(ExpectedConditions.elementToBeClickable(PRODUCTS_LINK)).click();  return this; }
    public CartPage clickApiList()   { wait.until(ExpectedConditions.elementToBeClickable(API_LIST_LINK)).click();  return this; }
    public CartPage clickTestCases() { wait.until(ExpectedConditions.elementToBeClickable(TEST_CASES_LINK)).click();return this; }
    public CartPage clickContactUs() { wait.until(ExpectedConditions.elementToBeClickable(CONTACT_US_LINK)).click();return this; }
}
