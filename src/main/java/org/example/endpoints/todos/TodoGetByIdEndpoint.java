package org.example.endpoints.todos;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoGetByIdEndpoint {

    private static final String BY_ID = "/api/todos/{id}";

    public static Response getById(long id) {
        return given()
                .spec(RestAssured.requestSpecification)
                .pathParam("id", id)
                .when()
                .get(BY_ID);
    }
}
