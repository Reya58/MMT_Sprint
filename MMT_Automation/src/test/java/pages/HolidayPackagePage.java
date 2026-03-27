package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HolidayPackagePage {
	
	@FindBy (xpath = "//span[@data-cy=\"closeModal\"]")
	private WebElement btn_PopUp;

	@FindBy (id = "fromCity")
	private WebElement btn_FromCity;
	
	@FindBy (className = "citypicker_input")
	private WebElement txt_FromCity;
	
	@FindBy (xpath = "//div[@class=\"searchedCity\"]/descendant::p")
	private WebElement btn_SelectCity_From;
	
	@FindBy (id = "toCity")
	private WebElement btn_ToCity;
	
	@FindBy (xpath = "//input[@placeholder=\"To\"]")
	private WebElement txt_ToCity;
	
	@FindBy (className = "dest-city-container")
	private WebElement btn_SelectCity_To;
	
	@FindBy (xpath = "//span[@data-cy=\"departureDate\"]")
	private WebElement btn_SelectDate;
	
	@FindBy (xpath = "//span[@class=\"DayPicker-NavButton DayPicker-NavButton--next\"]")
	private WebElement btn_NextMonth;
	
	@FindBy(xpath = "//div[contains(@class,'DayPicker-Caption')]")
	private WebElement txt_CurrentMonth;
	
	@FindBy (xpath = "//div[@aria-label=\"Tue May 12 2026\"]")
	private WebElement btn_Date;
	
	@FindBy (xpath = "//button[text() = \"APPLY\"]")
	private WebElement btn_Apply;
	
	@FindBy (xpath = "//div[text() = \"Filters\"]")
	private WebElement btn_Filters;
	
	@FindBy (xpath = "//label[text() = \"With Flights\"]")
	private WebElement btn_Filter1;
	
	@FindBy (xpath = "//p[text() = \"< ₹20,000\"]")
	private WebElement btn_Filter2;
	
	@FindBy (xpath = "//label[text() = \"5\"]")
	private WebElement btn_Filter3;
	
	@FindBy (id = "search_button")
	private WebElement btn_Search;
	
	@FindBy (id = "toCity")
	private WebElement txt_ToCityValue;
	
	@FindBy (xpath = "//span[text() = \"Visa Free Packages\"]")
	private WebElement btn_VisaFreePackages;
	
	@FindBy (xpath = "//li[@class=\"hdlRegion__citylist--item\"]/a")
	private List<WebElement> btn_List;
	
	@FindBy (xpath = "//span[text() = \"Disney Cruise\"]")
	private WebElement btn_DisneyCruise;
	
	@FindBy (xpath = "//p[text()=\"View All\"]")
	private WebElement btn_ViewAll;
	
	WebDriver driver;
	
	public HolidayPackagePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void selectFromCity(String fromCity) {
	    closePopupIfPresent();
	    btn_FromCity.click();
	    txt_FromCity.clear();
	    txt_FromCity.sendKeys(fromCity);
	    btn_SelectCity_From.click();
	}

	public void selectToCity(String toCity) {
	    btn_ToCity.click();
	    txt_ToCity.sendKeys(toCity);
	    try {
	        if (btn_SelectCity_To.isDisplayed()) {
	            btn_SelectCity_To.click();
	        }
	    } catch (Exception e) {
	        btn_SelectDate.click();
	    }
	}

	public boolean validateToCity(String string) {
	    if(txt_ToCityValue.getAttribute("value").equals(string)) {
	        return true;
	    }

	    return false;
	}

	public void selectDateAndGuests() {
	    selectDepartureDate("May 2026");
	    btn_Apply.click();
	    btn_Filters.click();
	}
	
	public void addFilters() {
		btn_Filters.click();
		btn_Filter1.click();
		btn_Filter2.click();
		btn_Filter3.click();
		btn_Apply.click();
	}
	
	public void search() {
		btn_Search.click();
	}
	
	public void clickSearchDetails() {
		HolidayPackagePage_Search hpp_s = new HolidayPackagePage_Search(driver);
		hpp_s.clickSearchDetails();
	}
	
	public boolean validateSearchDetails(String fromCity, String toCity) {
		HolidayPackagePage_Search hpp_s = new HolidayPackagePage_Search(driver);
		return hpp_s.validateSearchDetails(fromCity, toCity);
	}
	
	public boolean validateFilterDetails() {
		HolidayPackagePage_Search hpp_s = new HolidayPackagePage_Search(driver);
		return hpp_s.validateSearchFilters();
	}
	
	public void visaFreePackage() {
		closePopupIfPresent();
		
		btn_VisaFreePackages.click();
	}
	
	public boolean validateList() {
		if(btn_List.size() == 0) {
	        return false;
	    }
		
		for(int i = 0; i < btn_List.size(); i++) {
			if(!(btn_List.get(i).isDisplayed())) 
				return false;
		}
		
		return true;
	}
	
	public void visaFreeDestination() {
		btn_List.get(0).click();
		
		for (String handle : driver.getWindowHandles()) {
		    driver.switchTo().window(handle);
		    if (driver.getTitle().equals("Holiday Packages")) {
		        break;
		    }
		}
	}
	
	public void visaFreeSearch() {
		HolidayPackagePage_VFSearch hpp_vfs = new HolidayPackagePage_VFSearch(driver);
		hpp_vfs.clickSearchDetails();
	}
	
	public boolean validateVisaFreeDetails() {
		HolidayPackagePage_VFSearch hpp_vfs = new HolidayPackagePage_VFSearch(driver);
		return hpp_vfs.validateSearchDetails();
	}
	
	public boolean validateVisaFreeUI() {
		HolidayPackagePage_VFSearch hpp_vfs = new HolidayPackagePage_VFSearch(driver);
		return hpp_vfs.validateSearchUI();
	}
	
	public void disneyCruise() {
		closePopupIfPresent();
		
		btn_DisneyCruise.click();
	}
	
	public void disneyCruiseAll() {
		btn_ViewAll.click();

		for (String handle : driver.getWindowHandles()) {
		    driver.switchTo().window(handle);
		    if (driver.getTitle().equals("Holiday Packages")) {
		        break;
		    }
		}
	}
	
	public boolean validateDCAll() {
		HolidayPackagePage_DCSearch hpp_dcs = new HolidayPackagePage_DCSearch(driver);
		return hpp_dcs.validateSearch();
	}
	
	public void disneyCruisePackage() {
		HolidayPackagePage_DCSearch hpp_dcs = new HolidayPackagePage_DCSearch(driver);
		hpp_dcs.clickSearchDetails();
	}
	
	public boolean validateDCPackage() {
		HolidayPackagePage_DCSearch hpp_dcs = new HolidayPackagePage_DCSearch(driver);
		return hpp_dcs.validateSearchDetails();
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
	
	private void selectDepartureDate(String targetMonthYear) {
	    while (true) {
	        String currentMonth = txt_CurrentMonth.getText();

	        if (currentMonth.equalsIgnoreCase(targetMonthYear)) {
	            break;
	        }
	        btn_NextMonth.click();
	    }

	    btn_Date.click();
	}
}
