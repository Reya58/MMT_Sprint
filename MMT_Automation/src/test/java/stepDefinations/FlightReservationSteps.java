package stepDefinations;

import java.util.List;
import org.junit.Assert;

import base.BaseClass;
import base.PageManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.FlightResultPage;
import pages.FlightSearchPage;
import pages.FlightTravellerDetailsPage;
import utils.DriverFactory;

public class FlightReservationSteps extends BaseClass {
	private PageManager pageManager;

	@Before
	public void setUp() {
		DriverFactory.initDriver();
		pageManager = new PageManager(DriverFactory.getDriver());
		DriverFactory.getDriver().get("https://www.makemytrip.com/flights/");
	}

//
	@Given("the user is on the flight search page")
	public void the_user_is_on_the_flight_search_page() {
		pageManager.getFlightSearchPage().handleInterruptions();
	}

	@Given("the user searches for flights from {string} to {string}")
	public void the_user_searches_for_flights_from_to(String frmCity, String toCity) throws InterruptedException {
		pageManager.getFlightSearchPage().setFromCity(frmCity);
		pageManager.getFlightSearchPage().selectCity(frmCity);
		pageManager.getFlightSearchPage().setToCity(toCity);
		pageManager.getFlightSearchPage().selectCity(toCity);
	}

	@Given("the user selects departure date {string}")
	public void the_user_selects_departure_date(String date) throws InterruptedException {
		pageManager.getFlightSearchPage().selectDate(date);
	}

	@When("the user executes the flight search")
	public void the_user_executes_the_flight_search() {
		pageManager.getFlightSearchPage().search();
	}

	@Then("available flights are displayed")
	public void available_flights_are_displayed() {
		pageManager.getFlightResultPage().handleInterruptions();
		Assert.assertTrue("User is not on search results page",
				pageManager.getFlightResultPage().isOnSearchResultPage());
		Assert.assertTrue("No Flights Avaliable For this Route!!!",
				pageManager.getFlightResultPage().isFlightsAvailable());
	}

	@When("the user selects a flight")
	public void the_user_selects_a_flight() throws InterruptedException {
		pageManager.getFlightResultPage().handleInterruptions();
		pageManager.getFlightResultPage().selectFirstFlight();
		pageManager.getFlightResultPage().selectCheapestFareType();
		Thread.sleep(5000);
	}

	@When("enters valid traveller details {string}, {string}, {string}, {string}, {string}, {string}")
	public void enters_valid_traveller_details(String fname, String lname, String gender, String mobile, String email,
			String state) throws InterruptedException {
//		pageManager.getFlightTravellerDetailsPage();
//		Thread.sleep(10000);
		FlightTravellerDetailsPage travelDetailsPage = pageManager.getFlightTravellerDetailsPage();
		Thread.sleep(7000);
		travelDetailsPage.selectNoInsurance();
//		System.out.println("hihihi");
		travelDetailsPage.addTraveller();
		travelDetailsPage.enterFirstName(fname);
		travelDetailsPage.enterLastName(lname);
		travelDetailsPage.setGender(gender);
		travelDetailsPage.setCountryCode("91");
		travelDetailsPage.setContryCodeBookingDetails("91");
		travelDetailsPage.setMobileBookingDetails(mobile);
		travelDetailsPage.setEmailBookingDetails(email);
		travelDetailsPage.setState(state);
		travelDetailsPage.saveGSTDetails();

		travelDetailsPage.clickContinue();
		Thread.sleep(6000);
		travelDetailsPage.addressTripProtectionPopUp();
		travelDetailsPage.addressZeroCancelationPopup();
		travelDetailsPage.addressFlexiFlyPopups();
		travelDetailsPage.reviewTravelerDetails();

//		pageManager.getFlightTravellerDetailsPage().dismissAllPopups();
//		addressFlexiFlyPopups();
//		ftdpg.addressEmergencySeatPopUp();

//		
//		
//		

		if (!travelDetailsPage.takeSeatReccomended()) {
			System.out.println("Seat Recommendation Popup didn't appeared ");
			travelDetailsPage.selectSeat();
		}

		travelDetailsPage.clickContinue();
		Thread.sleep(6000);
		travelDetailsPage.addressEmergencySeatPopUp();

		travelDetailsPage.addMeal();

		travelDetailsPage.clickContinue();

		travelDetailsPage.setCabs();
		Thread.sleep(3000);
		travelDetailsPage.clickContinue();
	}

