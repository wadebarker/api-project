package org.example.factory;

import net.datafaker.Faker;
import org.example.config.AppPropertiesReader;
import org.example.models.profile.ProfileDto;

/**
 * Данные профиля: из {@code application.properties} и варианты для параметризации.
 */
public final class ProfileDataFactory {

    private static final Faker FAKER = new Faker();

    private ProfileDataFactory() {
    }

    /** Значения ключей {@code profile.*} из {@code application.properties}. */
    public static ProfileDto fromApplicationProperties() {
        return new ProfileDto(
                AppPropertiesReader.get("profile.name"),
                AppPropertiesReader.get("profile.dateOfBirth"),
                AppPropertiesReader.get("profile.surname"),
                AppPropertiesReader.get("profile.patronymic"),
                AppPropertiesReader.get("profile.sex"),
                AppPropertiesReader.get("profile.phone")
        );
    }

    /** Второй набор с тем же телефоном (длина 18), для data provider. */
    public static ProfileDto alternateFromProperties() {
        return new ProfileDto(
                AppPropertiesReader.get("profile.name") + FAKER.lorem().characters(0, 2),
                AppPropertiesReader.get("profile.dateOfBirth"),
                AppPropertiesReader.get("profile.surname"),
                AppPropertiesReader.get("profile.patronymic"),
                AppPropertiesReader.get("profile.sex"),
                AppPropertiesReader.get("profile.phone")
        );
    }
}
