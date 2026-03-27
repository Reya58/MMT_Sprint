package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StaysFilter {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    private static final By HOTEL_NAME_LOCATOR = By.xpath(
        "//p[@id='hlistpg_hotel_name'] | //p[contains(@class,'hotelCardComp__hotelName')]"
    );

    private static final By HOTEL_PRICE_LOCATOR = By.xpath(
        "//p[@id=\"hlistpg_hotel_shown_price\"]"
    );

    public StaysFilter(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js     = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // ── Filters ──────────────────────────────────────────────────────────────

    @FindBy(xpath = "//input[@aria-label='Apartment'] | //label[contains(text(),'Apartment')]")
    private WebElement apartmentFilter;

    @FindBy(xpath = "//input[contains(@aria-label,'Excellent')] | //label[contains(text(),'Excellent')]")
    private WebElement ratingFilter;

    @FindBy(xpath = "//input[@id='Wi-Fi'] | //label[contains(text(),'Wi-Fi')]")
    private WebElement wifiFilter;

    // ── Sort Options ─────────────────────────────────────────────────────────

    @FindBy(xpath = "//span[text()='Price (Low to High)']")
    private WebElement sortLowToHighOption;

    @FindBy(xpath = "//span[text()='Price (High to Low)']")
    private WebElement sortHighToLowOption;

    // ── Hotel list ───────────────────────────────────────────────────────────

    @FindBy(xpath = "(//p[@id='hlistpg_hotel_name'] | //p[contains(@class,'hotelCardComp__hotelName')])[1]")
    private WebElement firstHotel;

    // ── Public Methods ──────────────────────────────────────────────────────

    public int getHotelCount() {
        try {
            return driver.findElements(HOTEL_NAME_LOCATOR).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public List<String> getHotelNames() {
        List<String> names = new ArrayList<>();
        List<WebElement> hotels = driver.findElements(HOTEL_NAME_LOCATOR);
        for (WebElement hotel : hotels) {
            names.add(hotel.getText().trim());
        }
        return names;
    }

    public void printHotelNames() {
        List<String> names = getHotelNames();
        if (names.isEmpty()) {
            System.out.println("[StaysFilter] No hotel names found on the page.");
        } else {
            System.out.println("[StaysFilter] Hotel names:");
            for (String name : names) {
                System.out.println(name);
            }
        }
    }

    public void applyApartmentFilter() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@aria-label='Apartment'] | //label[contains(text(),'Apartment')]")));
        js.executeScript("arguments[0].click();", apartmentFilter);
        waitForHotelListToRefresh();
    }

    public void applyRatingFilter() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[contains(@aria-label,'Excellent')] | //label[contains(text(),'Excellent')]")));
        js.executeScript("arguments[0].click();", ratingFilter);
        waitForHotelListToRefresh();
    }

    public void applyWifiFilter() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@id='Wi-Fi'] | //label[contains(text(),'Wi-Fi')]")));
        js.executeScript("arguments[0].click();", wifiFilter);
        waitForHotelListToRefresh();
    }

    public void sortLowToHigh() {
        wait.until(ExpectedConditions.elementToBeClickable(sortLowToHighOption));
        js.executeScript("arguments[0].click();", sortLowToHighOption);
        waitForHotelListToRefresh();
    }

    public void sortHighToLow() {
        wait.until(ExpectedConditions.elementToBeClickable(sortHighToLowOption));
        js.executeScript("arguments[0].click();", sortHighToLowOption);
        waitForHotelListToRefresh();
    }

    /**
     * Verifies if hotel prices are sorted in ascending order
     */
    public boolean isSortedLowToHigh() {
        List<Integer> prices = getHotelPrices();
        System.out.println(prices);
        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) > prices.get(i+1)) return false;
        }
        return true;
    }

    public boolean isSortedHighToLow() {
        List<Integer> prices = getHotelPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) < prices.get(i+1)) return false;
        }
        return true;
    }
    public void selectFirstHotel() {
        wait.until(ExpectedConditions.elementToBeClickable(firstHotel));
        String parentWindow = driver.getWindowHandle();
        js.executeScript("arguments[0].click();", firstHotel);

        wait.until(d -> d.getWindowHandles().size() > 1);
        for (String window : driver.getWindowHandles()) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private void waitForHotelListToRefresh() {
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(HOTEL_NAME_LOCATOR));
        } catch (Exception e) {
            System.out.println("[StaysFilter] Hotel list did not refresh – 0 results may be expected.");
        }
    }

    private List<Integer> getHotelPrices() {
        List<Integer> prices = new ArrayList<>();
        List<WebElement> priceElements = driver.findElements(HOTEL_PRICE_LOCATOR);
        for (WebElement p : priceElements) {
            try {
                String text = p.getText().replaceAll("[^0-9]", "");
                if (!text.isEmpty()) {
                    prices.add(Integer.parseInt(text));
                }
            } catch (Exception e) {
                // ignore invalid price text
            }
        }
        return prices;
    }
}