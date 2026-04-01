package tests;

import base.BaseTest;
import io.restassured.response.Response;
import pojo.BookingDates;
import pojo.BookingRequest;

import static io.restassured.RestAssured.*;

import org.testng.Assert;
import org.testng.annotations.Test;

public class GetBookingTest extends BaseTest {
	
	BookingDates ob=new BookingDates("2023-01-01", "2024-01-01");
	BookingRequest br=new BookingRequest("Susan", "Brown", 1000, true, ob, "Breakfast");
	
	@Test
	public void validateGetAllBookings() {
	    Response response = given()
	           
	        .when()
	            .get("/booking")
	        .then()
	            .extract().response();

	    // Validations
	    Assert.assertEquals(response.getStatusCode(), 200);
	    Assert.assertTrue(response.jsonPath().getList("bookingid").size() > 0);
	}
	
	@Test
	public void validateGetByFirstName() {
	    Response response = given()
	            
	            .queryParam("firstname", br.getFirstname())
	        .when()
	            .get("/booking")
	        .then()
	            .extract().response();

	    Assert.assertEquals(response.getStatusCode(), 200);
	    // Ensure response is a list (even if empty, the structure must be valid)
	    Assert.assertNotNull(response.jsonPath().getList("bookingid"));
	}
	
	@Test
	public void validateGetByLastName() {
	    Response response = given()
	            
	            .queryParam("lastname", br.getLastname())
	        .when()
	            .get("/booking")
	        .then()
	            .extract().response();

	    Assert.assertEquals(response.getStatusCode(), 200);
	    Assert.assertNotNull(response.jsonPath().getList("bookingid"));
	}
	
	
	@Test
	public void validateGetByCheckinDate() {
		
	    Response response = given()
	            
	            .queryParam("checkin", br.getBookingdates().getCheckin())
	        .when()
	            .get("/booking")
	        .then()
	            .extract().response();

	    Assert.assertEquals(response.getStatusCode(), 200);
	    Assert.assertNotNull(response.jsonPath().getList("bookingid"));
	}
	
	@Test
	public void validateGetByCheckoutDate() {
	    Response response = given()
	            
	            .queryParam("checkout", br.getBookingdates().getCheckout())
	        .when()
	            .get("/booking")
	        .then()
	            .extract().response();
	    
	    Assert.assertEquals(response.getStatusCode(), 200);
	    Assert.assertNotNull(response.jsonPath().getList("bookingid"));
	}
	
	@Test
    public void getBookingByIdAndValidate() {
        
		int bookingId=5;

        // 2. Execute GET Request and store response
        Response response = given()
                
            .when()
                .get("/booking/5")
            .then()
                .extract().response();

        // 3. Perform Validations
        
        // --- Status and Header Validations ---
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        Assert.assertTrue(response.getContentType().contains("application/json"), "Response should be JSON");

        // --- Body Validations (Root Level) ---
        String firstname = response.jsonPath().getString("firstname");
        String lastname = response.jsonPath().getString("lastname");
        int totalPrice = response.jsonPath().getInt("totalprice");
        //boolean depositPaid = response.jsonPath().getBoolean("depositpaid");

        Assert.assertNotNull(firstname, "Firstname should not be null");
        Assert.assertNotNull(lastname, "Lastname should not be null");
        Assert.assertTrue(totalPrice > 0, "Total price should be greater than 0");

        // --- Nested Object Validations (bookingdates) ---
        String checkin = response.jsonPath().getString("bookingdates.checkin");
        String checkout = response.jsonPath().getString("bookingdates.checkout");

        Assert.assertNotNull(checkin, "Check-in date is missing");
        Assert.assertNotNull(checkout, "Check-out date is missing");

        // Optional: Print the response for debugging
        System.out.println("Booking Details for ID " + bookingId + ":");
        response.prettyPrint();
    }
	
}