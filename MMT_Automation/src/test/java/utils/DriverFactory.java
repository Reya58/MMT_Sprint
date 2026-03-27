package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    public static void initDriver(String browser) {
    	if(browser.contains("chrome")) {
    		driver.set(new ChromeDriver());
    	}
    	else if(browser.contains("firefox")) {
    		driver.set(new FirefoxDriver());
    	}
    	else if(browser.contains("edge")) {
    		driver.set(new EdgeDriver());
    	}
    	else {
    		driver.set(new FirefoxDriver());
    	}
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}