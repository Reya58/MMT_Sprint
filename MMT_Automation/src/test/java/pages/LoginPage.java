package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	@FindBy(xpath = "//img[@alt='signInByMailButton']")
	WebElement btn_login_vai_mail;
	
	@FindBy(xpath = "//input[@placeholder='Enter Email Address']")
	WebElement txt_email;
	
	@FindBy( xpath = "//button[@data-cy='continueBtn']")
	WebElement btn_continue_aftr_email;
	
	@FindBy( id = "password")
	WebElement txt_password;
	
	@FindBy( xpath = "//button[@data-cy='login']")
	WebElement btn_login;
	
	WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}

	public void enterEmail(String email) {
		btn_login_vai_mail.click();
		txt_email.sendKeys(email);
		btn_continue_aftr_email.click();
	}
	
	public void enterPassword(String password) {
		txt_password.sendKeys(password);
	}
	
	public void clickOnLogin() {
		btn_login.click();
	}
}
