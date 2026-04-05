package org.example.endpoints.auth;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * GET /api/auth/refresh — на бэкенде часто ожидается refresh JWT в {@code Authorization: Bearer ...}, а не access.
 */
public class RefreshEndpoint {

    private static final String REFRESH = "/api/auth/refresh";

    public static Response refresh() {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get(REFRESH);
    }

    public static Response refreshAuthenticated() {
        return given()
                .spec(RestAssured.requestSpecification)
                .when()
                .get(REFRESH);
    }

    public static Response refreshWithBearer(String jwt) {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + jwt)
                .when()
                .get(REFRESH);
    }

    /** Некоторые бэкенды отдают refresh только через httpOnly-cookie с таким именем. */
    public static Response refreshWithRefreshTokenCookie(String refreshTokenValue) {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .cookie("refreshToken", refreshTokenValue)
                .when()
                .get(REFRESH);
    }
}
