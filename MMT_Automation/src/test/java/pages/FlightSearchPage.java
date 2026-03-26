package pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
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

	@FindBy(xpath = "//input[@id='toCity'")
	private WebElement selected_toCity;

	@FindBy(xpath = "//div[@id='errorMessage']//span[@data-cy='sameCityError']")
	private WebElement errorMessage;

	WebDriver driver;
	WebDriverWait wait;

	private YearMonth getMonth(By locator, DateTimeFormatter formatter) {
		String text = driver.findElement(locator).getText();
		return YearMonth.parse(text, formatter);
	}

	private void waitForMonthChange(By locator, String oldValue) {
		new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver -> {
			String newValue = driver.findElement(locator).getText();
			return !newValue.equals(oldValue);
		});
	}

	private void dismissCoachmarkIfPresent() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

			WebElement coachmark = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class,'coachmark')]")));
			coachmark.click();

		} catch (Exception ignored) {
			// intentionally ignored
		}
	}

	private void closeLoginPopup() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

			WebElement closeBtn = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-cy='closeModal']")));

			closeBtn.click();

		} catch (Exception e) {
			System.out.println("Login popup not present");
		}
	}

	private void minimizeBanner() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

			WebElement minimizeBtn = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@alt='minimize']")));

			minimizeBtn.click();

		} catch (Exception e) {
			System.out.println("Banner minimize not present");
		}
	}

//	################# PUBLIC FUNCTIONS ##############################

//	----------CONSTRUCTOR----------
	public FlightSearchPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

//	--------------- SLECTING SPECIFIED DATE----------

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

//	-------------- RETURNING SELECTED DATE-------------

	public String getSelectedDate() throws InterruptedException {
		btn_departure_date.click();
		Thread.sleep(2000);
		return driver.findElement(By.xpath("//div[@class='DayPicker-Day DayPicker-Day--selected']"))
				.getAttribute("aria-label");
	}

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
	        wait.until(ExpectedConditions.presenceOfElementLocated(
	                By.cssSelector(".react-autosuggest__suggestion")
	        ));

	        // now verify actual visible suggestions
	        List<WebElement> suggestions = driver.findElements(
	                By.cssSelector(".react-autosuggest__suggestion")
	        );

	        return suggestions.size() > 0 && suggestions.get(0).isDisplayed();

	    } catch (Exception e) {
	        return false;
	    }
	}

	public void selectCity(String city) {
		By option = By.xpath("//span[contains(@class,'revampedCityName') and contains(text(),'" + city + "')]");

		wait.until(ExpectedConditions.elementToBeClickable(option)).click();
	}

	public void search() {
		btn_search.click();
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

	public void handleInterruptions() {
		closeLoginPopup();
		minimizeBanner();
		dismissCoachmarkIfPresent();
	}

}
