package org.example.endpoints.register;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.example.models.register.RegisterRequest;

import static io.restassured.RestAssured.given;

public class RegisterEndpoint {

    private static final String REGISTER = "/api/auth/register";

    @Step("Attach response body")
    private static void attachResponse(Response response) {
        Allure.addAttachment("Response Body", "application/json", response.asPrettyString(), ".json");
    }

    @Step("Register user with email: {request.email}")
    public static Response register(RegisterRequest request) {

        Response response = given()
                                .filter(new AllureRestAssured())
                                .contentType(ContentType.JSON)
                                .body(request)
                            .when()
                                .post(REGISTER)
                            .then()
                                .extract()
                                .response();

        attachResponse(response);

        return response;
    }
}