	@When("completes the booking")
	public void completes_the_booking() throws InterruptedException {
		pageManager.getFlightTravellerDetailsPage().clickOnProceedToPayment();
		Thread.sleep(4000);
//		pageManager.getFlightTravellerDetailsPage().dismissAllPopups();
		pageManager.getFlightTravellerDetailsPage().addressTripSummaryPopup();
		Thread.sleep(7000);
		pageManager.getFlightTravellerDetailsPage().addressUpgradePopups();
		Thread.sleep(7000);
		pageManager.getFlightTravellerDetailsPage().dismissNoUpgradePopUp();
//		pageManager.getFlightTravellerDetailsPage().dismissAllPopups();
//		Thread.sleep(150000);
//		pageManager.getFlightTravellerDetailsPage().dismissAllPopups();
//		System.out.println("it tfa");
//		Thread.sleep(300000);
//		pageManager.getFlightTravellerDetailsPage().addressTripSummaryPopup();
//		
	}

	@Then("the booking is successful")
	public void the_booking_is_successful() {
		Assert.assertTrue("Boking was Failed", pageManager.getFlightPaymenyPage().isOnPaymentPage());
	}

	String frmCityRoundTrip;
	String toCityRoundTrip;

	@Given("the user searches for round-trip flights from {string} to {string}")
	public void the_user_searches_for_round_trip_flights_from_to(String frmCity, String toCity)
			throws InterruptedException {
		FlightSearchPage searchPage = pageManager.getFlightSearchPage();
		searchPage.selectRoundTrip();

		searchPage.setFromCity(frmCity);
		searchPage.selectCity(frmCity);
		searchPage.setToCity(toCity);
		searchPage.selectCity(toCity);

		frmCityRoundTrip = searchPage.getSelectedFromCity();
		toCityRoundTrip = searchPage.getSelectedToCity();
		toCityRoundTrip = toCityRoundTrip.substring(0, toCityRoundTrip.indexOf(" "));
	}

	@Given("selects departure date {string} and return date {string}")
	public void selects_departure_date_and_return_date(String deptDate, String returnDate) throws InterruptedException {
		pageManager.getFlightSearchPage().selectDate(deptDate);
		pageManager.getFlightSearchPage().selectDate(returnDate);
	}

	@Then("onward and return flights are displayed")
	public void onward_and_return_flights_are_displayed() throws InterruptedException {
		FlightResultPage flightResultPage = pageManager.getFlightResultPage();
		pageManager.getFlightResultPage().handleInterruptions();

		Assert.assertTrue("FromCity Of Forward Journey Is Different from Searched FromCity",
				flightResultPage.fromCityForwardTripOfRoundTrip().contains(frmCityRoundTrip));

		Assert.assertTrue("ToCity Of Forward Journey Is Different from Searched ToCity",
				toCityRoundTrip.contains(flightResultPage.toCityForwardTripOfRoundTrip()));

		System.out.println(flightResultPage.fromCityReturnTripOfRoundTrip());
		System.out.println(toCityRoundTrip);
		Assert.assertTrue("FromCity Of Return Journey Is Different from Searched ToCity",
				flightResultPage.fromCityReturnTripOfRoundTrip().contains(toCityRoundTrip));

		Assert.assertTrue("ToCity Of Return Journey Is Different from Searched FromCity",
				frmCityRoundTrip.contains(flightResultPage.toCityReturnTripOfRoundTrip()));
	}

	@When("the user selects flights for both journeys")
	public void the_user_selects_flights_for_both_journeys() {
		pageManager.getFlightResultPage().bookRoundTrip();
		pageManager.getFlightResultPage().selectFareTypeRoundTrip();
	}

