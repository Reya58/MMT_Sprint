package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class PingTest extends BaseTest {

    @Test
    public void pingHealthCheck() {

        String response =
                given()
                .when()
                        .get("/ping")
                .then()
                        .statusCode(201)
                        .extract()
                        .asString();

        // Validate response body
        Assert.assertEquals(response, "Created");
    }
}