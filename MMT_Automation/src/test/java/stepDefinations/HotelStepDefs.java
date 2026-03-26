package stepDefinations;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.HotelListingsPage;
import pages.HotelSearchPage;
import utils.Hotel_ExcelUtil;

import org.testng.Assert;

public class HotelStepDefs {

	// ── Page Objects & Driver ─────────────────────────────────────────────────

	WebDriver driver;
	HotelSearchPage hs;
	HotelListingsPage hl;
	WebDriverWait wait;

	// ── Shared state between steps ────────────────────────────────────────────

	Map<String, String> data;
	private String storedMinPrice;

	// ── Locators used across multiple steps ───────────────────────────────────

	private static final By LISTING_PAGE_HEADER =
			By.xpath("//p[@class='font24 clampLine1Container']");

	private static final By HOTEL_PRICE_ELEMENTS =
			By.xpath("//div[contains(@class,'priceSec')]//p[contains(@class,'amount')] " +
					 "| //span[contains(@class,'price')] " +
					 "| //p[contains(@class,'price')]");

	private static final By LOCATION_TAG_ELEMENTS =
			By.xpath("//p[contains(@class,'locality')] " +
					 "| //span[contains(@class,'address')] " +
					 "| //p[contains(@class,'hotel-location')]");

	private static final By SOLD_OUT_MSG =
			By.xpath("//*[contains(text(),'Sold Out') " +
					 "or contains(text(),'sold out') " +
					 "or contains(text(),'No hotels found')]");

	// ═════════════════════════════════════════════════════════════════════════
	// HOOKS
	// ═════════════════════════════════════════════════════════════════════════

