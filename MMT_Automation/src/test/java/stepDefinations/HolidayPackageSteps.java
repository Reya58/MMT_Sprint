package stepDefinations;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.HolidayPackagePage;

public class HolidayPackageSteps {
	
	WebDriver driver;
	HolidayPackagePage hpp;
	
	@Given("the user is on the MakeMyTrip Holiday Packges homepage")
	public void the_user_is_on_the_make_my_trip_holiday_packges_homepage() {
		driver = new EdgeDriver();
		driver.get("https://www.makemytrip.com/holidays-india/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().window().maximize();
		hpp = new HolidayPackagePage(driver);
	}

	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	@When("the user selects from city {string}, to city {string}, valid departure date and room and guest details")
	public void the_user_selects_from_city_to_city_valid_departure_date_and_room_and_guest_details(String string, String string2) {
	    hpp.selectFromCity(string);
	    hpp.selectToCity(string2);
	    hpp.selectDateAndGuests();
	}

	@When("the user clicks the Search button")
	public void the_user_clicks_the_search_button() {
	    hpp.search();
	}

	@When("the user clicks the First result from Search")
	public void the_user_clicks_the_first_result_from_search() {
	    hpp.clickSearchDetails();
	}

	@Then("the holiday package result locations should match {string} to {string}")
	public void the_holiday_package_result_locations_should_match_to(String string, String string2) {
	    Assert.assertTrue(hpp.validateSearchDetails(string, string2));
	}

	@Then("the data {string} is not accepted")
	public void the_data_is_not_accepted(String string) {
	    Assert.assertFalse(hpp.validateToCity(string));
	}

	@When("flight, price and star rating filters are added")
	public void flight_price_and_star_rating_filters_are_added() {
	    hpp.addFilters();
	}

	@Then("filtered holiday packages should be displayed")
	public void filtered_holiday_packages_should_be_displayed() {
		Assert.assertTrue(hpp.validateFilterDetails());
	}
	
	@When("the user clicks on Visa Free Packages")
	public void the_user_clicks_on_visa_free_packages() {
	    hpp.visaFreePackage();
	}

	@Then("the Visa Free Packages section should be displayed")
	public void the_visa_free_packages_section_should_be_displayed() {
	    Assert.assertTrue(hpp.validateList());
	}

	@When("the user selects a destination")
	public void the_user_selects_a_destination() {
	    hpp.visaFreeDestination();
	}

	@When("the user chooses a package")
	public void the_user_chooses_a_package() {
	    hpp.visaFreeSearch();
	}

	@Then("the correct holiday packages page should open")
	public void the_correct_holiday_packages_page_should_open() {
	    Assert.assertTrue(hpp.validateVisaFreeDetails());
	}

	@Then("the holiday packages page should open with correct UI")
	public void the_holiday_packages_page_should_open_with_correct_ui() {
		Assert.assertTrue(hpp.validateVisaFreeUI());
	}
	
	@When("the user clicks on Disney Cruise")
	public void the_user_clicks_on_disney_cruise() {
	    hpp.disneyCruise();
	}

	@Then("the Disney Cruise section should be displayed")
	public void the_disney_cruise_section_should_be_displayed() {
		Assert.assertTrue(hpp.validateList());
	}

	@When("the user clicks View All")
	public void the_user_clicks_view_all() {
	    hpp.disneyCruiseAll();
	}

	@Then("the holiday packages page should open in a new tab")
	public void the_holiday_packages_page_should_open_in_a_new_tab() {
		Assert.assertTrue(hpp.validateDCAll());
	}

	@When("the user chooses a cruise package")
	public void the_user_chooses_a_cruise_package() {
	    hpp.disneyCruisePackage();
	}

	@Then("the correct cruise packages page should open")
	public void the_correct_cruise_packages_page_should_open() {
	    Assert.assertTrue(hpp.validateDCPackage());
	}

}
