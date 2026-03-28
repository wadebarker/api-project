package org.example.factory;

import net.datafaker.Faker;
import org.example.models.auth.RegisterRequest;

// factory (генерация данных)
public class RegisterDataFactory {

    private static final Faker faker = new Faker();

    private static String uniqueEmail() {
        return faker.internet().emailAddress() + System.currentTimeMillis();
    }

    // ✅ VALID USER
    public static RegisterRequest validUser() {
        return new RegisterRequest(
                uniqueEmail(),
                faker.internet().password(6, 20)
        );
    }

    // ✅ EMAIL = 6 characters
    public static RegisterRequest emailMinLength() {
        return new RegisterRequest(
                "b@b.ru",
                faker.internet().password(6, 20)
        );
    }

    // ✅ PASSWORD = 6 characters
    public static RegisterRequest passwordMinLength() {
        return new RegisterRequest(
                uniqueEmail(),
                "123456"
        );
    }

    // ✅ EMAIL = 50 characters
    public static RegisterRequest emailMaxLength() {
        String email = faker.lorem().characters(40) + "@mail.com";
        return new RegisterRequest(
                email,
                faker.internet().password(6, 20)
        );
    }

    // ✅ PASSWORD = 50 characters
    public static RegisterRequest passwordMaxLength() {
        return new RegisterRequest(
                uniqueEmail(),
                faker.lorem().characters(50)
        );
    }

    // ❌ EMAIL < 6
    public static RegisterRequest shortEmail() {
        return new RegisterRequest(
                "a@b.c",
                faker.internet().password(6, 20)
        );
    }

    // ❌ PASSWORD < 6
    public static RegisterRequest shortPassword() {
        return new RegisterRequest(
                uniqueEmail(),
                "12345"
        );
    }

    // ❌ EMAIL > 50
    public static RegisterRequest longEmail() {
        String email = faker.lorem().characters(42) + "@mail.com";
        return new RegisterRequest(
                email,
                faker.internet().password(6, 20)
        );
    }

    // ❌ PASSWORD > 50
    public static RegisterRequest longPassword() {
        return new RegisterRequest(
                uniqueEmail(),
                faker.lorem().characters(51)
        );
    }

    // ❌ EMPTY EMAIL
    public static RegisterRequest emptyEmail() {
        return new RegisterRequest(
                "",
                faker.internet().password(6, 20)
        );
    }

    // ❌ EMPTY PASSWORD
    public static RegisterRequest emptyPassword() {
        return new RegisterRequest(
                uniqueEmail(),
                ""
        );
    }

    // ❌ EMAIL WITHOUT @
    public static RegisterRequest emailWithoutAt() {
        return new RegisterRequest(
                "invalidemail.com",
                faker.internet().password(6, 20)
        );
    }

    // ❌ DUPLICATE EMAIL
    public static RegisterRequest duplicateEmail(String email) {
        return new RegisterRequest(
                email,
                faker.internet().password(6, 20)
        );
    }
}