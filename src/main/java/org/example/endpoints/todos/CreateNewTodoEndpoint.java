package org.example.endpoints.todos;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.models.todos.TodoDto;

import static io.restassured.RestAssured.given;

public class CreateNewTodoEndpoint {

    private static final String CREATE_TODO = "/api/todos/create";

    public static Response createTodo(TodoDto todo) {
        return given()
                .spec(RestAssured.requestSpecification)
                .body(todo)
                .when()
                .post(CREATE_TODO);
    }

    public static Response createTodo(String title, String description, String date, String time, boolean checked) {
        return createTodo(new TodoDto(title, description, date, time, checked));
    }
}
