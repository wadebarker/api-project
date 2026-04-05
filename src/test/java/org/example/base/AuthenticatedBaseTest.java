package org.example.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.config.AppPropertiesReader;
import org.example.endpoints.auth.LoginEndpoint;
import org.example.models.auth.LoginRequest;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

/**
 * Для тестов, где все сценарии идут с Bearer-токеном: один логин на класс и общая
 * {@link RestAssured#requestSpecification} для эндпоинтов, которые вызывают {@code .spec(...)}.
 */
public abstract class AuthenticatedBaseTest extends BaseTest {

    @BeforeClass(alwaysRun = true)
    public void setupAuthenticated() {
        Response response = LoginEndpoint.login(
                new LoginRequest(
                        AppPropertiesReader.get("email"),
                        AppPropertiesReader.get("password")
                )
        );

        String accessToken = response.jsonPath().getString("accessToken");

        RestAssured.requestSpecification = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken);
    }

    /** После логина вручную (например смена пароля) обновить глобальную спецификацию. */
    protected static void setBearerAccessToken(String accessToken) {
        RestAssured.requestSpecification = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken);
    }
}
