package org.example.factory;

import net.datafaker.Faker;
import org.example.config.AppPropertiesReader;
import org.example.models.auth.RegisterRequest;

// factory (генерация данных)
public class RegisterDataFactory {

    public static final int CREDENTIALS_MIN_LENGTH = 6;
    public static final int CREDENTIALS_MAX_LENGTH = 50;
    private static final Faker faker = new Faker();

    private static String uniqueEmail() {
        return faker.internet().emailAddress();
    }

    public static RegisterRequest validUser() {
        return new RegisterRequest(
                uniqueEmail(),
                faker.internet().password(CREDENTIALS_MIN_LENGTH, CREDENTIALS_MAX_LENGTH)
        );
    }

    public static RegisterRequest emailMinLength() {
        return new RegisterRequest(
                "b@b.ru",
                faker.internet().password(CREDENTIALS_MIN_LENGTH, CREDENTIALS_MAX_LENGTH)
        );
    }

    public static RegisterRequest passwordMinLength() {
        return new RegisterRequest(
                uniqueEmail(),
                faker.lorem().characters(CREDENTIALS_MIN_LENGTH)
        );
    }

    public static RegisterRequest emailMaxLength() {
        String email = faker.lorem().characters(40) + "@mail.com";
        return new RegisterRequest(
                email,
                faker.internet().password(CREDENTIALS_MIN_LENGTH, CREDENTIALS_MAX_LENGTH)
        );
    }

    public static RegisterRequest passwordMaxLength() {
        return new RegisterRequest(
                uniqueEmail(),
                faker.lorem().characters(CREDENTIALS_MAX_LENGTH)
        );
    }

    public static RegisterRequest shortEmail() {
        return new RegisterRequest(
                "a@b.c",
                faker.internet().password(CREDENTIALS_MIN_LENGTH, CREDENTIALS_MAX_LENGTH)
        );
    }

    public static RegisterRequest shortPassword() {
        return new RegisterRequest(
                uniqueEmail(),
                faker.lorem().characters(CREDENTIALS_MIN_LENGTH - 1)
        );
    }

    public static RegisterRequest longEmail() {
        String email = faker.lorem().characters(42) + "@mail.com";
        return new RegisterRequest(
                email,
                faker.internet().password(CREDENTIALS_MIN_LENGTH, CREDENTIALS_MAX_LENGTH)
        );
    }

    public static RegisterRequest longPassword() {
        return new RegisterRequest(
                uniqueEmail(),
                faker.lorem().characters(CREDENTIALS_MAX_LENGTH + 1)
        );
    }

    public static RegisterRequest emptyEmail() {
        return new RegisterRequest(
                "",
                faker.internet().password(CREDENTIALS_MIN_LENGTH, CREDENTIALS_MAX_LENGTH)
        );
    }

    public static RegisterRequest emptyPassword() {
        return new RegisterRequest(
                uniqueEmail(),
                ""
        );
    }

    public static RegisterRequest emailWithoutAt() {
        return new RegisterRequest(
                "invalidemail.com",
                faker.internet().password(CREDENTIALS_MIN_LENGTH, CREDENTIALS_MAX_LENGTH)
        );
    }

    public static RegisterRequest duplicateEmail() {
        return new RegisterRequest(
                AppPropertiesReader.get("email"),
                faker.internet().password(CREDENTIALS_MIN_LENGTH, CREDENTIALS_MAX_LENGTH)
        );
    }
}