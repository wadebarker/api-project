package org.example.tests.e2e.auth;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.example.assertions.auth.AuthAssertions;
import org.example.base.BaseTest;
import org.example.endpoints.auth.LoginEndpoint;
import org.example.endpoints.auth.RegisterEndpoint;
import org.example.factory.RegisterDataFactory;
import org.example.models.auth.LoginRequest;
import org.example.models.auth.RegisterRequest;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/*
Логика теста:

1) Сгенерировать нового пользователя
2) Вызвать register
4) С теми же данными вызвать login
*/

@Epic("User Authentication Module")
@Feature("Register + Login Flow")
@Severity(SeverityLevel.CRITICAL)
@Test(groups = "integration")
public class AuthFlowTest extends BaseTest {

    @Test
    @Story("User can register and then login")
    public void registerAndLoginFlow() {

        RegisterRequest registerRequest = RegisterDataFactory.validUser();

        Allure.step("Register new user", () -> {

            Response registerResponse = RegisterEndpoint.register(registerRequest);

            Allure.step("Verify register status code", () ->
                    assertThat(registerResponse.statusCode(), equalTo(201))
            );

            Allure.step("Verify response body", () -> {
                AuthAssertions.verifyTokens(registerResponse);
                AuthAssertions.verifyUserId(registerResponse);
                AuthAssertions.verifyUserEmail(registerResponse, registerRequest.getEmail());
            });
        });

        LoginRequest loginRequest = new LoginRequest(
                registerRequest.getEmail(),
                registerRequest.getPassword()
        );

        Allure.step("Login with registered user", () -> {

            Response loginResponse = LoginEndpoint.login(loginRequest);

            Allure.step("Verify login status code", () ->
                    assertThat(loginResponse.statusCode(), equalTo(200))
            );

            Allure.step("Verify response body", () -> {
                AuthAssertions.verifyTokens(loginResponse);
                AuthAssertions.verifyUserId(loginResponse);
                AuthAssertions.verifyUserEmail(loginResponse, loginRequest.getEmail());
            });
        });
    }
}