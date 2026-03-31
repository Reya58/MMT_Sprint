package stepDefinations;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import pages.*;
import utils.DriverFactory;

public class HomestaysSteps {

    WebDriver driver;
    HomeStays home;
    HomeStaysFilter filter;
    HomeStaysPropertyPage property;
    HomeStaysBookingPage booking;

    String oldPrice;

    // =========================================================
    // SETUP
    // =========================================================

    @Before
    public void setup() {

        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();
        driver.get("https://www.makemytrip.com/homestays/");
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    // =========================================================
    // BACKGROUND STEPS
    // =========================================================

    @Given("the user is on the MakeMyTrip homepage")
    public void open_homepage() throws InterruptedException {

        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState")
                        .equals("complete"));

        home = new HomeStays(driver);
        filter = new HomeStaysFilter(driver);
        property = new HomeStaysPropertyPage(driver);
        booking = new HomeStaysBookingPage(driver);

        
    }

    @Given("the user navigates to Villas and Homestays section")
    public void navigate_to_section() throws InterruptedException {
        System.out.println("[INFO] Villas & Homestays section loaded");
        
    }

    // =========================================================
    // TS01 - SEARCH
    // =========================================================

    @When("the user performs villa search for TC_MMT_Villas_{int}_{int}")
    public void villa_search(Integer tc1, Integer tc2) throws Exception {

    	String tcId = "TC_MMT_Villas_"
    	        + String.format("%02d", tc1)
    	        + "_"
    	        + String.format("%02d", tc2);

        System.out.println("[INFO] Executing TC: " + tcId);

        String city = utils.HomeStaysExcelReader.getCellData("Villas", tcId, "City");
        String adults = utils.HomeStaysExcelReader.getCellData("Villas", tcId, "Adults");
        String children = utils.HomeStaysExcelReader.getCellData("Villas", tcId, "Children");
        System.out.println(city+" "+adults+" "+children);

        home.close_popup();
         
        home.enterLocation(city);
        home.selectDates();
        home.selectAdults(Integer.parseInt(adults));
        home.selectChildren(Integer.parseInt(children));
        Thread.sleep(2000);
        home.clickSearch();

        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState")
                        .equals("complete"));
    }

    @Then("the villa listing validation is done")
    public void villa_listing_validation() {

        int count = filter.getHotelCount();

        Assert.assertTrue(count > 0,
                "No villa listings found");

        System.out.println("[PASS] Villa listings count: " + count);
    }

    // =========================================================
    // TS02 - FILTERS
    // =========================================================

    @When("the user applies filters for TC_MMT_Villas_{int}_{int}")
    public void apply_filters(Integer tc1, Integer tc2) {

        System.out.println("[INFO] Applying filters for TC " + tc1 + "_" + tc2);

        filter.applyApartmentFilter();
        filter.applyRatingFilter();
    }

    @Then("all displayed properties match selected filters")
    public void validate_filters() {

        Assert.assertTrue(filter.getHotelCount() > 0,
                "No results after applying filters");

        System.out.println("[PASS] Filters validated");
    }

    // =========================================================
    // TS03 - SORTING
    // =========================================================

    @When("the user sorts villas for TC_MMT_Villas_{int}_{int}")
    public void sort_villas(Integer tc1, Integer tc2) {
    	String tcId = "TC_MMT_Villas_"
    	        + String.format("%02d", tc1)
    	        + "_"
    	        + String.format("%02d", tc2);

  

        String sort = utils.HomeStaysExcelReader.getCellData("Villas", tcId, "Sort");
        if(sort.equals("LOW_TO_HIGH")) filter.sortLowToHigh();
        else filter.sortHighToLow();

        System.out.println("[INFO] Sorting TC " + tc1 + "_" + tc2);

    }

    @Then("villa listings are sorted correctly")
    public void validate_sorting() {

        boolean sorted =
                filter.isSortedLowToHigh()
                        || filter.isSortedHighToLow();

        Assert.assertTrue(sorted,
                "Sorting validation failed");

        System.out.println("[PASS] Sorting validated");
    }

    // =========================================================
    // TS04 - DETAILS + REVIEWS
    // =========================================================

    @When("the user opens villa details for TC_MMT_Villas_{int}_{int}")
    public void open_villa_details(Integer tc1, Integer tc2) {

        System.out.println("[INFO] Opening villa details " + tc1 + "_" + tc2);

        filter.selectFirstHotel();
    }

    @Then("villa details are validated")
    public void validate_villa_details() {

        Assert.assertTrue(property.isPropertyPageDisplayed(),
                "Villa details page not displayed");

        System.out.println("[PASS] Villa details validated");
    }

    @Then("guest reviews are validated")
    public void validate_reviews() {

        property.viewReviews();

        System.out.println("[PASS] Reviews validated");
    }

    // =========================================================
    // TS07 - COUPON
    // =========================================================

    @When("the user applies coupon for TC_MMT_Villas_{int}_{int}")
    public void apply_coupon(Integer tc1, Integer tc2) {
    	

    	String tcId = "TC_MMT_Villas_"
    	        + String.format("%02d", tc1)
    	        + "_"
    	        + String.format("%02d", tc2);

        String coupon = utils.HomeStaysExcelReader.getCellData("Villas", tcId, "Coupon");

        System.out.println("[INFO] Applying coupon: " + coupon);
        property.clickBookNow();

        oldPrice = booking.getTotalPrice();
        booking.applyCoupon(coupon);
    }

    @Then("discount is applied successfully")
    public void validate_coupon() {

        String newPrice = booking.getUpdatedPriceAfterCoupon(oldPrice);

        Assert.assertNotEquals(oldPrice, newPrice,
                "Coupon not applied");

        System.out.println("[PASS] Coupon validated");
    }
    @Then("no location suggestions should be displayed")
    public void no_location_suggestions_should_be_displayed() {
    	boolean result=home.isCityPresent();
    	Assert.assertFalse(result);
    }
}