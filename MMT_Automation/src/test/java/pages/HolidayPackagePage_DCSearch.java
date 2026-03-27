package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HolidayPackagePage_DCSearch {

	@FindBy (xpath = "//span[@class=\"close closeIcon\"]")
	private WebElement btn_PopUp;
	
	@FindBy (className = "packageTextContainer")
	private List<WebElement> txt_Packages;

	WebDriver driver;
	
	public HolidayPackagePage_DCSearch(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean validateSearch() {
		closePopupIfPresent();
		
		if(txt_Packages.size() == 0) {
	        return false;
	    }

		for(int i = 0; i < 5; i++) {
			if(!(txt_Packages.get(i).isDisplayed())) {
				System.out.println(i);
				return false;
			}
		}
		
		return true;
	}
	
	public void clickSearchDetails() {
		closePopupIfPresent();
		
		txt_Packages.get(0).click();
		
		for (String handle : driver.getWindowHandles()) {
		    driver.switchTo().window(handle);
		    if (driver.getTitle().equals("Best of Singapore & Disney Cruise")) {
		        break;
		    }
		}
	}

	public boolean validateSearchDetails() {
		HolidayPackagePage_DCPackage hpp_dcp = new HolidayPackagePage_DCPackage(driver);
		return hpp_dcp.validateCruise();
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