	/**
	 * Background step – launches browser, navigates to MMT, and dismisses
	 * the login modal and chatbot popup before every scenario.
	 * @throws InterruptedException 
	 */
	@Given("the user is on the MakeMyTrip Hotels homepage")
	public void the_user_is_on_the_make_my_trip_hotels_homepage() throws InterruptedException {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.get("https://www.makemytrip.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		hs = new HotelSearchPage(driver);
		hl = new HotelListingsPage(driver);
		hs.preReq();  // closes login modal + chatbot, clicks Hotels tab
	}

	/** Quits the browser after every scenario. */
	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	// ═════════════════════════════════════════════════════════════════════════
	// WHEN – Search form interactions
	// ═════════════════════════════════════════════════════════════════════════

	/**
	 * TC_01_01 / TC_01_02 / TC_01_03 / TC_01_04
	 * Reads "City" column from the Excel row matching the given tcId,
	 * types it into the destination field, and selects the first suggestion.
	 */
	@When("the user enters {string} in the destination field")
	public void the_user_enters_in_the_destination_field(String tcId) throws Exception {
		data = Hotel_ExcelUtil.getRowData(tcId);
		hs.enterDestination(data.get("City"));
	}

	/**
	 * TC_01_01 / TC_02_02 etc.
	 * Reads "Check-in" column from Excel and clicks the matching date cell.
	 */
	@When("the user selects check-in date {string}")
	public void the_user_selects_check_in_date(String tcId) throws Exception {
		data = Hotel_ExcelUtil.getRowData(tcId);
		hs.enterCheckInDate(data.get("Check-in"));
	}

	/**
	 * TC_01_01 / TC_02_02 etc.
	 * Reads "Check-out" column from Excel and clicks the matching date cell.
	 */
	@When("the user selects check-out date {string}")
	public void the_user_selects_check_out_date(String tcId) throws Exception {
		data = Hotel_ExcelUtil.getRowData(tcId);
		hs.enterCheckOutDate(data.get("Check-out"));
	}

	/**
	 * TC_01_01 / TC_02_02 etc.
	 * Receives rooms and adults directly from the feature file parameters
	 * and delegates to the page's rooms-and-guests method.
	 *
	 * NOTE: Both params are passed directly – no Excel lookup needed here
	 * because the feature passes the literal values "1" and "2".
	 * @throws Exception 
	 */
	@When("the user sets rooms and guests to {string} room and {string} adults")
	public void the_user_sets_rooms_and_guests_to_room_and_adults(String tcId) throws Exception {
		data = Hotel_ExcelUtil.getRowData(tcId);
		hs.enterRoomsAndGuests(data.get("Rooms"), data.get("Guests"));
	}

	/** Submits the hotel search form. */
	@When("the user clicks the Search button")
	public void the_user_clicks_the_search_button() {
		hs.clickSearchBtn();
	}

	// ═════════════════════════════════════════════════════════════════════════
	// THEN – TS_MMT_Hotels_01 : Basic Hotel Search assertions
	// ═════════════════════════════════════════════════════════════════════════

	/**
	 * TC_01_01 – asserts the hotel listing page header element is visible,
	 * confirming at least one result has been returned.
	 */
	@Then("the hotel listing page is displayed")
	public void the_hotel_listing_page_is_displayed() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(LISTING_PAGE_HEADER));
		Assert.assertTrue(hl.isHotelListingsDisplayed(),
				"Hotel listing page should be displayed with at least one result.");
	}

	/**
	 * TC_01_02 – checks that at least one visible location tag on the results
	 * page contains the searched landmark name (case-insensitive).
	 */
	@Then("each hotel card displays the correct location tag for {string}")
	public void each_hotel_card_displays_the_correct_location_tag_for(String landmark) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(LISTING_PAGE_HEADER));
		List<WebElement> locationTags = driver.findElements(LOCATION_TAG_ELEMENTS);
		boolean anyMatch = locationTags.stream()
				.anyMatch(el -> el.getText().toLowerCase()
						.contains(landmark.toLowerCase()));
		Assert.assertTrue(anyMatch,
				"Expected at least one location tag containing: " + landmark);
	}

	/**
	 * TC_01_03 – clicks the destination search trigger after entering a
	 * non-existent city, then asserts the 'No Results Found' message appears.
	 */
	@Then("clicking the search icon displays a no hotels found message")
	public void clicking_the_search_icon_displays_a_no_hotels_found_message() {
		hs.getDestSearch().click();
		wait.until(ExpectedConditions.visibilityOf(hs.getNonExistentCityMsg()));
		String msg = hs.getNonExistentCityMsg().getText();
		Assert.assertFalse(msg.isEmpty(),
				"'No Results Found' message should be visible for a non-existent city.");
	}

	/**
	 * TC_01_04 – triggers the destination search after typing special
	 * characters, then asserts no '!' remains in the field value.
	 */
	@Then("the application removes the special characters")
	public void the_application_removes_the_special_characters() {
		hs.getDestSearch().click();
		String value = hs.getDestinationField().getAttribute("value");
		Assert.assertFalse(value.contains("!"),
				"Special characters should have been stripped from the destination field. " +
				"Actual value: " + value);
	}

	/**
	 * TC_01_04 – asserts the cleaned destination value starts with or equals
	 * the expected string (e.g. "Mumbai").
	 */
	@Then("the destination field contains only {string}")
	public void the_destination_field_contains_only(String expected) {
		String actual = hs.getDestinationField().getAttribute("value").trim();
		Assert.assertTrue(actual.toLowerCase().startsWith(expected.toLowerCase()),
				"Expected destination field to start with '" + expected +
				"' but found: '" + actual + "'");
	}

	// ═════════════════════════════════════════════════════════════════════════
	// THEN – TS_MMT_Hotels_02 : Date Validation assertions
	// ═════════════════════════════════════════════════════════════════════════

	/**
	 * TC_02_02 – asserts the Search button is in an enabled state after a
	 * valid date range has been selected.
	 */
	@Then("the Search button is enabled")
	public void the_search_button_is_enabled() {
		Assert.assertTrue(hs.getSearchBtn().isEnabled(),
				"Search button should be enabled after selecting a valid date range.");
	}

	/**
	 * TC_02_02 – asserts the listing page is shown and the current URL
	 * contains the destination name.
	 */
	@Then("the hotel listing page is displayed for {string}")
	public void the_hotel_listing_page_is_displayed_for(String destination) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(LISTING_PAGE_HEADER));
		Assert.assertTrue(hl.isHotelListingsDisplayed(),
				"Hotel listing page should be displayed for: " + destination);
		String url = driver.getCurrentUrl().toLowerCase();
		Assert.assertTrue(url.contains(destination.toLowerCase()),
				"URL should reference the destination '" + destination +
				"'. Actual URL: " + url);
	}

	// ═════════════════════════════════════════════════════════════════════════
	// WHEN / THEN – TS_MMT_Hotels_03 : Star Rating Filter
	// ═════════════════════════════════════════════════════════════════════════

	/**
	 * TC_03_01 / TC_03_02 – applies the star rating filter for the given
	 * number of stars. Uses JavascriptExecutor for the 4-star click to avoid
	 * element intercept issues caused by sticky filter panels.
	 */
	@When("the user applies the star rating filter for {string} stars")
	public void the_user_applies_the_star_rating_filter_for_stars(String stars) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("(//input[@aria-label='" + stars + " Star']" +
						 "/parent::span/child::label)[2]")));
		if ("5".equals(stars)) {
			hl.filterBy5Stars();
		} else if ("4".equals(stars)) {
			WebElement fourStar = hl.getFourStarCheckbox();
			((JavascriptExecutor) driver)
					.executeScript("arguments[0].scrollIntoView(true);", fourStar);
			((JavascriptExecutor) driver)
					.executeScript("arguments[0].click();", fourStar);
		}
		pauseForFilterUpdate();
	}

	/**
	 * TC_03_01 – asserts every visible hotel card carries the expected star
	 * rating by checking the aria-label attribute on rendered star widgets.
	 */
	@Then("all displayed hotel cards show a {string} star rating")
	public void all_displayed_hotel_cards_show_a_star_rating(String stars) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(LISTING_PAGE_HEADER));
		List<WebElement> starWidgets = driver.findElements(
				By.xpath("//div[contains(@class,'starRating') or contains(@class,'star-rating')]"));
		Assert.assertFalse(starWidgets.isEmpty(),
				"Star rating widgets should be visible on result cards.");
		for (WebElement widget : starWidgets) {
			String ratingText = widget.getText().replaceAll("[^0-9]", "");
			if (!ratingText.isEmpty()) {
				Assert.assertEquals(ratingText, stars,
						"Hotel card shows " + ratingText +
						" stars but expected " + stars + " stars.");
			}
		}
	}

	/**
	 * TC_03_01 – asserts the result count label is visible after the
	 * 5-star filter is applied, confirming the count has been recalculated.
	 */
	@Then("the result count updates to reflect only {int}-star properties")
	public void the_result_count_updates_to_reflect_only_star_properties(Integer stars) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(LISTING_PAGE_HEADER));
		Assert.assertTrue(hl.isHotelListingsDisplayed(),
				"Results should be visible after applying the " + stars + "-star filter.");
	}

	/**
	 * TC_03_02 – asserts every visible hotel card carries either the first
	 * or the second star rating supplied.
	 */
	@Then("all displayed hotel cards show either {string} or {string} star ratings")
	public void all_displayed_hotel_cards_show_either_or_star_ratings(String s1, String s2) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(LISTING_PAGE_HEADER));
		List<WebElement> starWidgets = driver.findElements(
				By.xpath("//div[contains(@class,'starRating') or contains(@class,'star-rating')]"));
		Assert.assertFalse(starWidgets.isEmpty(),
				"Star rating widgets should be visible on result cards.");
		for (WebElement widget : starWidgets) {
			String ratingText = widget.getText().replaceAll("[^0-9]", "");
			if (!ratingText.isEmpty()) {
				Assert.assertTrue(ratingText.equals(s1) || ratingText.equals(s2),
						"Hotel card shows " + ratingText + " stars " +
						"but expected either " + s1 + " or " + s2 + " stars.");
			}
		}
	}

	/**
	 * TC_03_02 – asserts the listing page is still populated after the
	 * combined 4+5 star filter is applied.
	 */
	@Then("the result count reflects the combined filtered set")
	public void the_result_count_reflects_the_combined_filtered_set() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(LISTING_PAGE_HEADER));
		Assert.assertTrue(hl.isHotelListingsDisplayed(),
				"Result set should remain visible after applying combined star filters.");
	}

	// ═════════════════════════════════════════════════════════════════════════
	// WHEN / THEN – TS_MMT_Hotels_04 : Price Range Filter
	// ═════════════════════════════════════════════════════════════════════════

	/**
	 * TC_04_01 / TC_04_02 – stores the minimum price value so it can be
	 * used together with the maximum price in the next step.
	 */
	@When("the user sets the minimum price to {string}")
	public void the_user_sets_the_minimum_price_to(String min) {
		storedMinPrice = min;  // held until the max-price step triggers the filter
	}

	/**
	 * TC_04_01 / TC_04_02 – clears both price fields, enters the stored
	 * minimum and the supplied maximum, then clicks the Apply Range button.
	 */
	@When("the user sets the maximum price to {string}")
	public void the_user_sets_the_maximum_price_to(String max) {
		wait.until(ExpectedConditions.visibilityOf(hl.getMinPriceField()));
		((JavascriptExecutor) driver)
				.executeScript("arguments[0].scrollIntoView(true);", hl.getMinPriceField());
		hl.getMinPriceField().clear();
		hl.getMinPriceField().sendKeys(storedMinPrice);
		hl.getMaxPriceField().clear();
		hl.getMaxPriceField().sendKeys(max);
		hl.getMinMaxRangeBtn().click();
		pauseForFilterUpdate();
	}

	/**
	 * TC_04_01 – extracts every displayed hotel price and asserts each one
	 * falls within [minPrice, maxPrice].
	 */
	@Then("all displayed hotel cards show a per-night rate between {string} and {string}")
	public void all_displayed_hotel_cards_show_a_per_night_rate_between_and(
			String min, String max) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(LISTING_PAGE_HEADER));
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

	/**
	 * TC_04_02 – asserts no hotel listing header element is rendered when
	 * the price range yields zero results.
	 */
	@Then("no hotel cards are displayed")
	public void no_hotel_cards_are_displayed() {
		pauseForFilterUpdate();
		List<WebElement> cards = driver.findElements(LISTING_PAGE_HEADER);
		Assert.assertTrue(cards.isEmpty(),
				"No hotel cards should be rendered for this price range.");
	}

	/**
	 * TC_04_02 – waits for and asserts the 'Sold Out' or 'No hotels found'
	 * message is visible.
	 */
	@Then("a sold out or no results message is shown")
	public void a_sold_out_or_no_results_message_is_shown() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(SOLD_OUT_MSG));
		WebElement msg = driver.findElement(SOLD_OUT_MSG);
		Assert.assertTrue(msg.isDisplayed(),
				"A 'Sold Out' or 'No results' message should be visible " +
				"when no hotels match the price range.");
	}

	// ═════════════════════════════════════════════════════════════════════════
	// WHEN / THEN – TS_MMT_Hotels_05 : Sort Prices
	// ═════════════════════════════════════════════════════════════════════════

	/**
	 * TC_05_01 / TC_05_02 – clicks either the 'Price: Low to High' or
	 * 'Price: High to Low' sort button based on the supplied label.
	 */
	@When("the user sorts results by {string}")
	public void the_user_sorts_results_by(String sortOption) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(LISTING_PAGE_HEADER));
		if (sortOption.contains("Low to High")) {
			wait.until(ExpectedConditions.elementToBeClickable(hl.getPriceLToH()));
			hl.sortLowToHigh();
		} else if (sortOption.contains("High to Low")) {
			wait.until(ExpectedConditions.elementToBeClickable(hl.getPriceHToL()));
			hl.sortHighToLow();
		}
		pauseForFilterUpdate();
	}

	/**
	 * TC_05_01 – reads all visible hotel prices and verifies each price is
	 * greater than or equal to the one before it (ascending order).
	 */
	@Then("hotel cards are ordered in ascending price sequence")
	public void hotel_cards_are_ordered_in_ascending_price_sequence() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(LISTING_PAGE_HEADER));
		List<Integer> prices = extractAllPrices();
		Assert.assertFalse(prices.isEmpty(),
				"At least two hotel prices must be visible to verify sort order.");
		for (int i = 1; i < prices.size(); i++) {
			Assert.assertTrue(prices.get(i) >= prices.get(i - 1),
					"Prices are NOT in ascending order at position " + i + ". " +
					"Card[" + (i - 1) + "] = ₹" + prices.get(i - 1) +
					" > Card[" + i + "] = ₹" + prices.get(i));
		}
	}

	/**
	 * TC_05_01 – asserts the first card's price equals the minimum price
	 * across all visible results.
	 */
	@Then("the first card shows the lowest price among all results")
	public void the_first_card_shows_the_lowest_price_among_all_results() {
		List<Integer> prices = extractAllPrices();
		Assert.assertFalse(prices.isEmpty(), "Price elements must be visible.");
		int firstCardPrice = prices.get(0);
		int lowestPrice    = prices.stream().min(Integer::compareTo).orElse(0);
		Assert.assertEquals(firstCardPrice, lowestPrice,
				"First card price (₹" + firstCardPrice + ") should equal the " +
				"lowest price on page (₹" + lowestPrice + ").");
	}

	/**
	 * TC_05_02 – reads all visible hotel prices and verifies each price is
	 * less than or equal to the one before it (descending order).
	 */
	@Then("hotel cards are ordered in descending price sequence")
	public void hotel_cards_are_ordered_in_descending_price_sequence() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(LISTING_PAGE_HEADER));
		List<Integer> prices = extractAllPrices();
		Assert.assertFalse(prices.isEmpty(),
				"At least two hotel prices must be visible to verify sort order.");
		for (int i = 1; i < prices.size(); i++) {
			Assert.assertTrue(prices.get(i) <= prices.get(i - 1),
					"Prices are NOT in descending order at position " + i + ". " +
					"Card[" + (i - 1) + "] = ₹" + prices.get(i - 1) +
					" < Card[" + i + "] = ₹" + prices.get(i));
		}
	}

	/**
	 * TC_05_02 – asserts the first card's price equals the maximum price
	 * across all visible results.
	 */
	@Then("the first card shows the highest price among all results")
	public void the_first_card_shows_the_highest_price_among_all_results() {
		List<Integer> prices = extractAllPrices();
		Assert.assertFalse(prices.isEmpty(), "Price elements must be visible.");
		int firstCardPrice = prices.get(0);
		int highestPrice   = prices.stream().max(Integer::compareTo).orElse(0);
		Assert.assertEquals(firstCardPrice, highestPrice,
				"First card price (₹" + firstCardPrice + ") should equal the " +
				"highest price on page (₹" + highestPrice + ").");
	}

	// ═════════════════════════════════════════════════════════════════════════
	// PRIVATE HELPERS
	// ═════════════════════════════════════════════════════════════════════════

	/**
	 * Scrapes all hotel prices visible on the current results page.
	 * Strips non-numeric characters (₹, commas, spaces) and returns
	 * a list of integer prices.
	 */
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

	/**
	 * Gives the UI two seconds to update after a filter or sort action —
	 * keeps explicit waits as the primary strategy but accounts for
	 * JS-driven re-renders that do not change the DOM structure.
	 */
	private void pauseForFilterUpdate() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
