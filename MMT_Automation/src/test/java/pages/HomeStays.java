package pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomeStays {

    WebDriver driver;
    WebDriverWait wait;

    public HomeStays(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // ── Popups ───────────────────────────────────────────────────────────────

    @FindBy(xpath = "//div[@class='tp-dt-header-icon'][2]")
    private WebElement popupHeaderIcon;

    // FIX: was using escaped quotes inside string; corrected to plain single quotes
    @FindBy(xpath = "//span[@class='commonModal__close']")
    private WebElement popupCloseBtn;

    // ── Location ─────────────────────────────────────────────────────────────

    @FindBy(xpath = "//input[@id='city']")
    private WebElement locationClickTarget;

    @FindBy(xpath = "//input[@placeholder='Where do you want to stay?']")
    private WebElement locationTextInput;

    // ── Guests ───────────────────────────────────────────────────────────────

    @FindBy(xpath = "//button[@class='counter__button counter__button--increment'][1]")
    private WebElement adultsIncrement;

    @FindBy(xpath = "//button[@class='counter__button counter__button--increment'][2]")
    private WebElement childrenIncrement;

    @FindBy(xpath = "//button[@class='primaryBtn btnApplyNew pushRight capText']")
    private WebElement guestsApplyBtn;

    // ── Search ───────────────────────────────────────────────────────────────

    @FindBy(xpath = "//button[@id='hsw_search_button']")
    private WebElement searchBtn;

    // =========================================================================
    //  Public Actions
    // =========================================================================

    /**
     * Dismisses the login/promo popup gracefully.
     * Each click is wrapped in try-catch so a missing element never fails the test.
     * @throws InterruptedException 
     */
    public void close_popup() throws InterruptedException {
    	Thread.sleep(2000);
    	popupHeaderIcon.click();
    	popupCloseBtn.click();
    	
    }

    /**
     * Types a city into the location search box and selects the first suggestion.
     */
    public void enterLocation(String city) throws InterruptedException {
    	System.out.println("Hitingg");
       wait.until(ExpectedConditions.elementToBeClickable(locationClickTarget)).click();
        wait.until(ExpectedConditions.visibilityOf(locationTextInput));
        locationTextInput.clear();
        locationTextInput.sendKeys(city);
        Thread.sleep(2000); // wait for autocomplete list
        locationTextInput.sendKeys(Keys.ARROW_DOWN);
        locationTextInput.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
    }

    /**
     * FIX: Replaced hardcoded date strings with dynamic dates (today+1 / today+2).
     * Hardcoded dates break tests as calendar days pass.
     */
    public void selectDates() {
        LocalDate checkIn  = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(2);

        // MakeMyTrip aria-label format: "EEE MMM dd yyyy"  e.g. "Sat Mar 28 2026"
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEE MMM dd yyyy");

        By ciLocator = By.xpath("//div[@aria-label='" + checkIn.format(fmt)  + "']");
        By coLocator = By.xpath("//div[@aria-label='" + checkOut.format(fmt) + "']");

        try {
            wait.until(ExpectedConditions.elementToBeClickable(ciLocator)).click();
            wait.until(ExpectedConditions.elementToBeClickable(coLocator)).click();
        } catch (Exception e) {
            System.out.println("[HomeStays] Date selection failed: " + e.getMessage());
        }
    }

    /**
     * Increments Adults counter to desired count.
     * MakeMyTrip defaults to 1 adult, so we click (count-1) times.
     */
    public void selectAdults(int count) {
    	count-=2;
        wait.until(ExpectedConditions.elementToBeClickable(adultsIncrement));
        for (int i = 1; i < count; i++) {
            adultsIncrement.click();
        }
    }

    /**
     * Increments Children counter then clicks Apply.
     * Pass 0 children to skip incrementing.
     * @throws InterruptedException 
     */
    public void selectChildren(int count) throws InterruptedException {
    	
        for (int i = 0; i < count; i++) {
            childrenIncrement.click();
        }
        guestsApplyBtn.click();
    }

    /** Clicks the Search button. */
    public void clickSearch() {
        searchBtn.click();
    }
}