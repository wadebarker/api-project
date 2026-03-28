package org.example.factory;

import net.datafaker.Faker;
import org.example.models.auth.LoginRequest;
import org.example.config.AppPropertiesReader;

// factory (генерация данных)
public class LoginDataFactory {

    public static final int CREDENTIALS_MIN_LENGTH = 6;
    public static final int CREDENTIALS_MAX_LENGTH = 50;
    private static final Faker faker = new Faker();

    public static LoginRequest validUser() {
        return new LoginRequest(
                AppPropertiesReader.get("email"),
                AppPropertiesReader.get("password")
        );
    }

    public static LoginRequest wrongPassword() {
        return new LoginRequest(
                AppPropertiesReader.get("email"),
                faker.internet().password(CREDENTIALS_MIN_LENGTH, CREDENTIALS_MAX_LENGTH)
        );
    }

    public static LoginRequest randomEmail() {
        return new LoginRequest(
                faker.internet().emailAddress(),
                AppPropertiesReader.get("password")
        );
    }

    public static LoginRequest emptyEmail() {
        return new LoginRequest(
                "",
                faker.internet().password(CREDENTIALS_MIN_LENGTH, CREDENTIALS_MAX_LENGTH)
        );
    }

    public static LoginRequest emptyPassword() {
        return new LoginRequest(
                AppPropertiesReader.get("email"),
                ""
        );
    }
}