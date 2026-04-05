package org.example.tests.unit.profile;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.example.base.AuthenticatedBaseTest;
import org.example.dataproviders.IntegrationProfileDataProvider;
import org.example.endpoints.profile.ProfileGetEndpoint;
import org.example.endpoints.profile.ProfileImageEndpoint;
import org.example.endpoints.profile.ProfileSaveEndpoint;
import org.example.factory.ProfileDataFactory;
import org.example.models.profile.ProfileDto;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("Profile API")
@Feature("Save / get / avatar")
@Severity(SeverityLevel.NORMAL)
@Test(groups = "unit")
public class ProfileUnitTests extends AuthenticatedBaseTest {

    @Test(dataProvider = "integrationProfiles", dataProviderClass = IntegrationProfileDataProvider.class)
    public void saveProfileReturns200(ProfileDto profile) {
        Allure.step("POST /api/profile/save", () -> {
            Response response = ProfileSaveEndpoint.save(profile);

            assertThat(response.getStatusCode(), equalTo(200));
            assertThat(response.jsonPath().getLong("id"), greaterThan(0L));
            assertThat(response.jsonPath().getLong("userId"), greaterThan(0L));
            assertThat(response.jsonPath().getString("name"), equalTo(profile.getName()));
        });
    }

    @Test
    public void getProfileAfterSaveMatchesProperties() {
        Allure.step("GET /api/profile/get", () -> {
            ProfileDto profile = ProfileDataFactory.fromApplicationProperties();
            ProfileSaveEndpoint.save(profile);

            Response response = ProfileGetEndpoint.getProfile();

            assertThat(response.getStatusCode(), equalTo(200));
            assertThat(response.jsonPath().getString("phone"), equalTo(profile.getPhone()));
            assertThat(response.jsonPath().getString("surname"), equalTo(profile.getSurname()));
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
}
