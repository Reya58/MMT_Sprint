package tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.HomeStays;
import pages.staysfilter;

public class HomeStaysTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.makemytrip.com/");
    }

    // ============== COMPLETE FLOW =================
    @Test
    public void testSearchAndApplyFilters() throws InterruptedException {

        // Step 1: Search
        HomeStays home = new HomeStays(driver);

        home.close_popup();
        home.enterLocation("Goa");
        home.selectDates();
        home.selectAdults(2);
        home.selectChildren(1);
        home.clickSearch();

        // Wait for results page
        Thread.sleep(5000);

        // Step 2: Apply Filters
        staysfilter filter = new staysfilter(driver);

        filter.applyAllFilters();

        Thread.sleep(5000);

        int count = filter.getHotelCount();
        System.out.println("Hotels after filters: " + count);

        filter.printHotelNames();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}