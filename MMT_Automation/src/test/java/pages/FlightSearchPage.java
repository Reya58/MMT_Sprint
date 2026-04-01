package pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlightSearchPage {

	@FindBy(id = "fromCity")
	private WebElement btn_frmCity;

	@FindBy(xpath = "//input[@placeholder='From']")
	private WebElement txt_fromCity;

	@FindBy(id = "toCity")
	private WebElement btn_toCity;

	@FindBy(xpath = "//input[@placeholder='To']")
	private WebElement txt_toCity;

	@FindBy(xpath = "//p[@data-cy='departureDate']")
	private WebElement btn_departure_date;

	@FindBy(xpath = "//span[@class='DayPicker-NavButton DayPicker-NavButton--next']")
	private WebElement btn_next_month;

	@FindBy(xpath = "//p[@data-cy='submit']//a")
//	@FindBy(xpath = "//a[@data-cy='submit']")
	private WebElement btn_search;

	@FindBy(xpath = "//input[@id='fromCity']")
	private WebElement selected_fromCity;

	@FindBy(xpath = "//input[@id='toCity']")
	private WebElement selected_toCity;

	@FindBy(xpath = "//div[@id='errorMessage']//span[@data-cy='sameCityError']")
	private WebElement errorMessage;

	@FindBy(xpath = "//li[@data-cy='roundTrip']")
	private WebElement btn_roundTrip;

	@FindBy(xpath = "//li[@data-cy='mulitiCityTrip']")
	private WebElement btn_multiCityTrip;

	@FindBy(xpath = "//div[@data-cy='returnArea']")
	private WebElement btn_returnDate;

	@FindBy(xpath = "//div[@data-cy='flightTraveller']")
	private WebElement btn_travellersAndClasses;

//--------------FLIGHT TRACKER ELEMENTS--------------	

	@FindBy(xpath = "//div[@class='entryPointsGrid__row']//img[@alt='Flight Tracker']/../..")
	private WebElement btn_flightTracker;

	@FindBy(xpath = "//li//div//font//b[text()='By Flight']")
	private WebElement btn_flightTrackerByFlight;

	@FindBy(xpath = "//div[@class='search-options fswTabs']//li//div[.//font[text()='By Route']]")
	private WebElement btn_flightTrackerByRoute;

	@FindBy(xpath = "//div[@class='search-options fswTabs']//li//div[.//font[text()='By Airport']]")
	private WebElement btn_flightTrackerByAirport;

	// BY NUMBER ELEMENTS

	@FindBy(id = "fisAIRLINE")
	private WebElement txt_flightTrackerByFlightNumber;

	@FindBy(xpath = "//div[@class='date-field']")
	private WebElement btn_flightTrackedDate;

	// BY ROUTES ELEMENTS

	@FindBy(xpath = "//input[@placeholder='From']")
	private WebElement txt_FromCityFlightTracker;

	@FindBy(xpath = "//input[@placeholder='To']")
	private WebElement txt_ToCityFlightTracker;

	// BY AIRPORTS ELEMENTS

	@FindBy(id = "fisAIRPORT")
	private WebElement txt_AirportFlightTracker;

	@FindBy(xpath = "//button[@class='fis-search-button']")
	private WebElement btn_searchFlightTracker;

//	-------------MULTI SELECT CITY ELEMENTS-----------

	@FindBy(id = "fromAnotherCity0")
	private WebElement txt_FromAnotherCity;

	@FindBy(xpath = "(//button[@class='btnAddCity'])[2]")
	private WebElement btn_AddAnotherCity;

	WebDriver driver;
	WebDriverWait wait;

	private YearMonth getMonth(By locator, DateTimeFormatter formatter) {
		String monthText = driver.findElement(locator).getText().trim();

		// Fix missing space between month and year
		monthText = monthText.replaceAll("([A-Za-z]+)(\\d{4})", "$1 $2");

		return YearMonth.parse(monthText, formatter);
	}

	private void waitForMonthChange(By locator, String oldValue) {
		new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver -> {
			String newValue = driver.findElement(locator).getText();
			return !newValue.equals(oldValue);
		});
	}

	private void dismissCoachmarkIfPresent() {
		try {
			
//			WebElement coachmark = wait
//					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class,'coachmark')]")));
//			coachmark.click();
			Thread.sleep(3000);
			Actions act = new Actions(driver);
			
			act.moveByOffset(100, 100).perform();
			act.click().perform();
		} catch (Exception ignored) {
			// intentionally ignored
		}
	}

	private void closeLoginPopup() {
		try {
			WebElement closeBtn = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-cy='closeModal']")));

			closeBtn.click();

		} catch (Exception e) {
//			System.out.println("Login popup not present");
		}
	}

	private void minimizeBanner() {
		try {
			
			WebElement minimizeBtn = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@alt='minimize']")));

			minimizeBtn.click();

		} catch (Exception e) {
//			System.out.println("Banner minimize not present");
		}
	}

