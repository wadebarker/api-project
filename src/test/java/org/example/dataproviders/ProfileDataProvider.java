package org.example.dataproviders;

import org.example.factory.ProfileDataFactory;
import org.example.models.profile.ProfileDto;
import org.testng.annotations.DataProvider;

public final class ProfileDataProvider {

    private ProfileDataProvider() {
    }

    @DataProvider(name = "profileValidPayloads")
    public static Object[][] profileValidPayloads() {
        return new Object[][]{
                {ProfileDataFactory.fromApplicationProperties()},
                {ProfileDataFactory.alternateFromProperties()}
        };
    }

    /**
     * Негативные тела; фрагмент — для проверки тела ответа (язык сообщения может отличаться).
     */
    @DataProvider(name = "profileInvalidPayloads")
    public static Object[][] profileInvalidPayloads() {
        return new Object[][]{
                {ProfileDataFactory.invalidNameTooShort(), "name"},
                {ProfileDataFactory.invalidPhoneLength(), "phone"},
                {ProfileDataFactory.invalidSurnameTooShort(), "surname"}
        };
    }
}
