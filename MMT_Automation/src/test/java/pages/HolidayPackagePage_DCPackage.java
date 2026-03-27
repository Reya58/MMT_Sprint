package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HolidayPackagePage_DCPackage {

	@FindBy (className = "skipBtn")
	private WebElement btn_PopUp1;
	
	@FindBy (xpath = "//span[@class=\"close closeIcon\"]")
	private WebElement btn_PopUp2;
	
	@FindBy (className = "topHeading")
	private WebElement txt_Heading;

	WebDriver driver;
	
	public HolidayPackagePage_DCPackage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean validateCruise() {
		closePopupIfPresent();
		if(txt_Heading.getText().contains("Disney Cruise"))
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
