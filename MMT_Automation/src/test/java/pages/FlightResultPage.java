package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
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

	@FindBy(xpath = "//label[@for='fromCity']//div//span[@class='fswRevampedValue']")
	private WebElement fromCityField;

	@FindBy(xpath = "//label[@for='toCity']//div//span[@class='fswRevampedValue']")
	private WebElement toCityField;

	private String flightCard = "//div[@class=' ']";
//-----------PRIVATE FUNCTION FOR SPECIFC FILTER CONTAINER-----

	// -----FOR SPEACIFIC FILTER CONTAINER---------------
	private By getFilterContainer(String filterName) {
		return By.xpath("//p[text()='" + filterName + "']/ancestor::div[@class='filtersOuter']");
	}

	// ---------FOR GETTING WEBELEMENT OF SPEACIFIC FILTER'S SPEACIFIC
	// OPTION----------
	private WebElement getFilterOptionLabel(String filterName, String option) {
		WebElement container = wait
				.until(ExpectedConditions.visibilityOfElementLocated(getFilterContainer(filterName)));

		return container.findElement(By.xpath(
				".//label[.//p[contains(@class,'checkboxTitle') and contains(normalize-space(.),'" + option + "')]]"));
	}

//	-------------CONSTRUCTOR--------------- 
	public FlightResultPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
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

//	-------- NO FLIGHT FOR ROUTE ERROR-------------
	public Boolean isFlightsAvailable() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(flightCard))).isDisplayed();
	}

// --------	VISIBILITY OF FILTER ------------------

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

	public Boolean isFlightAvailabeAfterFilter() {
		try {
			return !driver.findElement(By.xpath("//p[text()='Too many filters applied!']")).isDisplayed();

		} catch (Exception e) {
			return true;
		}
	}

	public List<String> getFilterOptions(String filterName) {
		WebElement container = driver.findElement(getFilterContainer(filterName));

		List<WebElement> options = container.findElements(By.xpath("//p[@class='checkboxTitle']"));

		return options.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
	}

	public Boolean isFilterAvailable(String filterName) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(getFilterContainer(filterName))).isDisplayed();
	}

	public void selectAOption(String filterName, String filterOption) {
		WebElement label = getFilterOptionLabel(filterName, filterOption);
		WebElement checkbox = label.findElement(By.xpath(".//input"));
//				

		if (!checkbox.isSelected()) {
			checkbox.click();
		}
	}

	public Boolean isOptionSelected(String filterName, String option) {
		WebElement label = getFilterOptionLabel(filterName, option);

		WebElement checkbox = label.findElement(By.xpath("//input"));

		return checkbox.isSelected();
	}

	public void deselectOption(String filterName, String option) {
		WebElement label = getFilterOptionLabel(filterName, option);

		WebElement checkbox = label.findElement(By.xpath("//input"));

		if (checkbox.isSelected()) {
			label.click();
		}
	}

	public void clearAllFilter() {

		WebElement btn = wait.until(ExpectedConditions.visibilityOf(btn_clearAllFilter));

		try {
			btn.click();
		} catch (Exception e) {

		}
	}
//	----------------------- AIRLINE FILTER SPEACIFIC FUNTIONS ----------------

//	------------------------DEPARTURE AIRPORT----------------

//	----------------ARRIVAL AIRPORT-------------------

//	--------------STOPS FILTER----------------

	public Boolean isStopFilterPresnt() {
		return isFilterAvailable("Stops From" + fromCityField.getText());
	}

	public List<String> getStopsAvailable() {
		return getFilterOptions("Stops From" + fromCityField.getText());
	}

	public Boolean isStopSelected(String stop) {
		return isOptionSelected("Stops From" + fromCityField.getText(), stop);
	}

	public void deselectStop(String stop) {
		deselectOption("Stops From" + fromCityField.getText(), stop);
	}

//	---------DEPARTURE TIME SLOTS------------------
	public Boolean isDepartureTimeslotsAvailable() {
		return isFilterAvailable("Departure From" + fromCityField.getText());
	}

	public List<String> getAvailableDepartureTimeSlot() {
		return getFilterOptions("Departure From" + fromCityField.getText());
	}

	public Boolean isDepartureTimeslotSelected(String timeSlot) {
		return isOptionSelected("Departure From" + fromCityField.getText(), timeSlot);
	}

	public void deselectDepartureTimeslot(String timeSlot) {
		deselectOption("Departure From" + fromCityField.getText(), timeSlot);
	}

//	--------------ARRIVAL TIME SLOTS---------------

	public Boolean isArrivalTimeslotsAvailable() {
		return isFilterAvailable("Arrival at" + toCityField.getText());
	}

	public List<String> getAvailableArrivalTimeSlot() {
		return getFilterOptions("Arrival at" + toCityField.getText());
	}

	public Boolean isArrivalTimeslotSelected(String timeSlot) {
		return isOptionSelected("Arrival at" + toCityField.getText(), timeSlot);
	}

	public void deselectArrivalTimeslot(String timeSlot) {
		deselectOption("Arrival at" + toCityField.getText(), timeSlot);
	}

//	----------------------AIRCRAFT SIZE----------------

