package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class staysfilter {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    // Constructor
    public staysfilter(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // ================= LOCATORS =================

    @FindBy(xpath = "//h1")
    WebElement location;

    // ✅ Min-Max Price Inputs
    @FindBy(xpath = "//input[@name='min']")
    WebElement min_price;

    @FindBy(xpath = "//input[@name='max']")
    WebElement max_price;

    // Optional Apply button
    @FindBy(xpath = "//button[contains(@aria-label,'Range')]")
    WebElement price_set;

    // Property Type
    @FindBy(xpath = "//input[@aria-label='Apartment']")
    WebElement apartment;

    // Rating Filter
    @FindBy(xpath = "//input[contains(@aria-label,'Excellent')]")
    WebElement rating;

    // Hotel List
    @FindBy(xpath = "//p[@id='hlistpg_hotel_name']")
    List<WebElement> hotels;

    // First Hotel
    @FindBy(xpath = "(//p[@id='hlistpg_hotel_name'])[1]")
    WebElement firstHotel;

    // ================= METHODS =================

    public String getLocationText() {
        return location.getText();
    }

    // ✅ APPLY PRICE RANGE (FIXED)
    public void applyPriceRange(String min, String max) {

        wait.until(ExpectedConditions.visibilityOf(min_price));

        scrollToElement(min_price);

        min_price.clear();
        min_price.sendKeys(min);

        max_price.clear();
        max_price.sendKeys(max);

        // Press Enter if no button
        max_price.sendKeys(Keys.ENTER);

        // Optional click if button exists
        try {
            if (price_set.isDisplayed()) {
                price_set.click();
            }
        } catch (Exception e) {
            // ignore if not present
        }

        // Wait for results update
        wait.until(ExpectedConditions.visibilityOfAllElements(hotels));

        System.out.println("Price filter applied: " + min + " - " + max);
    }

    // Apartment Filter
    public void applyApartmentFilter() {
       // wait.until(ExpectedConditions.elementToBeClickable(apartment));
        scrollToElement(apartment);
        js.executeScript("arguments[0].click();", apartment);
    }

    // Rating Filter
    public void applyRatingFilter() {
        //wait.until(ExpectedConditions.elementToBeClickable(rating));
        scrollToElement(rating);
        js.executeScript("arguments[0].click();", rating);
    }

    // Apply All Filters
    public void applyAllFilters() {
        applyPriceRange("1000", "3000");
        applyApartmentFilter();
        applyRatingFilter();
    }

    // Hotel Count
    public int getHotelCount() {
        //wait.until(ExpectedConditions.visibilityOfAllElements(hotels));
        return hotels.size();
    }

    // Print Hotels
    public void printHotelNames() {
        for (WebElement hotel : hotels) {
            System.out.println(hotel.getText());
        }
    }

    // Select Hotel + Switch Tab
    public void selectFirstHotel() {
       // wait.until(ExpectedConditions.elementToBeClickable(firstHotel));
        firstHotel.click();

        for (String window : driver.getWindowHandles()) {
            driver.switchTo().window(window);
        }
    }

    // Scroll Utility
    public void scrollToElement(WebElement element) {
        js.executeScript(
            "arguments[0].scrollIntoView({block: 'center'});",
            element
        );
    }
}