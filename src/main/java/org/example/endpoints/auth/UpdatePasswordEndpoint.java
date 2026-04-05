package org.example.endpoints.auth;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.models.auth.PassDto;

import static io.restassured.RestAssured.given;

public class UpdatePasswordEndpoint {

    private static final String UPDATE_PASS = "/api/auth/update-pass";

    public static Response updatePassword(PassDto passDto) {
        return given()
                .spec(RestAssured.requestSpecification)
                .body(passDto)
                .when()
                .patch(UPDATE_PASS);
    }
}
