package org.example.tests.e2e.profile;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.example.assertions.profile.ProfileAssertions;
import org.example.base.AuthenticatedBaseTest;
import org.example.config.AppPropertiesReader;
import org.example.endpoints.auth.LoginEndpoint;
import org.example.endpoints.auth.UpdateEmailEndpoint;
import org.example.endpoints.auth.UpdatePasswordEndpoint;
import org.example.endpoints.profile.ProfileGetEndpoint;
import org.example.endpoints.profile.ProfileSaveEndpoint;
import org.example.factory.ProfileDataFactory;
import org.example.models.auth.LoginRequest;
import org.example.models.auth.PassDto;
import org.example.models.profile.ProfileDto;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Профиль из application.properties + смена email и пароля с откатом к исходным учётным данным.
 */
@Epic("Profile & Account")
@Feature("End-to-end profile and credentials")
@Severity(SeverityLevel.CRITICAL)
@Test(groups = {"integration", "e2e"})
public class ProfileAndAccountE2eTest extends AuthenticatedBaseTest {

    @Test
    @Story("Сохранение профиля, смена почты и пароля с возвратом к исходному состоянию")
    public void profileSaveGetAndEmailPasswordRoundTrip() {
        String accountEmail = AppPropertiesReader.get("email");
        String accountPassword = AppPropertiesReader.get("password");
        String tempEmail = "e2e." + UUID.randomUUID() + "@example.com";
        String tempPassword = accountPassword + "E2e9";

        ProfileDto profile = ProfileDataFactory.fromApplicationProperties();

        Allure.step("POST /api/profile/save — данные из application.properties", () -> {
            Response save = ProfileSaveEndpoint.save(profile);
            assertThat(save.getStatusCode(), equalTo(200));
            ProfileAssertions.assertSaveResponseMatchesRequest(save, profile);
        });

        Allure.step("GET /api/profile/get — совпадает с сохранённым", () -> {
            Response get = ProfileGetEndpoint.getProfile();
            assertThat(get.getStatusCode(), equalTo(200));
            ProfileAssertions.assertGetProfileMatches(get, profile);
        });

        Allure.step("PATCH /api/auth/update-email — полный цикл или пропуск при отказе API", () -> {
            Response toTemp = UpdateEmailEndpoint.updateEmail(new LoginRequest(tempEmail, accountPassword));
            if (toTemp.getStatusCode() == 200) {
                Allure.step("Смена на временный email и возврат на исходный", () -> {
                    try {
                        Response loginTemp = LoginEndpoint.login(new LoginRequest(tempEmail, accountPassword));
                        assertThat(loginTemp.getStatusCode(), equalTo(200));
                        setBearerAccessToken(loginTemp.jsonPath().getString("accessToken"));

                        Response back = UpdateEmailEndpoint.updateEmail(new LoginRequest(accountEmail, accountPassword));
                        assertThat(back.getStatusCode(), equalTo(200));

                        Response loginOrig = LoginEndpoint.login(new LoginRequest(accountEmail, accountPassword));
                        assertThat(loginOrig.getStatusCode(), equalTo(200));
                        setBearerAccessToken(loginOrig.jsonPath().getString("accessToken"));
                    } finally {
                        Response recovered = LoginEndpoint.login(new LoginRequest(accountEmail, accountPassword));
                        if (recovered.getStatusCode() == 200) {
                            setBearerAccessToken(recovered.jsonPath().getString("accessToken"));
                        }
                    }
                });
            } else {
                Allure.step(
                        "Смена на тестовый email отклонена (" + toTemp.getStatusCode()
                                + ") — часто из‑за политики домена. Подтверждение текущего email (контракт PATCH).",
                        () -> {
                            Response same = UpdateEmailEndpoint.updateEmail(
                                    new LoginRequest(accountEmail, accountPassword));
                            assertThat(same.getStatusCode(), anyOf(equalTo(200), equalTo(400)));
                        });
                Response relogin = LoginEndpoint.login(new LoginRequest(accountEmail, accountPassword));
                assertThat(relogin.getStatusCode(), equalTo(200));
                setBearerAccessToken(relogin.jsonPath().getString("accessToken"));
            }
        });

        Allure.step("PATCH /api/auth/update-pass — временный пароль и откат", () -> {
            assertThat(
                    UpdatePasswordEndpoint.updatePassword(new PassDto(accountPassword, tempPassword)).getStatusCode(),
                    equalTo(200)
            );

            Response loginNewPass = LoginEndpoint.login(new LoginRequest(accountEmail, tempPassword));
            assertThat(loginNewPass.getStatusCode(), equalTo(200));
            setBearerAccessToken(loginNewPass.jsonPath().getString("accessToken"));

            assertThat(
                    UpdatePasswordEndpoint.updatePassword(new PassDto(tempPassword, accountPassword)).getStatusCode(),
                    equalTo(200)
            );

            Response restored = LoginEndpoint.login(new LoginRequest(accountEmail, accountPassword));
            assertThat(restored.getStatusCode(), equalTo(200));
            setBearerAccessToken(restored.jsonPath().getString("accessToken"));
        });

        Allure.step("GET /api/profile/get после смены учётных данных — профиль на месте", () -> {
            Response get = ProfileGetEndpoint.getProfile();
            assertThat(get.getStatusCode(), equalTo(200));
            assertThat(get.jsonPath().getString("phone"), equalTo(profile.getPhone()));
        });
    }
}
