package org.example.tests.e2e.auth;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.example.assertions.auth.AuthAssertions;
import org.example.base.BaseTest;
import org.example.endpoints.auth.LoginEndpoint;
import org.example.endpoints.auth.RegisterEndpoint;
import org.example.models.auth.LoginRequest;
import org.example.models.auth.RegisterRequest;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/*
Логика теста:

1) Сгенерировать нового пользователя
2) Вызвать register
3) Проверить 201
4) С теми же данными вызвать login
5) Проверить 200 и токены
*/

@Epic("User Authentication Module")
@Feature("Register + Login Flow")
@Severity(SeverityLevel.CRITICAL)
public class AuthFlowTest extends BaseTest {

    @Test
    @Story("User can register and then login")
    public void registerAndLoginFlow() {

        // генерируем уникального пользователя
        String email = "test_" + UUID.randomUUID() + "@mail.com";
        String password = "Qwerty123";

        RegisterRequest registerRequest = new RegisterRequest(email, password);

        Allure.step("Register new user", () -> {

            Response registerResponse = RegisterEndpoint.register(registerRequest);

            Allure.step("Verify register status code", () ->
                    assertThat(registerResponse.statusCode(), equalTo(201))
            );
        });

        LoginRequest loginRequest = new LoginRequest(email, password);

        Allure.step("Login with registered user", () -> {

            Response loginResponse = LoginEndpoint.login(loginRequest);

            Allure.step("Verify login status code", () ->
                    assertThat(loginResponse.statusCode(), equalTo(200))
            );

            AuthAssertions.verifyTokens(loginResponse);
            AuthAssertions.verifyUserId(loginResponse);
            AuthAssertions.verifyUserEmail(loginResponse, loginRequest.getEmail());
        });
    }
}