package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HolidayPackagePage_Search {
	
	@FindBy (xpath = "//span[@class=\"close closeIcon\"]")
	private WebElement btn_PopUp;

	@FindBy (className = "packageTextContainer")
	private WebElement btn_FirstResult;
	
	@FindBy (xpath = "//div[text() = \"With Flight\"]/ancestor::div[@class=\"variant-card-container  pointer\"]")
	private WebElement btn_FirstResult_Flights;
	
	@FindBy (xpath = "//div[@class=\"titleWrapper\"]/p")
	private WebElement txt_PackageName;
	
	@FindBy (className = "priceStyle")
	private List<WebElement> txt_Prices;
	
	@FindBy (xpath = "//ul[@class=\"tripListWrapper\"]/li")
	private List<WebElement> txt_TripDetails;
	
	WebDriver driver;
	
	public HolidayPackagePage_Search(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void clickSearchDetails() {
		closePopupIfPresent();
		
		String title = txt_PackageName.getText();
		
		btn_FirstResult.click();
		try {
	        if (btn_FirstResult_Flights.isDisplayed()) {
	            btn_FirstResult_Flights.click();
	        }
	    } catch (Exception e) {
	        //ignore
	    }
		
		for (String handle : driver.getWindowHandles()) {
		    driver.switchTo().window(handle);
		    if (driver.getTitle().equals(title)) {
		        break;
		    }
		}
	}
		
	public boolean validateSearchDetails(String fromCity, String toCity) {
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
	
	private static double parseCurrency(String value) {
	    if (value == null || value.isEmpty()) {
	        return 0.0;
	    }

	    String cleaned = value.replaceAll("[^\\d.]", "");

	    return Double.parseDouble(cleaned);
	}
	
	private void closePopupIfPresent() {
	    try {
	        if (btn_PopUp.isDisplayed()) {
	            btn_PopUp.click();
	        }
	    } catch (Exception e) {
	        //ignore
	    }
	}
}
