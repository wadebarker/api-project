package org.example.endpoints.profile;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.models.profile.ProfileDto;

import static io.restassured.RestAssured.given;

public class ProfileSaveEndpoint {

    private static final String SAVE = "/api/profile/save";

    public static Response save(ProfileDto profile) {
        return given()
                .spec(RestAssured.requestSpecification)
                .body(profile)
                .when()
                .post(SAVE);
    }
}
