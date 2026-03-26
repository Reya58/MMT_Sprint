package base;

import org.openqa.selenium.WebDriver;

import pages.FlightResultPage;
import pages.FlightSearchPage;

public class PageManager {

    private WebDriver driver;
    private FlightSearchPage homePage;
    private FlightResultPage resultPage;

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
    	if(resultPage== null) {
    		resultPage = new FlightResultPage(driver);
    	}
    	return resultPage;
    }
}