	@When("enters valid traveller details")
	public void enters_valid_traveller_details() throws InterruptedException {
		FlightTravellerDetailsPage detailsPage = pageManager.getFlightTravellerDetailsPage();
		Thread.sleep(10000);
		detailsPage.selectNoInsurance();
		detailsPage.addTraveller();
//		
		detailsPage.enterFirstName("Md");
		detailsPage.enterLastName("Shadab");
		detailsPage.setGender("Male");
		detailsPage.setCountryCode("91");
		detailsPage.setContryCodeBookingDetails("91");
		detailsPage.setMobileBookingDetails("8444866632");
		detailsPage.setEmailBookingDetails("shadab@gmail.com");
		detailsPage.setState("West Bengal");
		detailsPage.saveGSTDetails();

		detailsPage.clickContinue();
		Thread.sleep(6000);
		detailsPage.addressTripProtectionPopUp();
		detailsPage.addressZeroCancelationPopup();
		detailsPage.addressFlexiFlyPopups();
		detailsPage.reviewTravelerDetails();

		if (!detailsPage.takeSeatReccomended()) {
			System.out.println("not reccomendation");
			detailsPage.selectSeat();
		}

		detailsPage.clickContinue();

		if (!detailsPage.takeSeatReccomended()) {
			System.out.println("not reccomendation");
			detailsPage.selectSeat();
		}
		detailsPage.clickContinue();
//		Thread.sleep(1000);

		Thread.sleep(6000);
		detailsPage.addressEmergencySeatPopUp();

		detailsPage.addMeal();
		detailsPage.clickContinue();

//		detailsPage.addMeal();

//		detailsPage.clickContinue();

		detailsPage.setCabs();
		Thread.sleep(3000);
		detailsPage.clickContinue();
		Thread.sleep(3000);
//		detailsPage.clickContinue();
	}

	@Given("the user searches for flights")
	public void the_user_searches_for_flights() throws InterruptedException {
		pageManager.getFlightSearchPage().setFromCity("Del");
		pageManager.getFlightSearchPage().selectCity("New Delhi");
		pageManager.getFlightSearchPage().setToCity("Beng");
		pageManager.getFlightSearchPage().selectCity("Bengaluru");

		pageManager.getFlightSearchPage().selectDate("Sat May 30 2026");

		pageManager.getFlightSearchPage().search();
	}

	@When("adds meals and extra baggage")
	public void adds_meals_and_extra_baggage() throws InterruptedException {
		FlightTravellerDetailsPage detailsPage = pageManager.getFlightTravellerDetailsPage();
		Thread.sleep(7000);
		detailsPage.addExtraBaggage();
		Thread.sleep(7000);
	}

	@Then("the user selects the cheapest available flight")
	public void the_user_selects_the_cheapest_available_flight() {
		pageManager.getFlightResultPage().selectCheapestTab();
		pageManager.getFlightResultPage().selectFirstFlight();
		pageManager.getFlightResultPage().selectCheapestFareType();
	}

	@Then("the user filters flights by airline {string}")
	public void the_user_filters_flights_by_airline(String airline) {
		pageManager.getFlightResultPage().selectAOption("Airlines", airline);
	}

	@Then("selects a flight after filter")
	public void selects_a_flight_from_that_airline() throws InterruptedException {
		Assert.assertTrue("No Flight Available After Filter",
				pageManager.getFlightResultPage().isFlightAvailabeAfterFilter());

		pageManager.getFlightResultPage().selectFirstFlight();
		pageManager.getFlightResultPage().selectFareByMMT();
	}

	@Then("the airline is {string}")
	public void the_airline_is(String airline) {
//		Assert.assertTrue("Booking Failed",pageManager.getFlightPaymenyPage().isOnPaymentPage());
		List<String> airlines = pageManager.getFlightTravellerDetailsPage().getAirlines();
//
//		Assert.assertNotNull("Airlines list is null on Traveller Details page",airlines);
//		Assert.assertFalse("Airlines list is empty on Traveller Details page",airlines.isEmpty());

		Assert.assertEquals("Different Airline Selected", airline, airlines.get(0));
	}

	@When("the user filters flights by airlines {string} and {string}")
	public void the_user_filters_flights_by_airlines_and(String airline1, String airline2) {
		pageManager.getFlightResultPage().handleInterruptions();
		pageManager.getFlightResultPage().selectAOption("Airlines", airline1);
		pageManager.getFlightResultPage().selectAOption("Airlines", airline2);
	}

