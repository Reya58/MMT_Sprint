package com.fakestore.utils;

import com.fakestore.config.ConfigManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.time.Instant;

import static io.restassured.RestAssured.given;

public class TokenManager {

	private static final long TOKEN_TTL_SECONDS = 3600;

	private static TokenManager instance;

	private static String cachedToken;
	private static Instant tokenFetchedAt;

	private TokenManager() {
	}

	public static TokenManager getInstance() {
		if (instance == null) {
			instance = new TokenManager();
		}
		return instance;
	}


	public String getToken() {
		if (isTokenExpired()) {
			if (isTokenExpired()) {
				fetchNewToken();
			}
		}
		return cachedToken;
	}


	public void invalidateToken() {
			cachedToken = null;
			tokenFetchedAt = null;
	}

	public String getBearerToken() {
		return "Bearer " + getToken();
	}

	// -----------------------------------------------------------------------
	// Private helpers
	// -----------------------------------------------------------------------

	private boolean isTokenExpired() {
		if (cachedToken == null || tokenFetchedAt == null)
			return true;
		else return false;
	}

	private void fetchNewToken() {
		ConfigManager cfg = ConfigManager.getInstance();

		String requestBody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", cfg.getUsername(),
				cfg.getPassword());

		Response response = given()
				.baseUri(cfg.getBaseUrl())
				.contentType(ContentType.JSON)
				.body(requestBody)
				.when()
				.post("/auth/login")
				.then().statusCode(200)
				.extract().response();

		cachedToken = response.jsonPath().getString("token");
		tokenFetchedAt = Instant.now();

		if (cachedToken == null || cachedToken.isBlank()) {
			throw new IllegalStateException("Login succeeded but returned empty token");
		}
		
	}
}