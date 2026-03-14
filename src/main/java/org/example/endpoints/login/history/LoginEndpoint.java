package org.example.endpoints.login.history;

import io.restassured.response.Response;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;

public class LoginEndpoint {
    private static final String LOGIN = "/api/auth/login";

    public static Response login(String email, String password) {
        String body = String.format("""
                {
                  "email": "%s",
                  "password": "%s"
                }
                """, email, password);

        return given()
                 .contentType(ContentType.JSON)
                    .body(body)
                .when()
                    .post(LOGIN)
                .then()
                    .extract()
                    .response();
    }
}
