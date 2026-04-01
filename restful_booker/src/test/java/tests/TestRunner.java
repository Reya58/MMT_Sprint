package tests;

import org.testng.TestNG;

public class TestRunner {

    public static void main(String[] args) {

        TestNG testng = new TestNG();

        testng.setTestClasses(new Class[] {
                AuthTest.class,
                CreateBookingTest.class,
                GetBookingTest.class,
                UpdateBookingTest.class,
                DeleteBookingTest.class,
                PingTest.class
        });

        testng.setDefaultSuiteName("API Suite");
        testng.setDefaultTestName("RestAssured Tests");

        testng.run();
    }
}