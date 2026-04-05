package org.example.dataproviders;

import org.example.factory.ProfileDataFactory;
import org.example.models.profile.ProfileDto;
import org.testng.annotations.DataProvider;

public final class IntegrationProfileDataProvider {

    private IntegrationProfileDataProvider() {
    }

    @DataProvider(name = "integrationProfiles")
    public static Object[][] integrationProfiles() {
        return new Object[][]{
                {ProfileDataFactory.fromApplicationProperties()},
                {ProfileDataFactory.alternateFromProperties()}
        };
    }
}
