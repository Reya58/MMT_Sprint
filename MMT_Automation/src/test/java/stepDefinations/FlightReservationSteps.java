package stepDefinations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.Assert;

import base.BaseClass;
import base.PageManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import utils.DriverFactory;

public class FlightReservationSteps extends BaseClass {
	private PageManager pageManager;

	@Before
	public void setUp() {
		DriverFactory.initDriver();
		pageManager = new PageManager(getDriver());
	}

	@Given("the user is on the flight search page")
	public void the_user_is_on_the_flight_search_page() {
		getDriver().get("https://www.makemytrip.com/flights/");
		pageManager.getFlightSearchPage().handleInterruptions();
	}

	@When("the user enters {string} in the From field")
	public void the_user_enters_in_the_from_field(String city) throws InterruptedException {
		pageManager.getFlightSearchPage().setFromCity(city);
	}

	@Then("matching city suggestions should be displayed")
	public void matching_city_suggestions_should_be_displayed() {
		Assert.assertTrue(pageManager.getFlightSearchPage().isSuggestionDisplayed());
	}

	@Then("the suggestions list should not be empty")
	public void the_suggestions_list_should_not_be_empty() {

		Assert.assertTrue("Suggestions are empty but expected to be present",
				pageManager.getFlightSearchPage().isSuggestionDisplayed());
	}

	@Then("no city suggestions should be displayed")
	public void no_city_suggestions_should_be_displayed() {
		Assert.assertFalse("Suggestions was expected to be empty but suggestion is present",
				pageManager.getFlightSearchPage().isSuggestionDisplayed());
	}

	@Then("the recent searches section should be visible")
	public void the_recent_searches_section_should_be_visible() {
		Assert.assertFalse(pageManager.getFlightSearchPage().isSuggestionDisplayed());
	}

	@When("the user enters {string} in the To field")
	public void the_user_enters_in_the_to_field(String city) throws InterruptedException {
		pageManager.getFlightSearchPage().setToCity(city);
	}

	@When("the user selects {string} as the From")
	public void the_user_selects_as_the_origin(String frmCity) throws InterruptedException {
		pageManager.getFlightSearchPage().setFromCity(frmCity);
		pageManager.getFlightSearchPage().selectCity(frmCity);

		String actualCity = pageManager.getFlightSearchPage().getSelectedFromCity();

		Assert.assertTrue("Expected city not selected. Actual: " + actualCity, actualCity.contains(frmCity));
	}

	@When("the user selects {string} as the To")
	public void the_user_selects_as_the_destination(String toCity) throws InterruptedException {
		pageManager.getFlightSearchPage().setToCity(toCity);
		pageManager.getFlightSearchPage().selectCity(toCity);

		String actualCity = pageManager.getFlightSearchPage().getSelectedToCity();

		Assert.assertTrue("Expected city not selected. Actual: " + actualCity, actualCity.contains(toCity));
	}

	@When("the user attempts to search for flights")
	public void the_user_attempts_to_search_for_flights() {
		pageManager.getFlightSearchPage().search();
		Assert.assertTrue(pageManager.getFlightResultPage().isOnSearchResultPage());
	}

	@Then("a validation error indicating identical origin and destination should be displayed")
	public void a_validation_error_indicating_identical_origin_and_destination_should_be_displayed() {
		// Write code here that turns the phrase above into concrete actions
		Assert.assertTrue(pageManager.getFlightSearchPage().isErrorMessegeDisplayed());
	}

	@Then("the flight search should not be executed")
	public void the_flight_search_should_not_be_executed() {
		// Write code here that turns the phrase above into concrete actions
		pageManager.getFlightSearchPage().search();
		Assert.assertFalse(pageManager.getFlightResultPage().isOnSearchResultPage());
	}

	@When("the user opens the departure date picker")
	public void the_user_opens_the_departure_date_picker() {
		pageManager.getFlightSearchPage().clickDate();
	}

	@Then("the user should not be able to select a past date")
	public void the_user_should_not_be_able_to_select_a_past_date() throws InterruptedException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy", Locale.ENGLISH);

		String yesterday = LocalDate.now().minusDays(1).format(formatter);