//	################# PUBLIC FUNCTIONS ##############################

//	----------CONSTRUCTOR----------
	public FlightSearchPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

// ---------------INTRUPTION HANDLING------------------
	public void handleInterruptions() {
		closeLoginPopup();
		minimizeBanner();
		dismissCoachmarkIfPresent();
	}

//	--------------- SLECTING SPECIFIED DATE ON ONE FLIGHT SEARCH PAGE-------------

	public boolean selectDate(String inputDate) throws InterruptedException {
		Thread.sleep(1000);

		// ---------- FORMATTERS ----------
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy");
		DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");

		// ---------- PARSE TARGET DATE ----------
		LocalDate targetDate = LocalDate.parse(inputDate, inputFormatter);
		YearMonth targetMonth = YearMonth.from(targetDate);

		// ---------- LOCATORS ----------
		By leftMonthLocator = By.xpath("(//div[contains(@class,'DayPicker-Caption')]/div)[1]");
		By rightMonthLocator = By.xpath("(//div[contains(@class,'DayPicker-Caption')]/div)[2]");
		By nextBtn = By.xpath("//span[contains(@class,'NavButton--next')]");
		By prevBtn = By.xpath("//span[contains(@class,'NavButton--prev')]");

		// ---------- GET DISPLAYED MONTHS ----------
		YearMonth leftMonth = getMonth(leftMonthLocator, monthFormatter);
		YearMonth rightMonth = getMonth(rightMonthLocator, monthFormatter);

		// ---------- NAVIGATION LOOP ----------
		int safetyCounter = 0; // prevents infinite loop

		while (!(targetMonth.equals(leftMonth) || targetMonth.equals(rightMonth))) {

			if (targetMonth.isAfter(rightMonth)) {
				driver.findElement(nextBtn).click();
			} else {
				driver.findElement(prevBtn).click();
			}

			// Wait for DOM update (important)
			waitForMonthChange(leftMonthLocator, leftMonth.format(monthFormatter));

			// Re-fetch updated months
			leftMonth = getMonth(leftMonthLocator, monthFormatter);
			rightMonth = getMonth(rightMonthLocator, monthFormatter);

			// Safety break
			if (++safetyCounter > 12) {
				throw new RuntimeException("Unable to navigate to target month: " + targetMonth);
			}
		}

		// ---------- SELECT DATE ----------
		By dateLocator = By.xpath("//div[@aria-label='" + inputDate + "']");
		WebElement dateElement = wait.until(ExpectedConditions.visibilityOf(driver.findElement(dateLocator)));

		// ---------- CHECK DISABLED ----------
		String classes = dateElement.getAttribute("class");

		if (classes.contains("DayPicker-Day--disabled")) {
			return false;
		}

		// ---------- CLICK + VERIFY ----------
		dateElement.click();
		return true;
	}

//	-------------- RETURNING SELECTED DATE-------------

	public String getSelectedDate() throws InterruptedException {
		btn_departure_date.click();
		Thread.sleep(2000);
		return driver.findElement(By.xpath("//div[@class='DayPicker-Day DayPicker-Day--selected']"))
				.getAttribute("aria-label");
	}

