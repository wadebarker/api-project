package org.example.endpoints.login;

import io.qameta.allure.Step;
import io.qameta.allure.Attachment;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.example.models.login.LoginRequest;

import static io.restassured.RestAssured.given;

public class LoginEndpoint {

    private static final String LOGIN = "/api/auth/login";

    // Вложение для тела ответа
    @Attachment(value = "Response body", type = "application/json")
    public static String attachResponse(Response response) {
        return response.asPrettyString();
    }

    // Шаг для логина
    @Step("Login with email: {request.email}")
    public static Response login(LoginRequest request) {
        Response response = given()
                                .filter(new AllureRestAssured()) // авто-логирование запроса/ответа
                                .contentType(ContentType.JSON)
                                .body(request)
                            .when()
                                .post(LOGIN)
                            .then()
                                .extract()
                                .response();

        attachResponse(response); // добавление тела ответа в отчет
        return response;
    }
}