package org.example.factory;

import net.datafaker.Faker;
import org.example.models.auth.LoginRequest;
import org.example.config.AppPropertiesReader;

// factory (генерация данных)
public class LoginDataFactory {

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
                faker.internet().password(8, 12)
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
                faker.internet().password(8, 12)
        );
    }

    public static LoginRequest emptyPassword() {
        return new LoginRequest(
                AppPropertiesReader.get("email"),
                ""
        );
    }
}