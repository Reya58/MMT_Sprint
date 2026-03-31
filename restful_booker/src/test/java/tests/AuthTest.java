package tests;

import base.BaseTest;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.AuthRequest;
import pojo.AuthResponse;

import static io.restassured.RestAssured.*;

public class AuthTest extends BaseTest {

    @Test
    public void createTokenTest() {

        AuthRequest request = new AuthRequest("admin", "password123");

        AuthResponse response =
                given()
                        .contentType(ContentType.JSON)
                        .body(request)
                .when()
                        .post("/auth")
                .then()
                        .statusCode(200)
                        .extract()
                        .as(AuthResponse.class);

        Assert.assertNotNull(response.getToken());
    }
}