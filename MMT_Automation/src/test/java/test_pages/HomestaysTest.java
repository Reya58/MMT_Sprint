package test_pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.HomeStays;
import pages.StaysFilter;
import pages.PropertyPage;
import pages.BookingPage;

public class HomestaysTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("https://www.makemytrip.com/homestays/");
    }

    @Test
    public void testCompleteHomestaysFlow() throws InterruptedException {

        // ===== Step 1: Search =====
        HomeStays home = new HomeStays(driver);
        home.close_popup();
        home.enterLocation("Goa");
        home.selectDates();
        home.selectAdults(2);
        home.selectChildren(0);
        home.clickSearch();

        // ===== Step 2: Apply Filters =====
        StaysFilter filter = new StaysFilter(driver);
        filter.applyPriceRange("1000", "3000");
        filter.applyApartmentFilter();

        int count = filter.getHotelCount();
        System.out.println("Hotels after filter: " + count);
        Assert.assertTrue(count > 0, "No hotels found after applying filter");
        filter.printHotelNames();

        // ===== Step 3: Select Property =====
        filter.selectFirstHotel();

        // ===== Step 4: Property Page =====
        PropertyPage property = new PropertyPage(driver);
        Assert.assertTrue(property.isPropertyPageDisplayed(), "Property page is NOT displayed");
        System.out.println("Property Page Opened Successfully");
      //  property.viewReviews();

        // ===== Step 5: Booking Flow =====
        property.clickBookNow();
        System.out.println("Clicked Book Now");

        // ===== Step 6: Booking Page =====
        BookingPage booking = new BookingPage(driver);
         
        booking.expandPriceBreakup();
        // Wait until price section is visible (View Price Breakup)
        //wait.until(ExpectedConditions.visibilityOf(booking.getTotalPrice()));

        // Get price before applying coupon
        String priceBefore = booking.getTotalPrice();
        System.out.println("Price before coupon: " + priceBefore);

        // Apply a coupon
        booking.applyCoupon("NEWHOMESTAY");
        System.out.println("Coupon applied successfully");

        // Get updated price after coupon
        String priceAfter = booking.getUpdatedPriceAfterCoupon(priceBefore);
        System.out.println("Price after coupon: " + priceAfter);

        // ✅ Check if price is updated
        Assert.assertNotEquals(priceAfter, priceBefore, "Price did NOT change after applying coupon");

        // Optionally click Pay Now
        // booking.clickPayNow();
        // System.out.println("Clicked Pay Now");

        System.out.println("Test Completed Successfully");
    }

    @AfterMethod
    public void tearDown() {
        // driver.quit(); // Enable when needed
    }
}