package org.example.assertions.auth;

import io.qameta.allure.Allure;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AuthAssertions {

    public static void verifyTokens(Response response) {

        Allure.step("Verify access token", () ->
                assertThat(response.jsonPath().getString("accessToken"), notNullValue())
        );

        Allure.step("Verify refresh token", () ->
                assertThat(response.jsonPath().getString("refreshToken"), notNullValue())
        );
    }

    public static void verifyUserEmail(Response response, String email) {

        Allure.step("Verify user email", () ->
                assertThat(response.jsonPath().getString("user.email"), equalTo(email))
        );
    }

    public static void verifyUserId(Response response) {

        Allure.step("Verify user id", () ->
                assertThat(response.jsonPath().getString("user.id"), notNullValue())
        );
    }
}