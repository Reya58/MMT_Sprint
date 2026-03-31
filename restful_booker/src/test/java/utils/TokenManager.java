package utils;

import io.restassured.http.ContentType;
import pojo.AuthRequest;
import pojo.AuthResponse;

import static io.restassured.RestAssured.*;

public class TokenManager {

    private static String token;

    public static String getToken() {
        if (token == null) {

            AuthRequest request = new AuthRequest("admin", "password123");

            token =
                    given()
                            .contentType(ContentType.JSON)
                            .body(request)
                    .when()
                            .post("/auth")
                    .then()
                            .extract()
                            .as(AuthResponse.class)
                            .getToken();
        }
        return token;
    }
}