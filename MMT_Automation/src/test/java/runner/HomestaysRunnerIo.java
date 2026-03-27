package runner;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

import utils.homestay_utils.DriverFactory;

@CucumberOptions(
    features = "src/test/resources/features/homestays.feature",
    glue = "stepDefinations",
    dryRun=false
)
public class HomestaysRunnerIo extends AbstractTestNGCucumberTests {
	
	@BeforeTest
	@Parameters("browser")
	public void setBrowserPerfernce(String browser) {
		//System.out.println(browser);
		DriverFactory.setBrowser(browser);
	}
	
	
 
}