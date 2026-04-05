package org.example.endpoints.todos;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.models.todos.TodoDto;

import static io.restassured.RestAssured.given;

public class TodoUpdateEndpoint {

    private static final String EDIT = "/api/todos/edit/{id}";

    public static Response update(long id, TodoDto todo) {
        return given()
                .spec(RestAssured.requestSpecification)
                .body(todo)
                .pathParam("id", id)
                .when()
                .patch(EDIT);
    }
}
