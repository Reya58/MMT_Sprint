package runner;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import utils.DriverFactory;

@CucumberOptions(
	    features = "src/test/resources/features/flightResev.feature",
	    glue = {"stepDefinations"},
	    tags= "@TC_01",
    	plugin = {
		    "pretty",
		    "html:reports/flight-module/cucumber-report.html",
		    "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
		},
	    monochrome = true
	)

public class FlightRunnerIo extends AbstractTestNGCucumberTests{
	@BeforeTest
	@Parameters("browser")
	public void setBrowserPerfernce(String browser) {
		System.out.println(browser);
		DriverFactory.setBrowser(browser);
	}
}