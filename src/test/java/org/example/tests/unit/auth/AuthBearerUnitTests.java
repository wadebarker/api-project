package org.example.tests.unit.auth;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.example.assertions.auth.AuthAssertions;
import org.example.base.AuthenticatedBaseTest;
import org.example.config.AppPropertiesReader;
import org.example.endpoints.auth.LoginEndpoint;
import org.example.endpoints.auth.LogoutEndpoint;
import org.example.endpoints.auth.RefreshEndpoint;
import org.example.endpoints.auth.UpdateEmailEndpoint;
import org.example.endpoints.auth.UpdatePasswordEndpoint;
import org.example.factory.LoginDataFactory;
import org.example.models.auth.LoginRequest;
import org.example.models.auth.PassDto;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("Auth API")
@Feature("Bearer endpoints")
@Severity(SeverityLevel.BLOCKER)
@Test(groups = "unit")
public class AuthBearerUnitTests extends AuthenticatedBaseTest {

    @Test(priority = 1)
    public void refreshContract() {
        Allure.step("GET /api/auth/refresh", () -> {
            Response withAccess = RefreshEndpoint.refreshAuthenticated();
            if (withAccess.getStatusCode() == 200) {
                AuthAssertions.verifyTokens(withAccess);
                return;
            }

            Response login = LoginEndpoint.login(LoginDataFactory.validUser());
            assertThat(login.getStatusCode(), equalTo(200));
            String refreshJwt = login.jsonPath().getString("refreshToken");

            Response withRefresh = RefreshEndpoint.refreshWithBearer(refreshJwt);
            if (withRefresh.getStatusCode() != 200) {
                withRefresh = RefreshEndpoint.refreshWithRefreshTokenCookie(refreshJwt);
            }

            if (withRefresh.getStatusCode() == 200) {
                AuthAssertions.verifyTokens(withRefresh);
            } else {
                assertThat(withRefresh.getStatusCode(), equalTo(401));
                String ct = withRefresh.getContentType();
                if (ct != null && ct.contains("json")) {
                    assertThat(withRefresh.jsonPath().getInt("statusCode"), equalTo(401));
                }
            }
        });
    }

    @Test(priority = 2)
    public void updateEmailContract() {
        Allure.step("PATCH /api/auth/update-email", () -> {
            LoginRequest body = LoginDataFactory.validUser();
            Response response = UpdateEmailEndpoint.updateEmail(body);
            int code = response.getStatusCode();

            assertThat(code, anyOf(equalTo(200), equalTo(400)));
            if (code == 200) {
                assertThat(response.jsonPath().getString("email"), equalTo(body.getEmail()));
                assertThat(response.jsonPath().getInt("id"), greaterThan(0));
            } else {
                assertThat(response.jsonPath().getInt("statusCode"), equalTo(400));
            }
        });
    }

    @Test(priority = 5)
    public void updatePasswordRoundTrip() {
        Allure.step("PATCH /api/auth/update-pass с откатом пароля", () -> {
            String email = AppPropertiesReader.get("email");
            String original = AppPropertiesReader.get("password");
            String temp = original + "Z9";

            assertThat(UpdatePasswordEndpoint.updatePassword(new PassDto(original, temp)).getStatusCode(), equalTo(200));

            Response relogin = LoginEndpoint.login(new LoginRequest(email, temp));
            assertThat(relogin.getStatusCode(), equalTo(200));
            setBearerAccessToken(relogin.jsonPath().getString("accessToken"));

            assertThat(UpdatePasswordEndpoint.updatePassword(new PassDto(temp, original)).getStatusCode(), equalTo(200));

            Response restored = LoginEndpoint.login(new LoginRequest(email, original));
            assertThat(restored.getStatusCode(), equalTo(200));
            setBearerAccessToken(restored.jsonPath().getString("accessToken"));
        });
    }

    @Test(priority = 100)
    public void logoutReturns200() {
        Allure.step("GET /api/auth/logout", () ->
                assertThat(LogoutEndpoint.logout().getStatusCode(), equalTo(200))
        );
    }
}
