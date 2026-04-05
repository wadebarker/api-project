package org.example.tests.integration.auth;

import io.restassured.response.Response;
import org.example.assertions.auth.AuthAssertions;
import org.example.base.BaseTest;
import org.example.dataproviders.IntegrationAuthPublicDataProvider;
import org.example.endpoints.auth.LoginEndpoint;
import org.example.endpoints.auth.RegisterEndpoint;
import org.example.models.auth.LoginRequest;
import org.example.models.auth.RegisterRequest;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Интеграционные проверки публичных auth-ручек: {@code POST /api/auth/login}, {@code POST /api/auth/register}.
 */
@Test(groups = "integration")
public class AuthPublicApiIntegrationTest extends BaseTest {

    @Test(dataProvider = "integrationLoginSuccess", dataProviderClass = IntegrationAuthPublicDataProvider.class)
    public void loginReturns200AndTokens(LoginRequest request) {
        Response response = LoginEndpoint.login(request);

        assertThat(response.getStatusCode(), equalTo(200));
        AuthAssertions.verifyTokens(response);
        AuthAssertions.verifyUserEmail(response, request.getEmail());
    }

    @Test(dataProvider = "integrationLoginUnauthorized", dataProviderClass = IntegrationAuthPublicDataProvider.class)
    public void loginReturnsUnauthorizedForBadCredentials(LoginRequest request) {
        Response response = LoginEndpoint.login(request);

        assertThat(response.getStatusCode(), anyOf(equalTo(400), equalTo(401)));
    }

    @Test(dataProvider = "integrationRegisterValid", dataProviderClass = IntegrationAuthPublicDataProvider.class)
    public void registerReturns201AndTokens(RegisterRequest request) {
        Response response = RegisterEndpoint.register(request);

        assertThat(response.getStatusCode(), equalTo(201));
        AuthAssertions.verifyTokens(response);
        AuthAssertions.verifyUserEmail(response, request.getEmail());
    }
}