//	---------------

	public Boolean isCheapestTabSelected() {
		return wait
				.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//div[@class='sortTabHeadList makeFlex active']//div//p[text()='Cheapest']")))
				.isEnabled();
	}

	public void selectCheapestTab() {
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[@class='sortTabHeadList makeFlex active']//div//p[text()='Cheapest']"))).click();
	}

	public void selectCheapestFareType() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[text()='BOOK NOW'])[1]"))).click();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
//	------------------FLIGHTS CARD----------------------

	String airlineName = "//div[@class=' ']//p[@class='boldFont blackText airlineName']";

	String fromCityName = "//div[@class=' ']//div[@class='flexOne timeInfoLeft']//p[@class='blackText']";

	String toCityName = "//div[@class=' ']//div[@class='flexOne timeInfoRight']//p[@class='blackText']";
	String departureTime = "//div[@class=' ']//div[@class='flexOne timeInfoLeft']//p[@class='appendBottom2 flightTimeInfo']";

	String arrivalTime = "//div[@class=' ']//div[@class='flexOne timeInfoRight']//p[@class='appendBottom2 flightTimeInfo']";

	String prices = "//div[@class=' ']//div[@class='priceSection  ']//span[@class=' fontSize18 blackFont']";

	String viewPriceButton = "//div[@class=' ']//div[@class='priceSection  ']//button";

	String priceSelectionPopUp = "//div[@class='fareFamilyOverlay fareFamilyPopupWrapper']";

	String bookSaver = "//p[text()='SAVER']/parent::div/parent::div/parent::div//button";

	String bookMMTFare = "//p[text()='FARE BY MAKEMYTRIP']/parent::div/parent::div/parent::div//button";

	String bookFlexi = "//p[text()='FLEXI']/parent::div/parent::div/parent::div//button";

	String noFlightsError = "//p[text()='Sorry, Flights Not Found.']";

	String searchButton = "//button[@class='fswRevampedSearchButton ']";

	String s = "//p[text()='Too many filters applied!']";

	public List<Map<String, String>> getAllFlightDetails() {

		List<Map<String, String>> result = new ArrayList<>();
		Set<String> seen = new HashSet<>();

		By cards = By.xpath(flightCard);

		int prevSize = 0;

		while (true) {

			List<WebElement> elements = driver.findElements(cards);

			for (WebElement card : elements) {

				try {
					String index = driver.findElement(By.xpath("//div[@class=' ']/parent::div"))
							.getAttribute("data-index");

					// avoid duplicate processing
					if (seen.contains(index))
						continue;
					seen.add(index);

					Map<String, String> data = new HashMap<>();

					data.put("index", index);

					data.put("airline", card.findElement(By.xpath(".//p[contains(@class,'airlineName')]")).getText());

					data.put("from",
							card.findElement(By
									.xpath(".//div[contains(@class,'timeInfoLeft')]//p[contains(@class,'blackText')]"))
									.getText());

					data.put("to",
							card.findElement(By
									.xpath(".//div[contains(@class,'timeInfoRight')]//p[contains(@class,'blackText')]"))
									.getText());

					data.put("departure",
							card.findElement(By.xpath(
									".//div[contains(@class,'timeInfoLeft')]//p[contains(@class,'flightTimeInfo')]"))
									.getText());

					data.put("arrival",
							card.findElement(By.xpath(
									".//div[contains(@class,'timeInfoRight')]//p[contains(@class,'flightTimeInfo')]"))
									.getText());

					data.put("price", card.findElement(By.xpath(".//span[contains(@class,'blackFont')]")).getText());

					result.add(data);

				} catch (StaleElementReferenceException | NoSuchElementException e) {
					// skip problematic cards (groupBooking etc.)
				}
			}

			// scroll
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1000)");

			try {
				Thread.sleep(1200);
			} catch (Exception e) {
			}

			if (seen.size() == prevSize)
				break;

			prevSize = seen.size();
		}

		return result;
	}

	public void selectFareByMMT() throws InterruptedException {
		Thread.sleep(3000);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='BOOK NOW']"))).click();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void selectFirstFlight() {
		try {
			Thread.sleep(4000);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(viewPriceButton))).click();
		} catch (Exception e) {
			System.out.println("button isn't clickable ");
		}
	}

	public String fromCityForwardTripOfRoundTrip() {
		String route = driver.findElement(By.xpath("(//div[@class='topSectionPaneView appendBottom10'])[1]")).getText();

		return route.substring(0, route.indexOf("→"));
	}

	public String toCityForwardTripOfRoundTrip() {
		String route = driver.findElement(By.xpath("(//div[@class='topSectionPaneView appendBottom10'])[1]")).getText();
		return route.substring(route.indexOf("→") + 2, route.indexOf(" ", route.indexOf("→") + 2)).trim();
	}

	public String fromCityReturnTripOfRoundTrip() {
		String route = driver.findElement(By.xpath("(//div[@class='topSectionPaneView appendBottom10'])[2]")).getText();

		return route.substring(0, route.indexOf("→"));
	}

	public String toCityReturnTripOfRoundTrip() {
		String route = driver.findElement(By.xpath("(//div[@class='topSectionPaneView appendBottom10'])[2]")).getText();

		return route.substring(route.indexOf("→") + 2, route.indexOf(" ", route.indexOf("→") + 2)).trim();
	}

	public void bookRoundTrip() {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Book Now']"))).click();
	}

	public void selectFareTypeRoundTrip() {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='CONTINUE']"))).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='BOOK NOW']"))).click();
	}
}
