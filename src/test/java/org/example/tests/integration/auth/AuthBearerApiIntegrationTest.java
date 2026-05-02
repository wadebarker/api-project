package org.example.tests.integration.auth;

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

/**
 * Ручки с Bearer: refresh, update-email, update-pass, logout. Порядок {@code priority}: сначала сценарии с токеном, logout — последним.
 */
@Test(groups = {"unit", "integration"})
public class AuthBearerApiIntegrationTest extends AuthenticatedBaseTest {

    @Test(priority = 1)
    public void refreshReturns200Or401Contract() {
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
    }

    @Test(priority = 2)
    public void updateEmailReturnsValidContract() {
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
    }

    @Test(priority = 5)
    public void updatePasswordRoundTripRestoresOriginalPassword() {
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
    }

    @Test(priority = 100)
    public void logoutReturns200() {
        assertThat(LogoutEndpoint.logout().getStatusCode(), equalTo(200));
    }
}
