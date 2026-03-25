package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HolidayPackagePage {
	
	@FindBy (xpath = "//span[@data-cy=\"closeModal\"]")
	WebElement btn_PopUp;

	@FindBy (id = "fromCity")
	WebElement btn_FromCity;
	
	@FindBy (className = "citypicker_input")
	WebElement txt_FromCity;
	
	@FindBy (xpath = "//div[@class=\"searchedCity\"]/descendant::p")
	WebElement btn_SelectCity_From;
	
	@FindBy (id = "toCity")
	WebElement btn_ToCity;
	
	@FindBy (xpath = "//input[@placeholder=\"To\"]")
	WebElement txt_ToCity;
	
	@FindBy (className = "dest-city-container")
	WebElement btn_SelectCity_To;
	
	@FindBy (xpath = "//span[@data-cy=\"departureDate\"]")
	WebElement btn_Departure;
	
	@FindBy (xpath = "//span[@class=\"DayPicker-NavButton DayPicker-NavButton--next\"]")
	WebElement btn_NextMonth;
	
	@FindBy(xpath = "//div[contains(@class,'DayPicker-Caption')]")
	WebElement txt_CurrentMonth;
	
	@FindBy (xpath = "//div[@aria-label=\"Tue May 12 2026\"]")
	WebElement btn_Date;
	
	@FindBy (xpath = "//div[text() = \"Rooms & Guests\"]")
	WebElement btn_Rooms;
	
	@FindBy (xpath = "//button[text() = \"APPLY\"]")
	WebElement btn_Apply;
	
	@FindBy (xpath = "//div[text() = \"Filters\"]")
	WebElement btn_Filters;
	
	@FindBy (xpath = "//label[text() = \"With Flights\"]")
	WebElement btn_Filter1;
	
	@FindBy (xpath = "//p[text() = \"< ₹20,000\"]")
	WebElement btn_Filter2;
	
	@FindBy (xpath = "//label[text() = \"5\"]")
	WebElement btn_Filter3;
	
	@FindBy (id = "search_button")
	WebElement btn_Search;
	
	@FindBy (className = "no-data-found")
	List<WebElement> txt_NoData;
	
	@FindBy (xpath = "//span[text() = \"Visa Free Packages\"]")
	WebElement btn_VisaFreePackages;
	
	@FindBy (linkText = "Malaysia")
	WebElement btn_Destination;
	
	@FindBy (xpath = "//span[text() = \"Disney Cruise\"]")
	WebElement btn_DisneyCruise;
	
	@FindBy (xpath = "//p[text()=\"View All\"]")
	WebElement btn_ViewAll;
	
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

	public boolean selectToCity(String toCity) {
	    btn_ToCity.click();
	    txt_ToCity.sendKeys(toCity);

	    if(txt_NoData.size() > 0 && txt_NoData.get(0).isDisplayed()) {
	        return false;   // invalid city
	    }

	    btn_SelectCity_To.click();
	    return true;        // valid city
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
		btn_Destination.click();
		
		for (String handle : driver.getWindowHandles()) {
		    driver.switchTo().window(handle);
		    if (driver.getTitle().equals("Holiday Packages")) {
		        break;
		    }
		}
	}
	
	public void disneyCruise() {
		closePopupIfPresent();
		
		btn_DisneyCruise.click();
		btn_ViewAll.click();

		for (String handle : driver.getWindowHandles()) {
		    driver.switchTo().window(handle);
		    if (driver.getTitle().equals("Holiday Packages")) {
		        break;
		    }
		}
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
	
	public void selectDepartureDate(String targetMonthYear) {
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
