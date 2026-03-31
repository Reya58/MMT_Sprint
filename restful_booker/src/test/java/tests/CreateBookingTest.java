package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.*;

import static io.restassured.RestAssured.*;

public class CreateBookingTest extends BaseTest {

    @Test
    public void createBooking() {

        BookingDates dates = new BookingDates("2024-01-01", "2024-01-05");

        BookingRequest request = new BookingRequest(
                "Biswayan", "Nath", 1000, true, dates, "Breakfast"
        );

        BookingResponse response =
                given()
                        .contentType("application/json")
                        .body(request)
                .when()
                        .post("/booking")
                .then()
                        .statusCode(200)
                        .extract()
                        .as(BookingResponse.class);

        Assert.assertTrue(response.getBookingid() > 0);
        
        BookingRequest booking = response.getBooking();
        Assert.assertEquals(booking.getFirstname(), request.getFirstname());
        Assert.assertEquals(booking.getLastname(), request.getLastname());
        Assert.assertEquals(booking.getTotalprice(), request.getTotalprice());
        Assert.assertEquals(booking.isDepositpaid(), request.isDepositpaid());
        Assert.assertEquals(booking.getBookingdates().getCheckin(), request.getBookingdates().getCheckin());
        Assert.assertEquals(booking.getBookingdates().getCheckout(), request.getBookingdates().getCheckout());
        Assert.assertEquals(booking.getAdditionalneeds(),request.getAdditionalneeds());
    }
}