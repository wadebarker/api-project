package org.example.tests.integration.profile;

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

/**
 * {@code POST /api/profile/save}, {@code GET /api/profile/get}, {@code GET /api/profile/image}.
 */
@Test(groups = "integration")
public class ProfileApiIntegrationTest extends AuthenticatedBaseTest {

    @Test(dataProvider = "integrationProfiles", dataProviderClass = IntegrationProfileDataProvider.class)
    public void saveProfileReturns200(ProfileDto profile) {
        Response response = ProfileSaveEndpoint.save(profile);

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.jsonPath().getLong("id"), greaterThan(0L));
        assertThat(response.jsonPath().getLong("userId"), greaterThan(0L));
        assertThat(response.jsonPath().getString("name"), equalTo(profile.getName()));
    }

    @Test
    public void getProfileReturns200MatchingSaved() {
        ProfileDto profile = ProfileDataFactory.fromApplicationProperties();
        ProfileSaveEndpoint.save(profile);

        Response response = ProfileGetEndpoint.getProfile();

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("phone"), equalTo(profile.getPhone()));
        assertThat(response.jsonPath().getString("surname"), equalTo(profile.getSurname()));
    }

    @Test
    public void getAvatarReturns200() {
        Response response = ProfileImageEndpoint.getAvatar();

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getBody().asByteArray().length, greaterThanOrEqualTo(0));
    }
}
