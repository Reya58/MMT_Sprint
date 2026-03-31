package utils;
	
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.edge.EdgeDriver;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.firefox.FirefoxDriver;
	
	public class DriverFactory {
	
	    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	    private static ThreadLocal<String> browserName = new ThreadLocal<>();

	    public static void setBrowser(String browser) {
	        browserName.set(browser);
	    }
	
	    public static void initDriver() {
	    	
	    	String browser = browserName.get();
	
	        if (browser.equalsIgnoreCase("chrome")) {
	            driver.set(new ChromeDriver());
	
	        } else if (browser.equalsIgnoreCase("firefox")) {
	            driver.set(new FirefoxDriver());
	
	        } else if (browser.equalsIgnoreCase("edge")) {
	            driver.set(new EdgeDriver());
	
	        } else {
	            throw new RuntimeException("Invalid browser: " + browser);
	        }
	
	        driver.get().manage().window().maximize();
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