package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	    features = "src/test/resources/features/HolidayPackage.feature",   // path to feature files
	    glue = {"stepDefinations"},                 					   // package of step defs
	    plugin = {
	        "pretty",
	        "html:target/cucumber-reports.html",
	        "json:target/cucumber.json"
	    },
	    monochrome = true
	)

public class HolidayPackageRunnerIO extends AbstractTestNGCucumberTests {
}
