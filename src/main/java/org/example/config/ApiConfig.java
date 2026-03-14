package org.example.config;

import io.restassured.RestAssured;

public class ApiConfig {
    public static final String BASE_URI = "http://2.59.41.2:7320";

    public static void setup() {
        RestAssured.baseURI = BASE_URI;
    }
}
