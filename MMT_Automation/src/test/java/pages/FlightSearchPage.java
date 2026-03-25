package pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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

	WebDriver driver;

	private YearMonth getMonth(By locator, DateTimeFormatter formatter) {
		String text = driver.findElement(locator).getText(); // "April 2026"
		return YearMonth.parse(text, formatter);
	}

	private void waitForMonthChange(By locator, String oldValue) {
		new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver -> {
			String newValue = driver.findElement(locator).getText();
			return !newValue.equals(oldValue);
		});
	}

//	################# PUBLIC FUNCTIONS ##############################

//	----------CONSTRUCTOR----------
	public FlightSearchPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

//	--------------- SLECTING SPECIFIED DATE----------

	public boolean selectDate(String inputDate) throws InterruptedException {
		Thread.sleep(1000);
//		btn_departure_date.click();
		Thread.sleep(3000);
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

//	-------------- RETURNING SELECTED PAGE-------------
	public String getSelectedDate() {
		return driver.findElement(By.xpath("//p[@data-cy='departureDate']")).getText();
	}

	public void setFromCity(String fromCity) throws InterruptedException {
		btn_frmCity.click();
		Thread.sleep(15000);
		txt_fromCity.sendKeys(fromCity);
	}

	public void setToCity(String toCity) throws InterruptedException {
		btn_toCity.click();
		Thread.sleep(15000);
		txt_toCity.sendKeys(toCity);
	}

	public Boolean isSuggestionDisplayed() {
		return driver.findElement(By.className("react-autosuggest__suggestions-list")).isEnabled();
	}

	public void selectCity(String city) throws InterruptedException {
		By optionsLocator = By.cssSelector("li[role='option']");

		List<WebElement> options = driver.findElements(optionsLocator);
		Thread.sleep(1500);
		for (WebElement option : options) {
			String text = option.getText().trim();
//			System.out.println(text);
			if (text.contains(city)) {
				option.click();
				return;
			}
		}

	}

	public void search() {
		btn_search.click();
	}

}
