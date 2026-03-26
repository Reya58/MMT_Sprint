package HomestaysRunner;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import stepDefinations.Hooks;

@CucumberOptions(
    features = "src/test/resources/features/homestays.feature",
    glue = "stepDefinations"
)
public class RunnerIo extends AbstractTestNGCucumberTests {

    @BeforeClass
    @Parameters("browser")
    public void setUp(String browser) {

        System.out.println("Browser from XML: " + browser);

        Hooks.browserThread.set(browser); // ✅ pass to Hooks
    }
}