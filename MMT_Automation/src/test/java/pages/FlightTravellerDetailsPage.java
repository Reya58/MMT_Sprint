package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlightTravellerDetailsPage {
	private WebDriver driver;
	private WebDriverWait wait;

	public FlightTravellerDetailsPage(WebDriver driver) {
		String parentWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();

		for (String window : allWindows) {
			if (!window.equals(parentWindow)) {
				driver.switchTo().window(window);
				break;
			}
		}
		this.driver = driver;

		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	private By greyOverlay = By.className("OverlayGreyBg");
	private String commonOverlay = "//*[contains(@class, 'commonOverlay')]";

	/**
	 * Utility: Waits for any loading overlays to disappear. This prevents
	 * ElementClickInterceptedException.
	 */
	private void waitForOverlayToDisappear() {
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(greyOverlay));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(commonOverlay)));
		} catch (Exception e) {
			// If they aren't present or already gone, we continue
		}
	}

	private void scrollTo(By locator) {
		WebElement elem = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", elem);

//		Actions act = new Actions(driver);
//		act.moveToElement(driver.findElement(locator)).perform();
////		act.scrollToElement(driver.findElement(locator)).perform();
	}

	// ----------------- FLIGHT INFO METHODS -----------------

	public List<String> getAirlines() {
		List<WebElement> headers = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("flightItenaryHdr")));
		List<String> airlines = new ArrayList<>();
		for (WebElement header : headers) {
			String text = header.getText().trim();
			String airline = text.substring(0, text.lastIndexOf(" "));
			airlines.add(airline);
		}
		return airlines;
	}

	public List<String> getTravelClasses() {
		List<WebElement> headers = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("flightItenaryHdr")));
		List<String> classes = new ArrayList<>();
		for (WebElement header : headers) {
			String text = header.getText().trim();
			String travelClass = text.substring(text.lastIndexOf(" ") + 1);
			classes.add(travelClass);
		}
		return classes;
	}

	// ----------------- INTERACTION METHODS INPUT -----------------

	public void selectNoInsurance() {
		waitForOverlayToDisappear();
		scrollTo(By.id("INSURANCE"));

		By noOptionLocator = By.xpath(
				"//div[@id='INSURANCE']//label[contains(.,'No')] | //div[@id='INSURANCE']//p[contains(text(),'No')]");

		WebElement noOption = wait.until(ExpectedConditions.elementToBeClickable(noOptionLocator));
		noOption.click();
	}

	public void addTraveller() {
		waitForOverlayToDisappear();
		scrollTo(By.id("TRAVELLER_DETAIL"));

		WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(By.className("addTravellerBtn")));
		addBtn.click();
	}

	public void enterFirstName(String name) {
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='First & Middle Name']")))
				.sendKeys(name);
	}

	public void enterLastName(String name) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Last Name']")))
				.sendKeys(name);
	}

	public void setGender(String gender) {
		String genderValue = gender.toUpperCase();
		// We click the label or the input based on what is clickable
		WebElement genderRadio = wait.until(ExpectedConditions.elementToBeClickable(By
				.xpath("//input[@value='" + genderValue + "']/parent::label | //input[@value='" + genderValue + "']")));
		genderRadio.click();
	}

	public void setCountryCode(String code) {
		WebElement input = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='dropdown__input']//input)[1]")));
		input.sendKeys(code);
		input.sendKeys(Keys.ENTER);
	}

	public void setMobileOfTraveller(String mobile) {
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Mobile No(Optional)']")))
				.sendKeys(mobile);
	}

	public void setEmailOfTraveller(String email) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Email(Optional)']")))
				.sendKeys(email);
	}

	public void setContryCodeBookingDetails(String code) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='Country Code']//input")))
				.sendKeys(code, Keys.ENTER);
	}

	public void setMobileBookingDetails(String mobile) {
		scrollTo(By.id("BILLING_ADDRESS"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@class='bookingDetailsForm']//input[@placeholder='Mobile No']"))).sendKeys(mobile);
	}

	public void setEmailBookingDetails(String email) {
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@id='contactDetails']//div[@id='Email']//input")))
				.sendKeys(email);

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='emailIds-dropdown']//div"))).click();
	}

	public void setState(String state) {
		try {

			WebElement state_input = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='BILLING_ADDRESS']//input")));
			state_input.click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[@id='BILLING_ADDRESS']//ul//li[text()='" + state + "']"))).click();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void saveGSTDetails() {
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[@id='BILLING_ADDRESS']//div[@class='checkboxWithLblWpr__checkboxCtr']"))).click();
	}

	public Boolean takeSeatReccomended() {
		try {
			scrollTo(By.id("SEATS_N_MEALS"));
//			waitForOverlayToDisappear();
			WebElement yesBtn = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//p[@class='seatBookingOverlayCta']//button[text()='Yes, Please']")));
			yesBtn.click();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void selectSeat() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"//div[contains(@id,'DEL$')]//div[contains(@class,'seatBlock') and contains(@class,'pointer')]")))
					.click();

		} catch (Exception e) {

		}
	}

	public void addMeal() {
		try {
			dismissAllPopups();

			scrollTo(By.id("SEATS_N_MEALS"));

			waitForOverlayToDisappear();

			By mealBtnLocator = By.xpath("(//button[contains(@class,'fltMealAddBtn')])[1]");
			WebElement mealBtn = wait.until(ExpectedConditions.presenceOfElementLocated(mealBtnLocator));

			mealBtn.click();

			System.out.println("Meal selected successfully");

		} catch (Exception e) {
			System.out.println("Meal wasn't selected. Error: " + e.getMessage());
		}
	}

	public void setCabs() {
		dismissAllPopups();
		scrollTo(By.xpath("//div[@id='BYPASS_CABS']"));
		waitForOverlayToDisappear();
	}

