package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlightPaymentPage {

	private WebDriver driver;
	private WebDriverWait wait;

	public FlightPaymentPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	public Boolean isOnPaymentPage() {
		try {
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dt__payment")));
			return element.isDisplayed();
		} catch (Exception e) {
			System.out.println("Payment Page didn't loaded");
			return false;
		}
	}

//	------------------BOOKING SUMMARY--------------
	private String getTravelRoute() {
	    return wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[@data-testid='lob-summary-title']//span[@data-testid='lob-summary-title-text']")))
	            .getText();
	}

	public String getFrom() {
	    String travelRoute = getTravelRoute();
	    return travelRoute.substring(0, travelRoute.indexOf("→"));
	}

	public String getTo() {
	    String travelRoute = getTravelRoute();
	    return travelRoute.substring(travelRoute.indexOf("→") + 1);
	}

	public String getFlightNumber() {
		return wait
				.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//div[@data-testid='lob-summary-info-item-0']")))
				.getText();
	}

	public String getTravelDate() {
		return wait
				.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//div[@data-testid='lob-summary-info-item-1']")))
				.getText();
	}

	public List<WebElement> getBookingDetails() {
		return wait.until(ExpectedConditions.visibilityOfAllElements(
				driver.findElement(By.xpath("//ul[@data-testid='booking-details-list']//li"))));
	}
}
