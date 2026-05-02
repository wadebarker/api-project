package org.example.tests.profile;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.example.assertions.profile.ProfileAssertions;
import org.example.base.AuthenticatedBaseTest;
import org.example.dataproviders.ProfileDataProvider;
import org.example.endpoints.profile.ProfileGetEndpoint;
import org.example.endpoints.profile.ProfileImageEndpoint;
import org.example.endpoints.profile.ProfileSaveEndpoint;
import org.example.factory.ProfileDataFactory;
import org.example.models.profile.ProfileDto;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Покрытие профиля: save / get / image, негативная валидация, запросы без Bearer.
 */
@Epic("Profile API")
@Feature("Save, get, avatar, security")
@Severity(SeverityLevel.NORMAL)
@Test(groups = {"unit", "integration"})
public class ProfileApiTest extends AuthenticatedBaseTest {

    @Test(dataProvider = "profileValidPayloads", dataProviderClass = ProfileDataProvider.class)
    public void saveProfileReturns200(ProfileDto profile) {
        Allure.step("POST /api/profile/save — валидный ProfileDto", () -> {
            Response response = ProfileSaveEndpoint.save(profile);

            assertThat(response.getStatusCode(), equalTo(200));
            ProfileAssertions.assertSaveResponseMatchesRequest(response, profile);
        });
    }

    @Test
    public void getProfileReturns200MatchingSaved() {
        Allure.step("GET /api/profile/get после save", () -> {
            ProfileDto profile = ProfileDataFactory.fromApplicationProperties();
            assertThat(ProfileSaveEndpoint.save(profile).getStatusCode(), equalTo(200));

            Response response = ProfileGetEndpoint.getProfile();

            assertThat(response.getStatusCode(), equalTo(200));
            ProfileAssertions.assertGetProfileMatches(response, profile);
        });
    }

    @Test
    public void getAvatarReturns200() {
        Allure.step("GET /api/profile/image", () -> {
            Response response = ProfileImageEndpoint.getAvatar();

            assertThat(response.getStatusCode(), equalTo(200));
            assertThat(response.getBody().asByteArray().length, greaterThanOrEqualTo(0));
        });
    }

    @Test(dataProvider = "profileInvalidPayloads", dataProviderClass = ProfileDataProvider.class)
    public void saveProfileInvalidReturns4xx(ProfileDto profile, String bodyFragment) {
        Allure.step("POST /api/profile/save — невалидное тело", () -> {
            Response response = ProfileSaveEndpoint.save(profile);

            assertThat(response.getStatusCode(), anyOf(equalTo(400), equalTo(422)));
            assertThat(response.asString(), containsString(bodyFragment));
        });
    }

    @Test
    public void saveWithoutAuthReturns401() {
        Allure.step("POST /api/profile/save без Authorization", () -> {
            Response response = ProfileSaveEndpoint.saveWithoutAuth(ProfileDataFactory.fromApplicationProperties());

            assertThat(response.getStatusCode(), equalTo(401));
            assertThat(response.jsonPath().getInt("statusCode"), equalTo(401));
        });
    }

    @Test
    public void getProfileWithoutAuthReturns401() {
        Allure.step("GET /api/profile/get без Authorization", () -> {
            Response response = ProfileGetEndpoint.getProfileWithoutAuth();

            assertThat(response.getStatusCode(), equalTo(401));
            assertThat(response.jsonPath().getInt("statusCode"), equalTo(401));
        });
    }

    @Test
    public void getAvatarWithoutAuthReturns401() {
        Allure.step("GET /api/profile/image без Authorization", () -> {
            Response response = ProfileImageEndpoint.getAvatarWithoutAuth();

            assertThat(response.getStatusCode(), equalTo(401));
            String ct = response.getContentType();
            if (ct != null && ct.contains("json")) {
                assertThat(response.jsonPath().getInt("statusCode"), equalTo(401));
            }
        });
    }
}
