package Rest_Api;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AuthUtil {

    public static String getToken() {

        RestAssured.baseURI = "https://fakestoreapi.com/"; // change to your actual URL

        String requestBody = "{\n" +
                "  \"username\": \"derek\",\n" +
                "  \"password\": \"jklg*_56\"\n" +
                "}";

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post("/auth/login");  // change endpoint if needed

        // Validate login
        response.then().statusCode(201);

        // Extract token (adjust key if API uses different name)
        String token = response.jsonPath().getString("token");

        System.out.println("Generated Token: " + token);

        return token;
    }
}