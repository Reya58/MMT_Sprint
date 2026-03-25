package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HotelListingsPage {

	WebDriver driver;
	public HotelListingsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
	@FindBy(xpath ="(//input[@aria-label='5 Star']/parent::span/child::label)[2]")
	   private WebElement fiveStarCheckbox;
	
	@FindBy(xpath ="(//input[@aria-label='4 Star']/parent::span/child::label)[2]")
	   private WebElement fourStarCheckbox;
	
	@FindBy(name ="min")
	   private WebElement minPriceField; //send keys
	
	@FindBy(name ="max")
	   private WebElement maxPriceField; //send keys
	
	@FindBy(xpath ="//button[@aria-label='Select Range button']")
	   private WebElement minMaxRangeBtn; 
	
	@FindBy(xpath ="//span[text()='Price (High to Low)']")
	   private WebElement priceHToL;
	
	@FindBy(xpath ="//span[text()='Price (Low to High)']")
	   private WebElement priceLToH;
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getFiveStarCheckbox() {
		return fiveStarCheckbox;
	}

	public void setFiveStarCheckbox(WebElement fiveStarCheckbox) {
		this.fiveStarCheckbox = fiveStarCheckbox;
	}

	public WebElement getFourStarCheckbox() {
		return fourStarCheckbox;
	}

	public void setFourStarCheckbox(WebElement fourStarCheckbox) {
		this.fourStarCheckbox = fourStarCheckbox;
	}

	public WebElement getMinPriceField() {
		return minPriceField;
	}

	public void setMinPriceField(WebElement minPriceField) {
		this.minPriceField = minPriceField;
	}

	public WebElement getMaxPriceField() {
		return maxPriceField;
	}

	public void setMaxPriceField(WebElement maxPriceField) {
		this.maxPriceField = maxPriceField;
	}

	public WebElement getMinMaxRangeBtn() {
		return minMaxRangeBtn;
	}

	public void setMinMaxRangeBtn(WebElement minMaxRangeBtn) {
		this.minMaxRangeBtn = minMaxRangeBtn;
	}

	public WebElement getPriceHToL() {
		return priceHToL;
	}

	public void setPriceHToL(WebElement priceHToL) {
		this.priceHToL = priceHToL;
	}

	public WebElement getPriceLToH() {
		return priceLToH;
	}

	public void setPriceLToH(WebElement priceLToH) {
		this.priceLToH = priceLToH;
	}
	
	//---------------------------------------------------------------------------------
	//Actions
	
	//TS:3
	public void filterBy5Stars()
	{
		getFiveStarCheckbox().click();
	}
	public void filterBy4And5Stars()
	{
		getFiveStarCheckbox().click();
		WebElement element = getFourStarCheckbox();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		element.click();
	}
	
	//TS:4
	public void minMaxPrice(String minPr, String maxPr)
	{
		getMinPriceField().sendKeys(minPr);	
		getMaxPriceField().sendKeys(maxPr);
		getMinMaxRangeBtn().click();
	}
	
	//TS:5
	public void sortHighToLow()
	{
		priceHToL.click();
	}
	public void sortLowToHigh()
	{
		priceLToH.click();
	}
	
	

}
