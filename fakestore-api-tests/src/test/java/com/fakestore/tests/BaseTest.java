package com.fakestore.tests;

import org.testng.annotations.BeforeSuite;

import com.fakestore.config.ConfigManager;
import io.restassured.RestAssured;

public class BaseTest {

	
	@BeforeSuite()
    public void globalSetup() {
        ConfigManager cfg = ConfigManager.getInstance();
 
        RestAssured.baseURI = cfg.getBaseUrl();
    }
}