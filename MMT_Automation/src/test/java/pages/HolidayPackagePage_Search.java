package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HolidayPackagePage_Search {
	
	@FindBy (xpath = "//span[@class=\"close closeIcon\"]")
	WebElement btn_PopUp;

	@FindBy (xpath = "//div[@class=\"packageCardWrapper similarPackage\"]")
	WebElement btn_FirstResult;
	
	@FindBy (xpath = "//div[text() = \"With Flight\"]/ancestor::div[@class=\"variant-card-container  pointer\"]")
	WebElement btn_FirstResult_Flights;
	
	@FindBy (className = "priceStyle")
	List<WebElement> txt_Prices;
	
	@FindBy (xpath = "//ul[@class=\"tripListWrapper\"]/li")
	List<WebElement> txt_TripDetails;
	
	WebDriver driver;
	
	public HolidayPackagePage_Search(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean validateSearchDetails(String fromCity, String toCity) {
		closePopupIfPresent();
		
		btn_FirstResult.click();
		btn_FirstResult_Flights.click();
		
		for (String handle : driver.getWindowHandles()) {
		    driver.switchTo().window(handle);
		    if (driver.getTitle().equals("Epic Kerala - Mega Price Drop Sale")) {
		        break;
		    }
		}
		
		HolidayPackagePage_Package hpp_p = new HolidayPackagePage_Package(driver);
		return hpp_p.validateLocations(fromCity, toCity);
	}
	
	public boolean validateSearchFilters() {
		closePopupIfPresent();
		
		for(int i = 0; i < txt_Prices.size(); i++) {
			if(parseCurrency(txt_Prices.get(i).getText()) > 20000) {
				return false;
			}
		}
		
		for(int i = 0; i < txt_TripDetails.size();) {
			if(!(txt_TripDetails.get(i).getText().contains("Flights"))) {
				return false;
			}
			i++;
			
			if(!(txt_TripDetails.get(i).getText().contains("5 Star"))) {
				return false;
			}
			i += 4;
		}
		
		return true;
	}
	
	public static double parseCurrency(String value) {
	    if (value == null || value.isEmpty()) {
	        return 0.0;
	    }

	    String cleaned = value.replaceAll("[^\\d.]", "");

	    return Double.parseDouble(cleaned);
	}
	
	public void closePopupIfPresent() {
	    try {
	        if (btn_PopUp.isDisplayed()) {
	            btn_PopUp.click();
	        }
	    } catch (Exception e) {
	        //ignore
	    }
	}
}
