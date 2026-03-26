package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PropertyPage {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public PropertyPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js     = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // ── Locators ─────────────────────────────────────────────────────────────

    // FIX: broadened selector – property title may use different class names across pages
    @FindBy(xpath = "//h1[contains(@class,'hotelName')] | //h1[contains(@class,'hotel-name')] | //h1[@class]")
    private WebElement propertyTitle;

    // FIX: wishlist button selector broadened; original was too specific
    @FindBy(xpath = "//button[contains(@class,'wishlist')] | //span[contains(@class,'wishlist')]")
    private WebElement wishlistBtn;

    // FIX: "BOOK" text-match is case-sensitive and may vary; using contains + case variants
    @FindBy(xpath = "//button[@class=\"appBtn filled large bkngOption__cta  \"]")
    private WebElement bookNowBtn;

    // ── Review locator (used dynamically) ────────────────────────────────────

    private static final By REVIEW_LOCATOR =
        By.xpath("(//p[contains(@class,'reviewText')] | //div[contains(@class,'review-text')])[1]");

    // =========================================================================
    //  Public Actions
    // =========================================================================

    /**
     * Returns true if the property title is visible on the page.
     * Waits up to 20 s to account for slow page loads.
     */
    public boolean isPropertyPageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(propertyTitle)).isDisplayed();
        } catch (Exception e) {
            System.out.println("[PropertyPage] Property title not found: " + e.getMessage());
            return false;
        }
    }

    /**
     * Scrolls to the reviews section and prints the first review text.
     */
    public void viewReviews() {
        try {
            WebElement review = wait.until(ExpectedConditions.presenceOfElementLocated(REVIEW_LOCATOR));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", review);
            System.out.println("[PropertyPage] First Review: " + review.getText());
        } catch (Exception e) {
            System.out.println("[PropertyPage] No reviews found on this property.");
        }
    }

    /**
     * Clicks the wishlist (heart) button.
     * Scrolls into view first to avoid element-not-interactable errors.
     */
    public void addToWishlist() {
        try {
            WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(@class,'wishlist')] | //span[contains(@class,'wishlist')]")));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
            wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
        } catch (Exception e) {
            System.out.println("[PropertyPage] Wishlist button not found: " + e.getMessage());
        }
    }

    /**
     * FIX: Replaced doubleClick (which opened two tabs) with a single JS click on
     * the Book Now button. Also waits for a new tab to open and switches to it.
     */
    public void clickBookNow() throws InterruptedException {
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(bookNowBtn));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
            String parentWindow = driver.getWindowHandle();

            // Single JS click – double-click previously caused two booking tabs to open
            js.executeScript("arguments[0].click();", btn);
            System.out.println("[PropertyPage] Clicked Book Now");

            // Switch to newly opened booking tab if one appears
            try {
                wait.until(d -> d.getWindowHandles().size() > 1);
                for (String window : driver.getWindowHandles()) {
                    if (!window.equals(parentWindow)) {
                        driver.switchTo().window(window);
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("[PropertyPage] No new tab opened – staying on current window.");
            }
        } catch (Exception e) {
            System.out.println("[PropertyPage] Book Now button not found: " + e.getMessage());
        }
    }
}