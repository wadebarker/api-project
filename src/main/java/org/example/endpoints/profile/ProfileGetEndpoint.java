package org.example.endpoints.profile;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ProfileGetEndpoint {

    private static final String GET_PROFILE = "/api/profile/get";

    public static Response getProfile() {
        return given()
                .spec(RestAssured.requestSpecification)
                .accept(ContentType.JSON)
                .when()
                .get(GET_PROFILE);
    }

    public static Response getProfileWithoutAuth() {
        RequestSpecification previous = RestAssured.requestSpecification;
        RestAssured.requestSpecification = null;
        try {
            return given()
                    .accept(ContentType.JSON)
                    .when()
                    .get(GET_PROFILE);
        } finally {
            RestAssured.requestSpecification = previous;
        }
    }
}
