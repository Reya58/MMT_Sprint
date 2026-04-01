package stepDefinations;

import java.time.Duration;

//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;

public class LoginSteps {
	WebDriver driver = new ChromeDriver();
	LoginPage login;
	@Given("the user is on the login page")
	public void the_user_is_on_the_login_page() {
	    // Write code here that turns the phrase above into concrete actions
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.manage().window().maximize();
//		driver.get("https://www.google.com/");
//		driver.findElement(By.xpath("//textarea[@title='Search']")).sendKeys("makemytrip",Keys.ENTER);
//		driver.findElement(By.xpath("//a[@href='https://www.makemytrip.com/']")).click();
		
		driver.get("https://www.makemytrip.com/flights");
	}

	@When("the user logs in with {string} and {string}")
	public void the_user_logs_in_with_and(String email, String password) throws InterruptedException {
	    // Write code here that turns the phrase above into concrete actions
	    login = new LoginPage(driver);
	    login.enterEmail(email);
	    login.enterPassword(password);
	    login.clickOnLogin();
	    
	    Thread.sleep(20000);
	}

	@Then("the user should be redirected to the dashboard")
	public void the_user_should_be_redirected_to_the_dashboard() {
//	    // Write code here that turns the phrase above into concrete actions
////	    DashboardPage dashboardPage = new DashboardPage(driver);
//	    String expectedInitials = "MS";
//	    Assert.assertTrue(dashboardPage.isDashboardLoaded());
//	    Assert.assertEquals(dashboardPage.getUserInitials(), expectedInitials);
	}
}
