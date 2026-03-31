package com.fakestore.utils;

import com.fakestore.config.ConfigManager;
import static io.restassured.RestAssured.given;
import io.restassured.specification.RequestSpecification;

public class SpecFactory {

    private SpecFactory() {}

    
    private static RequestSpecification baseBuilder() {
        ConfigManager cfg = ConfigManager.getInstance();
		return given()
				.baseUri(cfg.getBaseUrl())
				.contentType("application/json")
				.relaxedHTTPSValidation(); 
    }

    
    public static RequestSpecification publicSpec() {
        return baseBuilder();
    }

    public static RequestSpecification authSpec() {
    	
        return baseBuilder()
        		.auth().oauth2(TokenManager.getInstance().getBearerToken());
    }
}