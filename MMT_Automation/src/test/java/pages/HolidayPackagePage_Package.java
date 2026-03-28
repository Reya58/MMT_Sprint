package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HolidayPackagePage_Package {

	@FindBy (className = "skipBtn")
	private WebElement btn_PopUp1;
	
	@FindBy (xpath = "//span[@class=\"close closeIcon\"]")
	private WebElement btn_PopUp2;
	
	@FindBy (id = "fromCity")
	private WebElement txt_FromCity;
	
	@FindBy (className = "topHeading")
	private WebElement txt_ToCity;
	
	WebDriver driver;
	
	public HolidayPackagePage_Package(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean validateLocations(String fromCity, String toCity) {
		closePopupIfPresent();
		if(txt_FromCity.getAttribute("value").contains(fromCity) && txt_ToCity.getText().contains(toCity))
			return true;
		else
			return false;
	}
	
	private void closePopupIfPresent() {
	    try {
	        if (btn_PopUp1.isDisplayed()) {
	            btn_PopUp1.click();
	        }
	    } catch (Exception e) {
	        //ignore
	    }
	    try {
	        if (btn_PopUp2.isDisplayed()) {
	            btn_PopUp2.click();
	        }
	    } catch (Exception e) {
	        //ignore
	    }
	}
}
