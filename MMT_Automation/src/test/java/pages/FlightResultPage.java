package pages;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlightResultPage {

	private WebDriver driver;
	private WebDriverWait wait;

	@FindBy(xpath = "//span[@class='clearFilter']")
	private WebElement btn_clearAllFilter;
	
	
	
	
	
	
	
	
	
//	-------------CONSTRUCTOR--------------- 
	public FlightResultPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(4));
	}

//----------- PAGE  INTRUPTION HANDLERS ----------------
	public void handleInterruptions() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//div[@class='overlayCrossIconV2 newCrossIconV2Wrapper newCrossIconV2Wrapper--right']")))
					.click();

		} catch (Exception e) {

		}
	}

// --------	VISIBILITY OF FILTER ------------------
	public 

//--------------VERIFIER OF BEING ON SAME SEARCH RESULT PAGE -----------
	public Boolean isOnSearchResultPage() {

		try {
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("flightBody")));
			return element.isDisplayed();

		} catch (Exception e) {
			return false;
		}
	}
	
// --------------GENERAL FUNCTIONS FOR ALL FILTERS----------- 	
	
	public boolean isFilterPresent(String filterName) {
	    By filterHeading = By.xpath("//p[contains(@class,'filtersHeading') and normalize-space()='" + filterName + "']");
	    return driver.findElements(filterHeading).size() > 0;
	}
	
	
//	----------------------- AIRLINE FILTER----------------
	
	
	public void selectAirline(String airline) {
		
	}

	public boolean isAirlineSelected(String airline) {
		
	}

	public List<String> getAvailableAirlines(){
		
	}

	public void deselectAirline(String airline) {
		
	}

}
