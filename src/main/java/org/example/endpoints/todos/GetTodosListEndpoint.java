package org.example.endpoints.todos;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetTodosListEndpoint {

    private static final String GET_TODOS = "/api/todos";

    public static Response getTodos() {
        return given()
                    .spec(RestAssured.requestSpecification)
                .when()
                    .get(GET_TODOS);
    }
}
