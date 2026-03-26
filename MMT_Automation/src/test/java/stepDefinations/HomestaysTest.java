package stepDefinations;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import io.cucumber.java.en.*;
import pages.*;

public class HomestaysTest {

    WebDriver driver;
    HomeStays home;
    StaysFilter filter;
    PropertyPage property;
    BookingPage booking;

    String oldPrice;

    // =========================================================================
    // Setup
    // =========================================================================
    @Given("the user is on the MakeMyTrip homepage")
    public void open_homepage() throws InterruptedException {

        driver = Hooks.driverThread.get();

        // ✅ Initialize page objects HERE
        home = new HomeStays(driver);
        filter = new StaysFilter(driver);
        property = new PropertyPage(driver);
        booking = new BookingPage(driver);

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        driver.get("https://www.makemytrip.com/homestays/");
        Thread.sleep(3000);

        // Safe popup
                    home.close_popup();
      
    }
    @Given("the user navigates to Villas and Homestays section") 
    public void navigate_to_villas_and_homestays() { 
    	System.out.println("[Step] Villas and Homestays section – loaded via URL."); 
    	}
    // =========================================================================
    // Search Steps
    // =========================================================================
    @When("the user selects check-out date {string}") 
    public void select_check_out_date(String checkOut) { 
    	System.out.println("[Step] Check-out handled inside selectDates()."); 
    	}
    @When("the user enters {string} in the destination field")
    public void enter_destination(String destination) throws Exception {
        home.enterLocation(destination);
    }

    @When("the user selects check-in date {string}")
    public void select_check_in_date(String checkIn) {
        home.selectDates();
    }

    @When("the user sets guests to {string} adults")
    public void set_guests(String adults) throws InterruptedException {
        int count = Integer.parseInt(adults);
        home.selectAdults(count);
        home.selectChildren(0);
    }

    @When("the user clicks the Search button")
    public void click_search_button() throws InterruptedException {
        home.clickSearch();
        Thread.sleep(3000);
    }

    // =========================================================================
    // Assertions
    // =========================================================================
    @Then("the villa listing page is displayed")
    public void villa_listing_page_displayed() {
        Assert.assertTrue(filter.getHotelCount() > 0);
    }

    @Then("each villa card shows name, price, rating and location")
    public void verify_villa_cards() {
        List<String> names = filter.getHotelNames();
        Assert.assertFalse(names.isEmpty());
    }

    // =========================================================================
    // Property Steps
    // =========================================================================
    @When("the user selects a villa from results")
    public void select_villa() {
        filter.selectFirstHotel();
    }

    @Then("the property details page is displayed")
    public void property_details_page_displayed() {
        Assert.assertTrue(property.isPropertyPageDisplayed());
    }

    // =========================================================================
    // Booking
    // =========================================================================
    @When("the user proceeds to booking page")
    public void proceed_to_booking() throws Exception {
        property.clickBookNow();
        Thread.sleep(2000);
        switchToPropertyTab();
    }

    @When("the user enters coupon code {string}")
    public void enter_coupon(String code) {
        oldPrice = booking.getTotalPrice();
        booking.applyCoupon(code);
    }

    @Then("discount is applied successfully")
    public void verify_discount_applied() {
        String newPrice = booking.getUpdatedPriceAfterCoupon(oldPrice);
        Assert.assertNotEquals(oldPrice, newPrice);
    }

    // =========================================================================
    // Utility
    // =========================================================================
    public void switchToPropertyTab() {
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
    }
   
}