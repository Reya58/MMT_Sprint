package com.fakestore.config;

 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class ConfigManager {

    private static ConfigManager instance;
    private static Properties properties;
 
    private ConfigManager() {
        FileInputStream fis;
        try {
            fis = new FileInputStream("src/test/resources/configData/configuration.properties");
            properties = new Properties();
            try {
                properties.load(fis);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
 
    public static ConfigManager getInstance() {
        if (instance == null) {
        	instance = new ConfigManager();
        }
        return instance;
    }
 
    public String getBaseUrl(){
    	return get("base.url"); 
    }
    public String getUsername(){
    	return get("auth.username"); 
    }
    public String getPassword(){
    	return get("auth.password"); 
    }
    public int    getConnTimeout(){ 
    	return Integer.parseInt(get("timeout.connect.ms")); 
    }
    public int    getReadTimeout() {
    	return Integer.parseInt(get("timeout.read.ms")); 
    }
 
    public String get(String key) {
        String value = System.getProperty(key, properties.getProperty(key));
        if (value == null) {
            throw new IllegalArgumentException("Missing config key: " + key);
        }
        return value.trim();
    }
}

