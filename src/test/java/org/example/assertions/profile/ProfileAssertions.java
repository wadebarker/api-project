package org.example.assertions.profile;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.example.models.profile.ProfileDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public final class ProfileAssertions {

    private ProfileAssertions() {
    }

    public static void assertSaveResponseMatchesRequest(Response response, ProfileDto request) {
        Allure.step("Проверка тела ResProfileDto после save", () -> {
            assertThat(response.jsonPath().getLong("id"), greaterThan(0L));
            assertThat(response.jsonPath().getLong("userId"), greaterThan(0L));
            assertThat(response.jsonPath().getString("name"), equalTo(request.getName()));
            assertThat(response.jsonPath().getString("surname"), equalTo(request.getSurname()));
            assertThat(response.jsonPath().getString("phone"), equalTo(request.getPhone()));
        });
    }

    public static void assertGetProfileMatches(Response response, ProfileDto expected) {
        Allure.step("Проверка GET профиля", () -> {
            assertThat(response.jsonPath().getString("phone"), equalTo(expected.getPhone()));
            assertThat(response.jsonPath().getString("surname"), equalTo(expected.getSurname()));
            assertThat(response.jsonPath().getString("name"), equalTo(expected.getName()));
        });
    }
}
