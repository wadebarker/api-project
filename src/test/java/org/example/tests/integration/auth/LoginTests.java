package org.example.tests.integration.auth;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.example.assertions.auth.AuthAssertions;
import org.example.base.BaseTest;
import org.example.dataproviders.LoginDataProvider;
import org.example.endpoints.auth.LoginEndpoint;
import org.example.models.auth.LoginRequest;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Severity(SeverityLevel.BLOCKER)
@Epic("User Authentication Module")
@Feature("Login Functionality")
public class LoginTests extends BaseTest {

    @Test(dataProvider = "positiveLogin", dataProviderClass = LoginDataProvider.class)
    public void loginSuccess(LoginRequest request) {
        Allure.step("Perform positive login with email: " + request.getEmail(), () -> {

            Response response = LoginEndpoint.login(request);

            attachResponse(response);

            Allure.step("Verify response status code is 200", () ->
                    assertThat(response.statusCode(), equalTo(200))
            );

            Allure.step("Verify access token", () ->
                    assertThat(response.jsonPath().getString("accessToken"), notNullValue())
            );

            AuthAssertions.verifyTokens(response);
            AuthAssertions.verifyUserId(response);
            AuthAssertions.verifyUserEmail(response, request.getEmail());
        });
    }

    @Test(dataProvider = "negativeLogin", dataProviderClass = LoginDataProvider.class)
    public void loginNegative(LoginRequest request) {
        Allure.step("Perform negative login with email: " + request.getEmail(), () -> {

            Response response = LoginEndpoint.login(request);

            attachResponse(response);

            Allure.step("Verify response status code is 400 or 401", () ->
                    assertThat(response.statusCode(), anyOf(equalTo(400), equalTo(401)))
            );
        });
    }

    @Step("Attach response body")
    private void attachResponse(Response response) {
        Allure.addAttachment("Response Body", "application/json", response.asPrettyString(), ".json");
    }
}