	@Then("selected flight belongs to {string},{string} one of the chosen airlines")
	public void selected_flight_belongs_to_one_of_the_chosen_airlines(String airline1, String airline2) {

		List<String> actualAirlines = pageManager.getFlightTravellerDetailsPage().getAirlines();

		Assert.assertNotNull("Airlines list is null", actualAirlines);
		Assert.assertFalse("Airlines list is empty", actualAirlines.isEmpty());

		for (String actualAirline : actualAirlines) {
			Assert.assertTrue(
					"Unexpected airline found: " + actualAirline + " | Expected: " + airline1 + " or " + airline2,
					actualAirline.equals(airline1) || actualAirline.equals(airline2));
		}
	}

	@Given("the user navigates to flight status page")
	public void the_user_navigates_to_flight_status_page() throws InterruptedException {
		Thread.sleep(3000);
		pageManager.getFlightSearchPage().trackFlight();
	}
	@Given("is on Flight Status By Flight tab")
	public void is_on_Flight_Status_By_Flight_tab() throws InterruptedException {
		Thread.sleep(6000);
		pageManager.getFlightSearchPage().setFlightStatusByNumber();
	}
	@Given("is on Flight Status By Route tab")
	public void is_on_Flight_Status_By_Route_tab() throws InterruptedException {
		Thread.sleep(6000);
		pageManager.getFlightSearchPage().setFlightStatusByRoute();
		Thread.sleep(6000);
	}

	@When("the user enters flight number {string}")
	public void the_user_enters_flight_number(String flightNumber) {
		pageManager.getFlightSearchPage().setFlightStatusByNumber();
		pageManager.getFlightSearchPage().setFlightNumber(flightNumber);
		pageManager.getFlightSearchPage().selectDateFlightTracker("Fri Apr 03 2026");
	}

	@When("submits the request")
	public void submits_the_request() throws InterruptedException {
//		Thread.sleep(600000);
		pageManager.getFlightSearchPage().search();
	}

	@Then("the current flight status is displayed")
	public void the_current_flight_status_is_displayed() {
		Assert.assertTrue("Status isn't fetched", pageManager.getFlightSearchPage().flightStatusFetched());
	}

	@When("the user enters source {string} and destination {string}")
	public void the_user_enters_source_and_destination(String frmCity, String toCity) {
		pageManager.getFlightSearchPage().setFromCityFlightStatusByRoute(frmCity);
		pageManager.getFlightSearchPage().setToCityFlightStatusByRoute(toCity);
	}


//	all flights for "<from>","<to>"the route are displayed with status
	@Then("all flights for {string},{string} the route are displayed with status")
	public void all_flights_for_the_route_are_displayed_with_status(String frmCity, String toCity) {
		String actualFrmCity = pageManager.getFlightSearchPage().getFromCityFlightStatusByRoute();
		String actualToCity = pageManager.getFlightSearchPage().getToCityFlightStatusByRoute();

		actualFrmCity = actualFrmCity.trim();
		actualToCity = actualToCity.trim();
		frmCity = frmCity.trim();
		toCity = toCity.trim();

		Assert.assertTrue(
				"From city does not match expected route. Expected: " + frmCity + " but found: " + actualFrmCity,
				actualFrmCity.contains(frmCity));

		Assert.assertTrue("To city does not match expected route. Expected: " + toCity + " but found: " + actualToCity,
				actualToCity.contains(toCity));
	}

	@Given("is on Flight Status By Airport")
	public void is_on_Flight_Status_By_Airport() throws InterruptedException {
		Thread.sleep(6000);
		pageManager.getFlightSearchPage().setFlightStatusByAirport();
		Thread.sleep(6000);
	}

	@When("the user selects airport {string}")
	public void the_user_selects_airport(String airport) {
		pageManager.getFlightSearchPage().setAirportFlightStatusByAirport(airport);
	}

	@Then("all arrivals and departures of {string} are displayed with status")
	public void all_arrivals_and_departures_are_displayed_with_status(String airport) throws InterruptedException {
		Thread.sleep(6000);
		Assert.assertTrue("Arrivals and departures with status are not displayed",
				pageManager.getFlightSearchPage().flightStatusFetched());
		String actualAirport  =  pageManager.getFlightSearchPage().getAirportFlightStatusByAirport();
		Assert.assertTrue("Status for different Airport is Displayed",actualAirport.contains(airport));
	}

	@After
	public void tearDown() {
		DriverFactory.quitDriver();
	}

}