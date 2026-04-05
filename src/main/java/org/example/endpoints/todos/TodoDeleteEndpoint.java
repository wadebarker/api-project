package org.example.endpoints.todos;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoDeleteEndpoint {

    private static final String DELETE = "/api/todos/delete/{id}";

    public static Response delete(long id) {
        return given()
                .spec(RestAssured.requestSpecification)
                .pathParam("id", id)
                .when()
                .delete(DELETE);
    }
}
