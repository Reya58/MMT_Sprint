package pages;

import java.time.Duration;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomeStays {

    WebDriver driver;

    // Constructor
    public HomeStays(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    @FindBy(xpath="//div[@class='tp-dt-header-icon'][2]")
    private WebElement popup;
    
    @FindBy(xpath="//span[@class=\"commonModal__close\"]")
    private WebElement popup1;
    
    @FindBy(xpath="//input[@id=\"city\"]")
    private WebElement location_click;
    
    @FindBy(xpath="//input[@placeholder=\"Where do you want to stay?\"]")
    WebElement location_text;
    
    @FindBy(xpath = "//div[@aria-label=\"Sat Mar 28 2026\"]") // change dynamically if needed
    WebElement checkInDate;

    // Check-out date
    @FindBy(xpath = "//div[@aria-label=\"Sun Mar 29 2026\"]")
    WebElement checkOutDate;
      
    @FindBy(xpath="//button[@class=\"counter__button counter__button--increment\"][1]")
    WebElement adults;
    
    @FindBy(xpath="//button[@class=\"counter__button counter__button--increment\"][2]")
    WebElement childs;
    
    @FindBy(xpath="//button[@class=\"primaryBtn btnApplyNew pushRight capText\"]")
    WebElement apply;
    
    @FindBy(xpath="//button[@id=\"hsw_search_button\"]")
    WebElement search;
    
    
    public void close_popup() {
    	popup.click();
    	popup1.click();
    }
    
    public void enterLocation(String city) throws InterruptedException {

        // Click to open search box
        location_click.click();

        Thread.sleep(2000); // wait for new input box

        // Type in actual input field
        location_text.sendKeys(city);

        Thread.sleep(2000);

        location_text.sendKeys(Keys.ARROW_DOWN);
        location_text.sendKeys(Keys.ENTER);
    }

    // Select dates
    public void selectDates() {
        checkInDate.click();
        checkOutDate.click();
    }

    // Select adults
    public void selectAdults(int count) {
        for(int i = 1; i < count; i++) {
            adults.click();
        }
        
    }

    // Select children
    public void selectChildren(int count) {
        for(int i = 0; i < count; i++) {
            childs.click();
        }
        apply.click();
    }

    // Click search
    public void clickSearch() {
        search.click();
    }
   
}