package stepDefinations;

import java.time.Duration;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.DriverFactory;

public class Hooks {

    @Before
    public void setUp() {
        DriverFactory.initDriver();
        
        DriverFactory.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        DriverFactory.getDriver().manage().window().maximize();
    }

    @After
    public void tearDown(Scenario scenario) {
        System.out.println("Finished Scenario: " + scenario.getName());
        DriverFactory.quitDriver();
    }
}