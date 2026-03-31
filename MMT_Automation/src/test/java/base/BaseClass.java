package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import utils.DriverFactory;

public class BaseClass {

    protected WebDriver getDriver() {
        WebDriver driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }
}