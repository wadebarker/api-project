package org.example.endpoints.profile;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProfileGetEndpoint {

    private static final String GET_PROFILE = "/api/profile/get";

    public static Response getProfile() {
        return given()
                .spec(RestAssured.requestSpecification)
                .when()
                .get(GET_PROFILE);
    }
}