//-------------	BUTTONS---------------
	public void clickContinue() {
		try {
			waitForOverlayToDisappear();

			By continueBtn = By.xpath("//button[contains(text(),'Continue')]");

			WebElement elem = wait.until(ExpectedConditions.presenceOfElementLocated(continueBtn));

			// Scroll into view reliably
			scrollTo(continueBtn);

			// Wait until it's actually clickable
			wait.until(ExpectedConditions.elementToBeClickable(elem));

			// Click
			elem.click();

		} catch (Exception e) {
			System.out.println("Continue button not found: " + e.getMessage());
		}
	}

	public void reviewTravelerDetails() {
		try {
			// This is a popup that appears AFTER clicking continue
			waitForOverlayToDisappear();
			driver.findElement(By.xpath("//div[@class='commonOverlay']//h3[text()='Review Details']"));

			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//div[@class='commonOverlay']//button[text()='CONFIRM']"))).click();
		} catch (Exception e) {
			System.out.println("Confirmation popup did not appear.");
		}
	}

	public void addressEmergencySeatPopUp() {
		try {
			waitForOverlayToDisappear();
			driver.findElement(
					By.xpath("//div[@class='commonOverlay']//h3[text()='You have selected an Emergency Exit Seat']"));

			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='commonOverlay']//button")))
					.click();
			System.out.println("Emergency Seat PopUp Addressed");
		} catch (Exception e) {
			System.out.println("No Emergency Seat Popup");
		}
	}

	public void addressZeroCancelationPopup() {
		try {
			driver.findElement(By.xpath(commonOverlay));
			WebElement btn = driver.findElement(By.xpath("(//div[@class='commonOverlay']//button)[2]"));
			btn.click();

			System.out.println("Zero Cancelation Popup Addesssed");

		} catch (Exception e) {
			System.out.println("No Zero Cancelation Popup");
		}

	}

	public void clickOnProceedToPayment() {
//		dismissAllPopups(); // One last check before payment
//		addressUpgradePopups();
//		addressTripSummaryPopup();
		try {
			By locator = By.xpath("//button[text()='Proceed to pay']");
			scrollTo(locator);
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		} catch (Exception e) {
			System.out.println("Proceed to payment not clickable" + e.getMessage());
		}
	}

	public void addressTripSummaryPopup() {
		try {
			wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='commonOverlay  trip-summary-overlay']")));

			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//div[@class='commonOverlay  trip-summary-overlay']//a[@class='trip-summary-cta']")))
					.click();

		} catch (Exception e) {
			System.out.println("Trip Summary didn't appeared");
		}
	}

	public void addressFreeDatePopups() {
		try {
			driver.findElement(By.xpath("//div[@class='commonOverlay']"));

			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("(//div[@class='marketing-popup-ctas']//button)[2]"))).click();

		} catch (Exception e) {
			System.out.println("Free Date Change popup didn't appeared");
		}
	}

	public void addressFlexiFlyPopups() {
		try {
			driver.findElement(By.xpath("//div[@class='commonOverlay']"));

			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("(//div[@class='marketing-popup-ctas']//button)[2]"))).click();

		} catch (Exception e) {
			System.out.println("FlexiFly Popups didn't appeared");
		}
	}

	public void addressUpgradePopups() {
		try {
			waitForOverlayToDisappear();

			driver.findElement(By.xpath("//div[@class='commonOverlay  dynamicUpgradeOverlay']"));

			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//div[@class='commonOverlay  dynamicUpgradeOverlay']//button")))
					.click();

		} catch (Exception e) {
			System.out.println("Upgrade popup doesn't appeared");
		}
	}

	public void addExtraBaggage() {
		try {

			waitForOverlayToDisappear();
			scrollTo(By.xpath("//button[text()='ADD BAGGAGE']"));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='ADD BAGGAGE']"))).click();

			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//div[@class='baggageAddonWrapper']//span[@class='qtyActions']")))
					.click();

			waitForOverlayToDisappear();

			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='DONE']"))).click();

			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//div[@class='baggageAddonWrapper']//span[@class='qtyActions']")))
					.click();

			waitForOverlayToDisappear();

			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='DONE']"))).click();

		} catch (Exception e) {
			System.out.println("Add Baggeg button not present");
		}
	}

	public void addressTripProtectionPopUp() {
		try {
			waitForOverlayToDisappear();

			driver.findElement(By.xpath("//div[@class='commonOverlay  dynamicUpgradeOverlay']"));

			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='commonOverlay']//button)[2]")))
					.click();

		} catch (Exception e) {
			System.out.println("Trip Protection Didn't appeared");
		}
	}

	public void dismissAllPopups() {
		try {
			List<WebElement> buttons = driver.findElements(By.xpath(commonOverlay + "//button"));

			if (buttons.size() == 2) {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(" + commonOverlay + "//button" + ")[2]")))
						.click();
			} else {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(" + commonOverlay + "//button" + ")[1]")))
						.click();
			}
		} catch (Exception e) {
//			System.out.println(e.getMessage());
		}

	}
}
