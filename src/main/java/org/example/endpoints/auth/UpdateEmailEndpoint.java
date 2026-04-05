package org.example.endpoints.auth;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.models.auth.LoginRequest;

import static io.restassured.RestAssured.given;

/**
 * PATCH /api/auth/update-email — тело {@code SignInDto} (в проекте: {@link LoginRequest}).
 */
public class UpdateEmailEndpoint {

    private static final String UPDATE_EMAIL = "/api/auth/update-email";

    public static Response updateEmail(LoginRequest signInDto) {
        return given()
                .spec(RestAssured.requestSpecification)
                .body(signInDto)
                .when()
                .patch(UPDATE_EMAIL);
    }
}
