package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	    features = "src/test/resources/features/flightResev.feature",
	    glue = {"stepDefinations"},
	    tags= "@results",
    	plugin = {
		    "pretty",
		    "html:reports/flight-module/cucumber-report.html",
		},
	    monochrome = true
	)

public class FlightRunnerIo extends AbstractTestNGCucumberTests{
	
}