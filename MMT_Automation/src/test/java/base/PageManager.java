package base;

import org.openqa.selenium.WebDriver;

import pages.FlightPaymentPage;
import pages.FlightResultPage;
import pages.FlightSearchPage;
import pages.FlightTravellerDetailsPage;

public class PageManager {

	private WebDriver driver;
	private FlightSearchPage homePage;
	private FlightResultPage resultPage;
	private FlightTravellerDetailsPage detailsPage;
	private FlightPaymentPage paymentpage;

	public PageManager(WebDriver driver) {
		this.driver = driver;
	}

	public FlightSearchPage getFlightSearchPage() {
		if (homePage == null) {
			homePage = new FlightSearchPage(driver);
		}
		return homePage;
	}

	public FlightResultPage getFlightResultPage() {
		if (resultPage == null) {
			resultPage = new FlightResultPage(driver);
		}
		return resultPage;
	}

	public FlightTravellerDetailsPage getFlightTravellerDetailsPage() {
		if (detailsPage == null) {
			detailsPage = new FlightTravellerDetailsPage(driver);
		}
		return detailsPage;
	}

	public FlightPaymentPage getFlightPaymenyPage() {
		if (paymentpage == null) {
			
			
			
			paymentpage = new FlightPaymentPage(driver);
		}
		return paymentpage;
	}
}