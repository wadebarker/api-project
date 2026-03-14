package org.example.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.config.Credentials;
import org.example.endpoints.login.history.LoginEndpoint;
import org.example.config.ApiConfig;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;


public abstract class BaseTest {

    @BeforeClass
    public void setup() {
        ApiConfig.setup();

        // Авторизация
        Response response = LoginEndpoint.login(
                Credentials.EMAIL,
                Credentials.PASSWORD
        );


        // Получаем токена
        String accessToken = response.jsonPath().getString("accessToken");

        // Создаём глобальный requestSpecification
        RestAssured.requestSpecification = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken);
    }
}
