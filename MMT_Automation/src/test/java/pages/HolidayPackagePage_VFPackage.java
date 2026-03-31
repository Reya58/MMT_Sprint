package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HolidayPackagePage_VFPackage {

	@FindBy (className = "skipBtn")
	private WebElement btn_PopUp1;
	
	@FindBy (xpath = "//span[@class=\"close closeIcon\"]")
	private WebElement btn_PopUp2;
	
	@FindBy (className = "dest-guide-header")
	private WebElement txt_Destination;
	
	@FindBy (className = "topHeading")
	private WebElement txt_Heading;
	
	@FindBy (xpath = "//div[@class=\"imageLoaderContainer \"]/img")
	private List<WebElement> img_UI;
	
	WebDriver driver;
	
	public HolidayPackagePage_VFPackage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean validateLocation(String destination) {
		closePopupIfPresent();
		if(txt_Destination.getText().contains(destination))
			return true;
		else
			return false;
	}
	
	public boolean validateUI() {
		closePopupIfPresent();
		if(!(txt_Heading.isDisplayed())) {
			return false;
		}
		
		closePopupIfPresent();
		if(img_UI.size() == 0) {
	        return false;
	    }

		for(int i = 0; i < 5; i++) {
			if(!(img_UI.get(i).isDisplayed())) {
				System.out.println(i);
				return false;
			}
		}
		
		return true;
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
