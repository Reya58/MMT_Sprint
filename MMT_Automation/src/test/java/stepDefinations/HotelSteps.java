package stepDefinations;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.HotelListingsPage;
import pages.HotelSearchPage;
import utils.DriverFactory;
import utils.Hotel_ExcelUtil;

public class HotelSteps {

    WebDriver driver;
    WebDriverWait wait;

    HotelSearchPage hs;
    HotelListingsPage hl;

    Map<String, String> data;
    int noOfHotels;
    private static final By LISTING_PAGE_HEADER =
			By.xpath("//p[@class='font24 clampLine1Container']");
    
    private static final By HEADING=
    		By.xpath("//h1[@class='font24 latoBlack blackText appendTop12']");

	private static final By HOTEL_PRICE_ELEMENTS =
			By.xpath("//div[contains(@class,'priceSec')]//p[contains(@class,'amount')] " +
					 "| //span[contains(@class,'price')] " +
					 "| //p[contains(@class,'price')]");


	private static final By MSG =
			By.xpath("//*[contains(text(),'Sold Out') " +
					 "or contains(text(),'sold out') " +
					 "or contains(text(),'No hotels found')]");

    // ============================================================
    // BACKGROUND
    // ============================================================
//	@Before
//    public void setUp() {
////        DriverFactory.initDriver();
////
////        driver = DriverFactory.getDriver();
////
////        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
////        driver.manage().window().maximize();
//		String browser = runner.Hotel_RunnerIo.browser;
//	    Hotels_DriverFactory.initDriver(browser);
//	    driver = Hotels_DriverFactory.getDriver();
//	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
//        driver.manage().window().maximize();
//    }
//
//    @After
//    public void tearDown(Scenario scenario) {
//        System.out.println("Finished Scenario: " + scenario.getName());
//        Hotels_DriverFactory.quitDriver();
//    }
	@Given("the user is on the MakeMyTrip Hotels homepage")
    public void open_homepage() throws InterruptedException {
//        driver = new FirefoxDriver();
//        driver.manage().window().maximize();
    	driver = DriverFactory.getDriver();
        driver.get("https://www.makemytrip.com/?cc=in&redirectedBy=gl");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        hs = new HotelSearchPage(driver);
        hl = new HotelListingsPage(driver);

        hs.preReq(); // close popup
    }

//    @After
//    public void tearDown() {
////        if (driver != null) driver.quit();
//    	Hotel_DriverFactory.quitDriver();
//    }

    // ============================================================
    // WHEN – TC_ID BASED ENTRY
    // ============================================================

    @When("the user enters {string}")
    public void user_enters_tcId(String tcId) throws Exception {

        switch(tcId) {

            case "TC_MMT_Hotels_01_03":
                execute_TC_01_03(tcId);
                break;

            case "TC_MMT_Hotels_01_04":
                execute_TC_01_04(tcId);
                break;

            default:
                execute_default_meth(tcId);
        }
    }
    String min;
    String max;
    @When("the user sets the minimum price to {string}")
    public void the_user_sets_the_minimum_price_to(String min) {
        
        this.min=min;;
    }
    @When("the user sets the maximum price to {string}")
    public void the_user_sets_the_maximum_price_to(String max) {
        
        this.max=max;
        hl.getMinPriceField().sendKeys(min);
		hl.getMaxPriceField().clear();
		hl.getMaxPriceField().sendKeys(max);
		hl.getMinMaxRangeBtn().click();
    }

    // ============================================================
    // TC IMPLEMENTATIONS (SEPARATE FUNCTIONS)
    // ============================================================

    // TC_01_01, TC_01_02, TC_03_01, TC_04_01, TC_04_02
    public void execute_default_meth(String tcId) throws Exception {
        data = Hotel_ExcelUtil.getRowData(tcId);

        hs.enterDestination(data.get("City"));
        hs.enterCheckInDate(data.get("Check-in"));
        hs.enterCheckOutDate(data.get("Check-out"));
        hs.enterRoomsAndGuests(data.get("Rooms"), data.get("Guests"));
        hs.clickSearchBtn();
        
       
        if(tcId.equals("TC_MMT_Hotels_03_01"))
        {
        	String heading=driver.findElement(HEADING).getText();
            heading=heading.substring(0,heading.indexOf(' '));
            noOfHotels=Integer.parseInt(heading);
            hl.filterBy5Stars();
        }
        	
    }


    //  TC_01_03
    public void execute_TC_01_03(String tcId) throws Exception {
        data = Hotel_ExcelUtil.getRowData(tcId);
        hs.enterNonExistentDestination(data.get("City"));
    }