		// 2 days back
		String twoDaysBack = LocalDate.now().minusDays(2).format(formatter);

		Assert.assertTrue("Yesterday should be disabled", pageManager.getFlightSearchPage().selectDate(yesterday));

		Assert.assertTrue("Two days back should be disabled",
				pageManager.getFlightSearchPage().selectDate(twoDaysBack));
	}

	private String expectedDate;

	@When("the user selects a date {string} days from today")
	public void the_user_selects_a_date_days_from_today(String daysToFuture) throws InterruptedException {
		int daysInFuture = Integer.valueOf(daysToFuture);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy", Locale.ENGLISH);

		// 2 days forward
		expectedDate = LocalDate.now().plusDays(daysInFuture).format(formatter);

		pageManager.getFlightSearchPage().selectDate(expectedDate);
	}

	@Then("the selected date should be displayed in the departure date field")
	public void the_selected_date_should_be_displayed_in_the_departure_date_field() throws InterruptedException {
		String actualDate = pageManager.getFlightSearchPage().getSelectedDate();

		Assert.assertTrue("Expected: " + expectedDate + " but got: " + actualDate, actualDate.contains(expectedDate));
	}

	@Given("the user searches flights from {string} to {string}")
	public void the_user_searches_flights_from_to(String frmCity, String toCity) throws InterruptedException {
		pageManager.getFlightSearchPage().setFromCity(frmCity);
		pageManager.getFlightSearchPage().selectCity(frmCity);
		pageManager.getFlightSearchPage().setToCity(toCity);
		pageManager.getFlightSearchPage().selectCity(toCity);
	}

	@Given("user set flight date to be {string}")
	public void user_set_flight_date_to_be(String date) throws InterruptedException {
		pageManager.getFlightSearchPage().selectDate(date);
	}

	@When("the flight search is executed")
	public void the_flight_search_is_executed() {
		pageManager.getFlightSearchPage().search();
	}

	@Then("avaliable flights are shown to user")
	public void avaliable_flights_are_shown_to_user() {
		Assert.assertTrue("User is not on search results page",
				pageManager.getFlightResultPage().isOnSearchResultPage());
	}

//	@Given("the user searches for invalid flights route from {string} to {string} on a future date")
//	public void the_user_searches_for_flights_from_to_on_a_future_date(String fromCity, String toCity) {
//	    
//	}
//
//	@Then("one or more flight results should be displayed")
//	public void one_or_more_flight_results_should_be_displayed() {
//	    // Write code here that turns the phrase above into concrete actions
//	    throw new io.cucumber.java.PendingException();
//	}
//
//	@Then("a no-results message should be displayed")
//	public void a_no_results_message_should_be_displayed() {
//	    // Write code here that turns the phrase above into concrete actions
//	    throw new io.cucumber.java.PendingException();
//	}
//
//	@Then("no flight results should be shown")
//	public void no_flight_results_should_be_shown() {
//	    // Write code here that turns the phrase above into concrete actions
//	    throw new io.cucumber.java.PendingException();
//	}
//
//	@Given("a user has searched for flights from {string} to {string} on {string}")
//	public void a_user_has_searched_for_flights_from_to_on(String string, String string2, String string3) {
//	    // Write code here that turns the phrase above into concrete actions
//	    throw new io.cucumber.java.PendingException();
//	}
//
//	@When("the flight results are displayed")
//	public void the_flight_results_are_displayed() {
//	    // Write code here that turns the phrase above into concrete actions
//	    throw new io.cucumber.java.PendingException();
//	}
//
//	@Then("the results should be sorted in ascending order of price")
//	public void the_results_should_be_sorted_in_ascending_order_of_price() {
//	    // Write code here that turns the phrase above into concrete actions
//	    throw new io.cucumber.java.PendingException();
//	}
//
//	@Then("the first result should have the lowest price")
//	public void the_first_result_should_have_the_lowest_price() {
//	    // Write code here that turns the phrase above into concrete actions
//	    throw new io.cucumber.java.PendingException();
//	}

	@After
	public void tearDown() {
		DriverFactory.quitDriver();
	}

}