package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class DeleteBookingTest extends BaseTest {

    @Test
    public void deleteBooking() {

        // Step 1: Create Token
        String token =
                given()
                        .contentType("application/json")
                        .body("{ \"username\": \"admin\", \"password\": \"password123\" }")
                .when()
                        .post("/auth")
                .then()
                        .statusCode(200)
                        .extract()
                        .path("token");

        // Step 2: Create Booking (so we have dynamic ID)
        int bookingId =
                given()
                        .contentType("application/json")
                        .body("{\n" +
                                "    \"firstname\" : \"Test\",\n" +
                                "    \"lastname\" : \"User\",\n" +
                                "    \"totalprice\" : 500,\n" +
                                "    \"depositpaid\" : true,\n" +
                                "    \"bookingdates\" : {\n" +
                                "        \"checkin\" : \"2024-01-01\",\n" +
                                "        \"checkout\" : \"2024-01-05\"\n" +
                                "    },\n" +
                                "    \"additionalneeds\" : \"Breakfast\"\n" +
                                "}")
                .when()
                        .post("/booking")
                .then()
                        .statusCode(200)
                        .extract()
                        .path("bookingid");

        // Step 3: Delete Booking
        Response response =
                given()
                        .header("Cookie", "token=" + token)
                .when()
                        .delete("/booking/" + bookingId);

        // Step 4: Validate Response
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.getBody().asString(), "Created");

        // Step 5 (Optional): Verify Deletion
        given()
        .when()
                .get("/booking/" + bookingId)
        .then()
                .statusCode(404);
    }
}