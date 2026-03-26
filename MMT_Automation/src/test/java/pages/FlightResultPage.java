package pages;

import java.time.Duration;

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

	public FlightResultPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(4));
	}

	public Boolean isOnSearchResultPage() {

		try {
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("flightBody")));
			return element.isDisplayed();

		} catch (Exception e) {
			return false;
		}
	}

}
