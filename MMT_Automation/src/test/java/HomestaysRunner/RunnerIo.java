package HomestaysRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;



@CucumberOptions(
	    features = "src/test/resources/features/homestays.feature",
	    glue = "stepDefinations",
	    dryRun = true
	)
public class RunnerIo extends AbstractTestNGCucumberTests {
	  
}