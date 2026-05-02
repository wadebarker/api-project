package org.example.endpoints.profile;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.models.profile.ProfileDto;

import static io.restassured.RestAssured.given;

public class ProfileSaveEndpoint {

    private static final String SAVE = "/api/profile/save";

    public static Response save(ProfileDto profile) {
        return given()
                .spec(RestAssured.requestSpecification)
                .contentType(ContentType.JSON)
                .body(profile)
                .when()
                .post(SAVE);
    }

    public static Response saveWithoutAuth(ProfileDto profile) {
        RequestSpecification previous = RestAssured.requestSpecification;
        RestAssured.requestSpecification = null;
        try {
            return given()
                    .contentType(ContentType.JSON)
                    .body(profile)
                    .when()
                    .post(SAVE);
        } finally {
            RestAssured.requestSpecification = previous;
        }
    }
}
