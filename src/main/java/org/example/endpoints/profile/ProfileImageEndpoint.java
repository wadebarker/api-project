package org.example.endpoints.profile;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/** GET /api/profile/image — ответ может быть изображением (не JSON). */
public class ProfileImageEndpoint {

    private static final String IMAGE = "/api/profile/image";

    public static Response getAvatar() {
        return given()
                .spec(RestAssured.requestSpecification)
                .when()
                .get(IMAGE);
    }
}