    //  TC_01_04
    public void execute_TC_01_04(String tcId) throws Exception {
        data = Hotel_ExcelUtil.getRowData(tcId);
        hs.enterSpecialCharDestination(data.get("City"));
        hs.enterCheckInDate(data.get("Check-in"));
        hs.enterCheckOutDate(data.get("Check-out"));
        hs.enterRoomsAndGuests(data.get("Rooms"), data.get("Guests"));
        hs.clickSearchBtn();
    }


    // ============================================================
    // THEN (ASSERTIONS)
    // ============================================================

    @Then("the hotel listing page is displayed")
    public void verify_listing() {
        Assert.assertTrue(hl.isHotelListingsDisplayed());
    }

    @Then("at least one hotel card displays the correct location tag for {string}")
    public void verify_location(String landmark) {
        //Assert.assertTrue(hl.verifyLocationTag(landmark));
    	
    	 List<WebElement> list = driver.findElements(By.xpath("//div[@class='pc__html']/child::span[2]"));
    	 Assert.assertFalse(list.isEmpty());
		 for(WebElement l:list)
		 {
			 String s=l.getText();
			 Assert.assertTrue(s.contains("from "+landmark));
		 }
		

    }

    @Then("displays a no hotels found message")
    public void verify_no_results() {
        //Assert.assertTrue(hs.isNoResultsDisplayed());
    	
		String msg = hs.getNonExistentCityMsg().getText();
		Assert.assertFalse(msg.isEmpty(),
				"'No Results Found' message should be visible for a non-existent city.");
    }


    @Then("the destination field contains only {string}")
    public void verify_clean_value(String expected) {
        //Assert.assertTrue(hs.getDestinationValue().contains(expected));
    	String value = hs.getDestinationField().getAttribute("value").trim();
//		Assert.assertFalse(value.contains("!"),
//				"Special characters should have been stripped from the destination field. " +
//				"Actual value: " + value);
    	Assert.assertEquals(value, expected);
    	

    }
    
    @Then("at least one displayed hotel cards show a {int} star rating")
    public void verify_star_filter(int stars) {
        //Assert.assertTrue(hl.verifyStarRating(stars));

		List<WebElement> fiveStarHotels=driver.findElements(By.xpath("//span[@class='sprite rating_fill ratingFive']"));
		Assert.assertTrue(fiveStarHotels.size()>0);
	}


    @Then("the result count updates to reflect only {int} star properties")
    public void verify_result_count(int stars) {
        //Assert.assertTrue(hl.isHotelListingsDisplayed());
    	String heading=driver.findElement(HEADING).getText();
        heading=heading.substring(0,heading.indexOf(' '));
        int noOfHotelsAfterFilter=Integer.parseInt(heading);
        Assert.assertTrue(noOfHotelsAfterFilter<noOfHotels);
    	
		
    }

    @Then("all displayed hotel cards show a per-night rate between {string} and {string}")
    public void verify_price_range(String min, String max) {
       // Assert.assertTrue(hl.verifyPriceRange(min, max));
    	
		int minVal = Integer.parseInt(min);
		int maxVal = Integer.parseInt(max);
		List<Integer> prices = extractAllPrices();
		Assert.assertFalse(prices.isEmpty(),
				"At least one hotel card with a visible price should be present.");
		for (int price : prices) {
			Assert.assertTrue(price >= minVal && price <= maxVal,
					"Price ₹" + price + " is outside the expected range " +
					"[₹" + min + " – ₹" + max + "].");
		}

    }

    @Then("no hotel cards are displayed")
    public void verify_no_cards() {
        //Assert.assertTrue(hl.noHotelsDisplayed());
 
		List<WebElement> cards = driver.findElements(LISTING_PAGE_HEADER);
		Assert.assertTrue(cards.isEmpty(),
				"No hotel cards should be rendered for this price range.");

    }

    @Then("an 'Adjust price filter' message is shown")
    public void verify_sold_out() {
        //Assert.assertTrue(hl.isSoldOutMessageDisplayed());
    	
		WebElement msg = driver.findElement(MSG);
		Assert.assertTrue(msg.getText().contains("Adjust price range filter"),
				"A 'Adjust price range filter' message should be visible " +
				"when no hotels match the price range.");

    }
    
//    private void pauseForFilterUpdate() {
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			Thread.currentThread().interrupt();
//		}
//    }
    
    private List<Integer> extractAllPrices() {
		List<WebElement> priceElements = driver.findElements(HOTEL_PRICE_ELEMENTS);
		List<Integer> prices = new ArrayList<>();
		for (WebElement el : priceElements) {
			String text = el.getText().replaceAll("[^0-9]", "").trim();
			if (!text.isEmpty()) {
				prices.add(Integer.parseInt(text));
			}
		}
		return prices;
	}


}