//	---------------ONEWAY AND ROUND TRIP CITY SELECTION-----------
	public void setFromCity(String fromCity) throws InterruptedException {
		btn_frmCity.click();
		Thread.sleep(1000);
		txt_fromCity.sendKeys(fromCity);
	}

	public void setToCity(String toCity) throws InterruptedException {
		btn_toCity.click();
		Thread.sleep(1000);
		txt_toCity.sendKeys(toCity);
	}

	public Boolean isSuggestionDisplayed() {

		try {
			// wait briefly for any suggestion item to appear
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".react-autosuggest__suggestion")));

			// now verify actual visible suggestions
			List<WebElement> suggestions = driver.findElements(By.cssSelector(".react-autosuggest__suggestion"));

			return suggestions.size() > 0 && suggestions.get(0).isDisplayed();

		} catch (Exception e) {
			return false;
		}
	}

	public void selectCity(String city) throws RuntimeException {
		if (isSuggestionDisplayed()) {
			try {
				By option = By.xpath("//span[contains(@class,'revampedCityName') and contains(text(),'" + city + "')]");

				wait.until(ExpectedConditions.elementToBeClickable(option)).click();
			} catch (Exception e) {
				throw new RuntimeException("Suggestion isn't clickable: " + city, e);
			}
		} else {
			throw new RuntimeException("Suggestion didn't appear");
		}
	}

	public String getSelectedFromCity() {
		return selected_fromCity.getAttribute("value");
	}

	public String getSelectedToCity() {
		return selected_toCity.getAttribute("value");
	}

	public Boolean isErrorMessegeDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(errorMessage)).isDisplayed();
	}

	public void clickDate() {
		btn_departure_date.click();
	}

//-----------------FARE CARD ------------------
	public void selectFareCard(String fare) {
		WebElement fareCard = driver.findElement(
				By.xpath("//div[@class='fareOptionsWrap ']//div//div[contains(text()," + fare + "'Regular']"));

		fareCard.click();
	}

//	-------------------------CONFIGURING TRAVEL----------------------------

	public void setAdultsTravelers(String count) {
		if (Integer.valueOf(count) <= 9) {
			driver.findElement(By.xpath("//li[@data-cy='adults-" + count + "']")).click();
		} else {
			driver.findElement(By.xpath("//li[@data-cy='adults-10']")).click();
		}
	}

	public void setChildTraveller(String count) {
		if (Integer.valueOf(count) <= 6) {
			driver.findElement(By.xpath("//li[@data-cy='children-" + count + "']")).click();
		} else {
			driver.findElement(By.xpath("//li[@data-cy='children-7']")).click();
		}
	}

	public void setInfantTraveller(String count) {
		if (Integer.valueOf(count) <= 6) {
			driver.findElement(By.xpath("//li[@data-cy='infants-" + count + "']")).click();
		} else {
			driver.findElement(By.xpath("//li[@data-cy='infants-7']")).click();
		}
	}

	public void setTravelClass(String travelClass) {
		driver.findElement(By.xpath(
				"//ul[@class='guestCounter classSelect font12 darkText']//li[contains(text()," + travelClass + ")]"))
				.click();
	}

	public void applyTraveller() {
		driver.findElement(By.xpath("//button[@class='primaryBtn btnApply pushRight']")).click();
	}

	public String getTravelerDetails() {
		return driver.findElement(By.id("travellers")).getAttribute("value");
	}

//	----------------MAIN SEARCH BUTTON OF FLIGHT SEARCH PAGE------------------
	public void search() {
		try {
			driver.findElement(By.xpath("//a[text()='Search']")).click();
		}catch(Exception e) {
			
		}
	}

//	----------ROUND TRIP------
	public void selectRoundTrip() {
		btn_roundTrip.click();
	}

