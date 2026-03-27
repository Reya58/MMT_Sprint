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

public class BookingPage {

	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;

	public BookingPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		this.js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}

	// ── Coupon ───────────────────────────────────────────────────────────────

	// FIX: placeholder text may differ; broadened with contains()
	@FindBy(xpath = "//input[contains(@placeholder,'Coupon') or contains(@placeholder,'coupon')]")
	private WebElement couponField;

	// FIX: data-testid selector was fragile; added text-based fallback
	@FindBy(xpath = "//a[@data-testid='applyCpnBtn'] | //button[contains(text(),'Apply')]")
	private WebElement applyBtn;

	// ── Price ─────────────────────────────────────────────────────────────────

	// FIX: index-based (//div[@class='pricBreakup__rht'])[5] is very brittle.
	// Now targets the element containing "Total Amount" / "Total" label text.
	@FindBy(xpath = "//div[contains(@class,'pricBreakup__rht') and last()] " + "| //span[contains(@class,'totalAmt')] "
			+ "| //div[contains(text(),'Total')]/following-sibling::div[1]")
	private WebElement totalPriceEl;

	// FIX: broadened to cover both "View Price Breakup" and "Price Breakup" labels
	@FindBy(xpath = "//span[contains(text(),'Price Breakup')] | //a[contains(text(),'Price Breakup')]")
	private WebElement viewPriceBreakup;

	// ── Payment ──────────────────────────────────────────────────────────────

	@FindBy(xpath = "//button[contains(text(),'Pay Now')] | //button[contains(text(),'PAY NOW')]")
	private WebElement payNowBtn;

	// =========================================================================
	// Public Actions
	// =========================================================================

	/**
	 * Expands the price breakup panel if it is collapsed. Silently ignores errors
	 * if the panel is already expanded or absent.
	 */
	public void expandPriceBreakup() {
		try {
			WebElement breakup = wait.until(ExpectedConditions.elementToBeClickable(viewPriceBreakup));
			breakup.click();
			wait.until(ExpectedConditions.visibilityOf(totalPriceEl));
		} catch (Exception e) {
			// Already expanded or element not present – safe to continue
		}
	}

	/**
	 * Enters a coupon code and clicks Apply. Waits until the price element is
	 * visible after applying.
	 */
	public void applyCoupon(String couponCode) {
		try {
			wait.until(ExpectedConditions.visibilityOf(couponField));
			couponField.clear();
			couponField.sendKeys(couponCode);

			wait.until(ExpectedConditions.elementToBeClickable(applyBtn)).click();

			// Wait until the total price area is visible (page refreshes pricing)
			wait.until(ExpectedConditions.visibilityOf(totalPriceEl));
		} catch (Exception e) {
			System.out.println("[BookingPage] Coupon application failed: " + e.getMessage());
		}
	}

	/**
	 * Returns the current total price text from the price breakup section. Expands
	 * the section first if needed.
	 */
	public String getTotalPrice() {
		expandPriceBreakup();
		try {
			wait.until(ExpectedConditions.visibilityOf(totalPriceEl));
			return totalPriceEl.getText().trim();
		} catch (Exception e) {
			System.out.println("[BookingPage] Could not read total price: " + e.getMessage());
			return "";
		}
	}

	/**
	 * FIX: Waits for the price to actually differ from oldPrice before returning.
	 * Original had no safety net if the price never changed (infinite wait risk).
	 * Now uses a 15-second timeout and returns current text (changed or not).
	 */
	public String getUpdatedPriceAfterCoupon(Object oldPrice) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(15))
					.until(d -> !totalPriceEl.getText().trim().equals(oldPrice));
		} catch (Exception e) {
			System.out.println("[BookingPage] Price did not change after coupon – coupon may be invalid.");
		}
		return totalPriceEl.getText().trim();
	}

	/**
	 * Scrolls to the Pay Now button and clicks it.
	 * @throws InterruptedException 
	 */
	public void clickPayNow() throws InterruptedException {
	    Thread.sleep(2000);
		try {
			WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//button[contains(text(),'Pay Now')] | //button[contains(text(),'PAY NOW')]")));
			js.executeScript("arguments[0].scrollIntoView(true);", btn);
			wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
		} catch (Exception e) {
			System.out.println("[BookingPage] Pay Now button not found: " + e.getMessage());
		}
	}
}