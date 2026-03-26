package stepDefinations;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;

import homestay_utils.DriverFactory;

public class Hooks {

    public static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    public static ThreadLocal<String> browserThread = new ThreadLocal<>();

    @Before
    public void setUp() {

        String browser = browserThread.get(); // get from runner
        System.out.println("Launching browser: " + browser);

        WebDriver driver = DriverFactory.getDriver(browser);
        driverThread.set(driver);
    }

    @After
    public void tearDown() {
        WebDriver driver = driverThread.get();

        if (driver != null) {
            driver.quit();
            driverThread.remove();
        }
    }
}