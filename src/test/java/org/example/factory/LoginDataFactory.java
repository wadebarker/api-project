package org.example.factory;

import net.datafaker.Faker;
import org.example.config.Credentials;
import org.example.models.login.LoginRequest;

// factory (генерация данных)
public class LoginDataFactory {

    private static final Faker faker = new Faker();

    public static LoginRequest validUser() {
        return new LoginRequest(
                Credentials.EMAIL,
                Credentials.PASSWORD
        );
    }

    public static LoginRequest wrongPassword() {
        return new LoginRequest(
                Credentials.EMAIL,
                faker.internet().password(8, 12)
        );
    }

    public static LoginRequest randomEmail() {
        return new LoginRequest(
                faker.internet().emailAddress(),
                Credentials.PASSWORD
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
                Credentials.EMAIL,
                ""
        );
    }
}