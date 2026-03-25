package pages;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookingPage {

    WebDriver driver;

    // Constructor
    public BookingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ------------------ Coupon Section ------------------
    @FindBy(xpath = "//input[@placeholder='Have A Coupon Code?']")
    WebElement couponField;

    @FindBy(xpath = "//a[@data-testid='applyCpnBtn']")
    WebElement applybtn;

    // ------------------ Price Section ------------------
    @FindBy(xpath = "(//div[@class='pricBreakup__rht'])[5]")
	public
    WebElement totalPrice;

    @FindBy(xpath = "//span[text()='View Price Breakup']")
    WebElement viewPriceBreakup;

    // ------------------ Payment ------------------
    @FindBy(xpath = "//button[contains(text(),'Pay Now')]")
    WebElement payNowBtn;

    // ------------------ Actions ------------------

    /** Expand the price section if collapsed */
    public void expandPriceBreakup() {
        try {
            if (viewPriceBreakup.isDisplayed()) {
                viewPriceBreakup.click();
                // Wait until total price becomes visible
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOf(totalPrice));
            }
        } catch (Exception e) {
            // Already expanded or not present → ignore
        }
    }

    /** Apply coupon and wait for it to be applied */
    public void applyCoupon(String couponCode) {
        couponField.sendKeys(couponCode);
        applybtn.click();

        // Optional: wait until price changes
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(totalPrice));
    }

    /** Get current total price */
    public String getTotalPrice() {
        expandPriceBreakup(); // ensure price is visible
        return totalPrice.getText();
    }

    /** Get updated price after applying coupon */
    public String getUpdatedPriceAfterCoupon(String oldPrice) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> !totalPrice.getText().equals(oldPrice));
        return totalPrice.getText();
    }

    /** Click Pay Now button */
    public void clickPayNow() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", payNowBtn);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(payNowBtn));
        payNowBtn.click();
    }
}