//	------------------------FLIGHT TRACKER BY NUMBER-------------

	public void setFlightStatusByNumber() {
		btn_flightTrackerByFlight.click();
	}

	public Boolean selectDateFlightTracker(String date) {
		btn_flightTrackedDate.click();
		By dateLocator = By.xpath("//div[@aria-label='" + date + "']");
		WebElement dateElement = driver.findElement(dateLocator);

		// ---------- CHECK DISABLED ----------
		String classes = dateElement.getAttribute("class");

		if (classes.contains("DayPicker-Day--disabled")) {
			return false;
		}

		// ---------- CLICK + VERIFY ----------
		dateElement.click();
		return true;
	}

	public void setFlightNumber(String flightNumber) {
		txt_flightTrackerByFlightNumber.sendKeys(flightNumber);
	}

	public String getFlightNumberFieldValue() {
		return txt_flightTrackerByFlightNumber.getAttribute("value");
	}

	public String getDateFieldValue() {
		btn_flightTrackedDate.click();
		return driver.findElement(By.xpath("//div[@class='DayPicker-Day DayPicker-Day--selected']"))
				.getAttribute("aria-label");
	}

//	------------------FLIGHT SEARCH BY ROUTE-----------
	public void setFlightStatusByRoute() {
		btn_flightTrackerByRoute.click();
	}

	public void setFromCityFlightStatusByRoute(String fromCity) {
		try {
			driver.findElement(By.xpath("//label[@for='fisFROM']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("(//input[@placeholder='From'])[2]")).sendKeys(fromCity);
			selectCity(fromCity);
		}catch(Exception e){
			
		}
	}

	public String getFromCityFlightStatusByRoute() {
		return txt_FromCityFlightTracker.getAttribute("value");
	}

	public void setToCityFlightStatusByRoute(String toCity) {
		try {
			driver.findElement(By.xpath("//label[@for='fisTO']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("(//input[@placeholder='To'])[2]")).sendKeys(toCity);
			selectCity(toCity);
		}catch(Exception e){
			
		}
	}

	public String getToCityFlightStatusByRoute() {
		return txt_ToCityFlightTracker.getAttribute("value");
	}

	public void setDateFlightStatusByRoute(String date) throws InterruptedException {
		try {
			driver.findElement(By.xpath("//div[@class='date-field']")).click();
			System.out.println(selectDate(date));
		}catch(Exception e) {
			
		}

	}

	public String getDateSelected() {
		btn_flightTrackedDate.click();
		return driver.findElement(By.xpath("//div[@class='DayPicker-Day DayPicker-Day--selected']"))
				.getAttribute("aria-label");
	}

//	------------BY AIRPORT ------------

	public void setFlightStatusByAirport() {
		btn_flightTrackerByAirport.click();
	}

	public void setAirportFlightStatusByAirport(String airport) {
		try {
			driver.findElement(By.xpath("//label[@for='fisAIRPORT']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("(//input[@placeholder='Airport'])[2]")).sendKeys(airport);
			selectCity(airport);
		}catch(Exception e) {
			
		}
	}

	public String getAirportFlightStatusByAirport() {
		try {
			String actualAirport = driver.findElement(By.xpath("//div[@class='fontSize24 latoBlack appendBottom12']")).getText();
			return actualAirport.substring(actualAirport.lastIndexOf(" "));
		}catch(Exception e) {
			return "Not able to fetch";
		}
	}

	public void setDateFlightStatusByAirport(String date) throws InterruptedException {
		btn_flightTrackedDate.click();
		selectDate(date);
	}

	public String getSelectedDateFlightStatusByAirport() {
		btn_flightTrackedDate.click();
		return driver.findElement(By.xpath("//div[@class='DayPicker-Day DayPicker-Day--selected']"))
				.getAttribute("aria-label");
	}

	// COMMON TO FLIGHT STATUS
	
	public void statusSearch() {
		try {
			driver.findElement(By.xpath("//button[@class='fis-search-button']")).click();
		}catch(Exception e) {
			
		}
	}
	
	public void trackFlight() {
		btn_flightTracker.click();
	}

	public Boolean flightStatusFetched() {
		return wait
				.until(ExpectedConditions.visibilityOf(
						driver.findElement(By.xpath("//div[@class='pageStickyHder flight-status-search']"))))
				.isDisplayed();
	}

	public String getFetchedHeaderFlightStatusByAirport() {
		return wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//div[@class='fontSize24 latoBlack appendBottom12']"))))
				.getText();
	}

	public String getFlightStatusBy() {
		return driver
				.findElement(By
						.xpath("//div[@class='flight-status-search fsw searchWidgetContainer']//li[@class='selected']"))
				.getText();
	}

}
