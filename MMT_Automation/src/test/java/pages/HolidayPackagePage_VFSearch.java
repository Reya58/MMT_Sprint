package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HolidayPackagePage_VFSearch {

	@FindBy (xpath = "//span[@class=\"close closeIcon\"]")
	private WebElement btn_PopUp;

	@FindBy (xpath = "//div[@class=\"packageCardWrapper similarPackage\"]")
	private WebElement btn_FirstResult;
	
	@FindBy (xpath = "//div[text() = \"With Flight\"]/ancestor::div[@class=\"variant-card-container  pointer\"]")
	private WebElement btn_FirstResult_Flights;

	WebDriver driver;
	
	public HolidayPackagePage_VFSearch(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void clickSearchDetails() {
		closePopupIfPresent();
		
		btn_FirstResult.click();
		btn_FirstResult_Flights.click();
		
		for (String handle : driver.getWindowHandles()) {
		    driver.switchTo().window(handle);
		    if (driver.getTitle().equals("Epic Kerala - Mega Price Drop Sale")) {
		        break;
		    }
		}
	}
	
	public boolean validateSearchDetails() {
		HolidayPackagePage_VFPackage hpp_vfp = new HolidayPackagePage_VFPackage(driver);
		return hpp_vfp.validateLocation("Malaysia");
	}
	
	public boolean validateSearchUI() {
		HolidayPackagePage_VFPackage hpp_vfp = new HolidayPackagePage_VFPackage(driver);
		return hpp_vfp.validateUI();
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
