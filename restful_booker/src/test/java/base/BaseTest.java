package base;

import com.aventstack.extentreports.*;
import io.restassured.RestAssured;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExtentManager;

import java.lang.reflect.Method;

public class BaseTest {

    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @BeforeSuite
    public void setupReport() {
        extent = ExtentManager.getInstance();
    }

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @BeforeMethod
    public void startTest(Method method) {
        ExtentTest extentTest = extent.createTest(method.getName());
        test.set(extentTest);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {
            test.get().fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.get().pass("Test Passed");
        } else {
            test.get().skip("Test Skipped");
        }
    }

    @AfterSuite
    public void flushReport() {
        extent.flush();
    }
}