
package org.example.endpoints.register.history;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class RegisterEndpoint {

    private static final String REGISTER = "/api/auth/register";

    public static Response register(String email, String password) {
        String body = String.format("""
                {
                  "email": "%s",
                  "password": "%s"
                }
                """, email, password);

        return given()
                    .body(body)
               .when()
                    .post(REGISTER);
    }
}