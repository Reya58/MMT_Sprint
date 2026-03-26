package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

public class Hotel_RunnerIo {
	
	@CucumberOptions(
		    features = "src/test/resources/features/Hotels.feature",     // path to feature files
		    glue = "stepDefinations",                     // package of step defs
		    plugin = {
		        "pretty",
		        "html:reports/hotels_module/cucumber-report.html",
		        "json:reports/hotels_module/cucumber.json"
		    },
		    monochrome = true                             // clean console output
		)

	public class TestRunner extends AbstractTestNGCucumberTests {

//	    @Override
//	    @org.testng.annotations.DataProvider(parallel = true)
//	    public Object[][] scenarios() {
//	        return super.scenarios();
//	    }
	}

}
