package org.example.endpoints.auth;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LogoutEndpoint {

    private static final String LOGOUT = "/api/auth/logout";

    public static Response logout() {
        return given()
                .spec(RestAssured.requestSpecification)
                .when()
                .get(LOGOUT);
    }
}
