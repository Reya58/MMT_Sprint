package runner;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import utils.DriverFactory;

@CucumberOptions(
    features = "src/test/resources/features/HolidayPackage.feature",
    glue = {"stepDefinations"},
    plugin = {"pretty", "html:target/cucumber-reports.html",
            "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
    monochrome = true
)
public class HolidayPackageRunnerIO extends AbstractTestNGCucumberTests {

	@BeforeTest
    @Parameters("browser")
    public void setupBrowserPreference(String browser) {
		DriverFactory.setBrowser(browser);
    }
}