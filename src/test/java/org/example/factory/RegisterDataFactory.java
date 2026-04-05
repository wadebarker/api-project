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

    /** Короткий валидный email (на бэкенде иногда min длина строже, чем в Swagger). */
    public static RegisterRequest emailMinLength() {
        return new RegisterRequest(
                "ab@c.ru",
                faker.internet().password(CREDENTIALS_MIN_LENGTH, CREDENTIALS_MAX_LENGTH)
        );
    }

    /** Ровно 6 символов, есть буквы и цифра (часто требуется бэкендом). */
    public static RegisterRequest passwordMinLength() {
        return new RegisterRequest(
                uniqueEmail(),
                "Qwert1"
        );
    }

    /** Локальная часть только [a-z], домен валидный для regex в OpenAPI, всего 50 символов. */
    public static RegisterRequest emailMaxLength() {
        String local = "a".repeat(40);
        return new RegisterRequest(
                local + "@test.mail",
                faker.internet().password(CREDENTIALS_MIN_LENGTH, CREDENTIALS_MAX_LENGTH)
        );
    }

    public static RegisterRequest passwordMaxLength() {
        String pwd = "A1a" + "x".repeat(CREDENTIALS_MAX_LENGTH - 3);
        return new RegisterRequest(
                uniqueEmail(),
                pwd
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