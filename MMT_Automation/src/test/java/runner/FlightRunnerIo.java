package runner;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import utils.DriverFactory;

@CucumberOptions(
	    features = "src/test/resources/features/flightResev.feature",
	    glue = {"stepDefinations"},
	    tags= "@autocomplete",
    	plugin = {
		    "pretty",
		    "html:reports/flight-module/cucumber-report.html",
		},
	    monochrome = true
	)

public class FlightRunnerIo extends AbstractTestNGCucumberTests{
	@BeforeTest
	@Parameters("browser")
	public void setBrowserPerfernce(String browser) {
		DriverFactory.initDriver(browser);
	}
	
	@AfterTest
	public void quitBrowser() {
		DriverFactory.quitDriver();
	}
	
}