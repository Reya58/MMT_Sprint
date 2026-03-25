package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PropertyPage {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public PropertyPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // ================= LOCATORS =================

    @FindBy(xpath = "//h1[contains(@class,'hotelName')]")
    WebElement propertyTitle;

    @FindBy(xpath = "//p[contains(@class,'reviewText')]")
    List<WebElement> reviewList;

    @FindBy(xpath = "//button[contains(@class,'wishlist')]")
    WebElement wishlistBtn;

    // ❌ DO NOT USE directly (can become stale)
    // @FindBy(xpath = "//button[contains(text(),'BOOK')]")
    // WebElement bookNowBtn;

    // ================= METHODS =================

    public boolean isPropertyPageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(propertyTitle)).isDisplayed();
    }

    // View Reviews
    public void viewReviews() {

        By reviewLocator = By.xpath("(//p[contains(@class,'reviewText')])[1]");

        try {
            WebElement review = wait.until(
                    ExpectedConditions.presenceOfElementLocated(reviewLocator)
            );

            js.executeScript("arguments[0].scrollIntoView({block:'center'});", review);

            System.out.println("First Review: " + review.getText());

        } catch (Exception e) {
            System.out.println("No reviews found or stale issue handled");
        }
    }

    // Add to Wishlist
    public void addToWishlist() {
        wait.until(ExpectedConditions.elementToBeClickable(wishlistBtn)).click();
    }

    // ✅ FIXED: Stale-safe Book Now
    public void clickBookNow() {

        By bookNowLocator = By.xpath("//button[text()='BOOK THIS NOW']");

        WebElement bookNow = wait.until(
                ExpectedConditions.presenceOfElementLocated(bookNowLocator)
        );

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", bookNow);

       // wait.until(ExpectedConditions.elementToBeClickable(bookNow)).click();
        bookNow.click();

        System.out.println("Clicked Book Now successfully");
    }
}