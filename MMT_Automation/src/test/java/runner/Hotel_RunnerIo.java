//package runner;
//
//
//import io.cucumber.testng.AbstractTestNGCucumberTests;
//import io.cucumber.testng.CucumberOptions;
//
//public class Hotel_RunnerIo {
//	
//	@CucumberOptions(
//		    features = "src/test/resources/features/Hotels.feature",     // path to feature files
//		    glue = "stepDefinations",                     // package of step defs
//		    plugin = {
//		        "pretty",
//		        "html:reports/hotels_module/cucumber-report.html",
//		        "json:reports/hotels_module/cucumber.json"
//		    },
//		    monochrome = true                             // clean console output
//		)
//
//	public class Hotel_RunnerIo extends AbstractTestNGCucumberTests {
//	    
//	    @Override
//	    @org.testng.annotations.DataProvider(parallel = true)
//	    public Object[][] scenarios() {
//	        return super.scenarios();
//	        
//	    }
//	}
//
//}
package runner;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import utils.DriverFactory;

@CucumberOptions(
    features = "src/test/resources/features/Hotels.feature",
    glue = "stepDefinations",
    plugin = {
        "pretty",
        "html:reports/hotels_module/cucumber-report.html",
        "json:reports/hotels_module/cucumber.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true
)
public class Hotel_RunnerIo extends AbstractTestNGCucumberTests {

	@BeforeTest
    @Parameters("browser")
    public void setupBrowserPreference(String browser) {
		DriverFactory.setBrowser(browser);
    }
	
//	public static String browser;
//
//    @Parameters("browser")
//    @BeforeClass
//    public void setBrowser(String browserName) {
//        browser = browserName;
//    }
//    @Override
//    @org.testng.annotations.DataProvider(parallel = false)
//    public Object[][] scenarios() {
//        return super.scenarios();
    }

