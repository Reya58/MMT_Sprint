package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HotelSearchPage {

		
//Hotel search
//---------------------------------------------------------------------------------------------------------------
//		WebDriver driver=new FirefoxDriver();
//		driver.manage().window().maximize();
//		driver.get("https://www.makemytrip.com/?cc=in&redirectedBy=gl");
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		
		WebDriver driver;
		public HotelSearchPage(WebDriver driver) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	    }
		
		@FindBy(xpath ="//span[@class='commonModal__close']")
		   private WebElement loginCloseBtn;
		
		@FindBy(xpath ="(//div[@class='tp-dt-header-icon'])[2]")
		   private WebElement chatbotCloseBtn;
		
//		@FindBy(xpath ="(//span[text()='Hotels'])[1]")
//		   private WebElement hotelsSection;
		
		@FindBy(linkText="Hotels")
		private WebElement hotelsSection;
		
		@FindBy(id ="city")
		   private WebElement destinationField; 
		
		@FindBy(xpath ="//input[@title='Where do you want to stay?']")
		   private WebElement destination; //send keys, arrow down, key enter
		
		@FindBy(xpath="//span[@aria-label='Next Month']")
			private WebElement nextMonthBtn;
		
		@FindBy(xpath ="//div[@aria-label='Wed Apr 08 2026']/child::span")
		   private WebElement checkInDate; 

		@FindBy(xpath ="//div[@aria-label='Fri Apr 10 2026']/child::span")
		   private WebElement checkOutDate; 
		
		@FindBy(xpath ="//button[@data-cy='RoomsGuestsNew_327']")
		   private WebElement roomsAndGuests; 
		
		
		@FindBy(id="hsw_search_button")
			private WebElement searchBtn;
		
		
		@FindBy(xpath="//p[@class='hw__searchResultNotFoundText']")
			private WebElement nonExistentCityMsg;
		
		@FindBy(xpath="//div[@class='chevronIconRight']")
			private WebElement destSearch;
		
		
		public WebDriver getDriver() {
			return driver;
		}
		
		public WebElement getNonExistentCityMsg()
		{
			return nonExistentCityMsg;
		}
		
		public WebElement getDestSearch()
		{
			return destSearch;
		}

		public void setDriver(WebDriver driver) {
			this.driver = driver;
		}

		public WebElement getLoginCloseBtn() {
			return loginCloseBtn;
		}

		public void setLoginCloseBtn(WebElement loginCloseBtn) {
			this.loginCloseBtn = loginCloseBtn;
		}

		public WebElement getChatbotCloseBtn() {
			return chatbotCloseBtn;
		}

		public void setChatbotCloseBtn(WebElement chatbotCloseBtn) {
			this.chatbotCloseBtn = chatbotCloseBtn;
		}

		public WebElement getHotelsSection() {
			return hotelsSection;
		}

		public void setHotelsSection(WebElement hotelsSection) {
			this.hotelsSection = hotelsSection;
		}

		public WebElement getDestinationField() {
			return destinationField;
		}

		public void setDestinationField(WebElement destinationField) {
			this.destinationField = destinationField;
		}

		public WebElement getDestination() {
			return destination;
		}

		public void setDestination(WebElement destination) {
			this.destination = destination;
		}
		public WebElement getNextMonthBtn()
		{
			return nextMonthBtn;
		}
		public WebElement getCheckInDate() {
			return checkInDate;
		}

		public void setCheckInDate(WebElement checkInDate) {
			this.checkInDate = checkInDate;
		}

		public WebElement getCheckOutDate() {
			return checkOutDate;
		}

		public void setCheckOutDate(WebElement checkOutDate) {
			this.checkOutDate = checkOutDate;
		}

		public WebElement getRoomsAndGuests() {
			return roomsAndGuests;
		}

		public void setRoomsAndGuests(WebElement roomsAndGuests) {
			this.roomsAndGuests = roomsAndGuests;
		}

		public WebElement getSearchBtn() {
			return searchBtn;
		}

		public void setSearchBtn(WebElement searchBtn) {
			this.searchBtn = searchBtn;
		}
		
		
		//------------------------------------------------------------------
		//Actions
		
		//Pre-req
		public void preReq() throws InterruptedException
		{
			getLoginCloseBtn().click();
			Thread.sleep(3000);
			getChatbotCloseBtn().click();
			getHotelsSection().click();
		}
		
		public void enterDestination(String dest) throws InterruptedException
		{
			getDestinationField().click();
			getDestination().sendKeys(dest);
			Thread.sleep(3000);
			getDestination().sendKeys(Keys.ARROW_DOWN);
			Thread.sleep(3000);
			getDestination().sendKeys(Keys.ENTER);
			Thread.sleep(2000);
		}
		
		public void enterNonExistentDestination(String dest) throws InterruptedException
		{
			getDestinationField().click();
			getDestination().sendKeys(dest);
			Thread.sleep(3000);
		}
		
		public void enterSpecialCharDestination(String dest)
		{
			getDestinationField().click();
			getDestination().sendKeys(dest);
			getDestSearch().click();		
		}
		
		public void enterCheckInDate(String date) throws InterruptedException
		{
			getNextMonthBtn().click();
			Thread.sleep(2000);
			getCheckInDate().click();
		}
		
		public void enterCheckOutDate(String date)
		{
			getCheckOutDate().click();
		}
		
		public void enterRoomsAndGuests(String room, String adults) throws InterruptedException
		{
			WebElement element = driver.findElement(By.xpath("//div[@class='travellingWithPets__container']"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(2000);
			getRoomsAndGuests().click();
			Thread.sleep(2000);
			WebElement element2 = driver.findElement(By.xpath("//img[@alt='Make My Trip']"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element2);
			Thread.sleep(2000);
		}
		
		public void clickSearchBtn()
		{
			getSearchBtn().click();
		}
		//TS: 1
		public void basicHotelSearch(String dest) throws InterruptedException
		{
			getDestinationField().click();
			getDestination().sendKeys(dest);
			Thread.sleep(3000);
			getDestination().sendKeys(Keys.ARROW_DOWN);
			Thread.sleep(3000);
			getDestination().sendKeys(Keys.ENTER);
			getCheckInDate().click();
			getCheckOutDate().click();
			getRoomsAndGuests().click();
			getSearchBtn().click();
		}
		public void landmarkSearch(String landmark) throws InterruptedException
		{
			getDestinationField().click();
			getDestination().sendKeys(landmark);
			Thread.sleep(3000);
			getDestination().sendKeys(Keys.ARROW_DOWN);
			Thread.sleep(3000);
			getDestination().sendKeys(Keys.ENTER);
			getCheckInDate().click();
			getCheckOutDate().click();
			getRoomsAndGuests().click();
			getSearchBtn().click();
		}
		public String nonExistentCity(String city) throws InterruptedException
		{
			getDestinationField().click();
			getDestination().sendKeys(city);
			Thread.sleep(3000);
			getDestination().sendKeys(Keys.ARROW_DOWN);
			Thread.sleep(3000);
			getDestination().sendKeys(Keys.ENTER);
			Thread.sleep(3000);	
			return getNonExistentCityMsg().getText(); //No Results Found
		}
		public void specialCharacterDestination(String city) throws InterruptedException
		{
			getDestinationField().click();
			getDestination().sendKeys(city);
			Thread.sleep(3000);
			getDestSearch().click();
			Thread.sleep(3000);
			
		}
		public void validDate(String dest) throws InterruptedException
		{
			getDestinationField().click();
			getDestination().sendKeys(dest);
			Thread.sleep(3000);
			getDestination().sendKeys(Keys.ARROW_DOWN);
			Thread.sleep(3000);
			getDestination().sendKeys(Keys.ENTER);
			getCheckInDate().click();
			getCheckOutDate().click();
			getRoomsAndGuests().click();
			getSearchBtn().click();
		}
		

	}


