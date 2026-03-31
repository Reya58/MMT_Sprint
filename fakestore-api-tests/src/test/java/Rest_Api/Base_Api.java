package Rest_Api;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class Base_Api {

    protected static String BASE_URL = "https://fakestoreapi.com/"; // change this
    protected static String TOKEN;

    protected RequestSpecification request;

    public void setup() {
        System.out.println("Hit");
        // Set base URI
        RestAssured.baseURI = BASE_URL;

        // Generate token only once
        if (TOKEN == null) {
        	System.out.println("Hitt");
            TOKEN = AuthUtil.getToken();
        }

        // Common request specification
        request = RestAssured
                .given()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json");
    }
}
