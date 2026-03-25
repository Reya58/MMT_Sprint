package stepDefinations;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.en.*;
import pages.FlightSearchPage;

public class FlightReservationSteps {
	
	WebDriver driver;
	
	@Given("the user is on the flight search page")
	public void the_user_is_on_the_flight_search_page() {
	    // Write code here that turns the phrase above into concrete actions
		driver = new FirefoxDriver();
		
		driver.get("https://www.makemytrip.com/flights/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.findElement(By.className("commonModal__close")).click();
		driver.findElement(By.xpath("//div[@class='tp-dt-header-icon']//img[@alt='minimize']")).click();
	}

	@When("the user searches for flights from {string} to {string} on {string}")
	public void the_user_searches_for_flights_from_to_on(String frmCity, String toCity, String date) throws InterruptedException {
	    // Write code here that turns the phrase above into concrete actions
		FlightSearchPage flpg = new FlightSearchPage(driver);
		flpg.setFromCity(frmCity);
		if (flpg.isSuggestionDisplayed()) {
			flpg.selectCity(frmCity);
		}
		
		flpg.setToCity(toCity);
		if(flpg.isSuggestionDisplayed()) {
			flpg.selectCity(toCity);
		}
		flpg.selectDate(date);
		Thread.sleep(2000);
		flpg.search();
	}

	@Then("a list of available flights is displayed")
	public void a_list_of_available_flights_is_displayed() {
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("flights page is loaded");
	}
	
}
