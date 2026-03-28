package org.example.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.config.ApiConfig;
import org.example.config.AppPropertiesReader;
import org.example.endpoints.auth.LoginEndpoint;
import org.example.models.auth.LoginRequest;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

public abstract class BaseTest {

    @BeforeClass
    public void setup() {
        ApiConfig.setup();

        // Авторизация
        Response response = LoginEndpoint.login(
                new LoginRequest(
                        AppPropertiesReader.get("email"),
                        AppPropertiesReader.get("password")
                )
        );

        // Получаем токен
        String accessToken = response.jsonPath().getString("accessToken");

        // Глобальный requestSpecification
        RestAssured.requestSpecification = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken);
    }
}