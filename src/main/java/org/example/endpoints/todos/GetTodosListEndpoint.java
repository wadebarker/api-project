package org.example.endpoints.todos;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * GET /api/todos — список задач пользователя (OpenAPI: TodosController_todos, security: bearer JWT).
 */
public class GetTodosListEndpoint {

    private static final String GET_TODOS = "/api/todos";

    /** Запрос с токеном из {@link RestAssured#requestSpecification} (задаётся в {@link org.example.base.AuthenticatedBaseTest}). */
    public static Response getTodos() {
        return given()
                .spec(RestAssured.requestSpecification)
                .accept(ContentType.JSON)
                .when()
                .get(GET_TODOS);
    }

    /**
     * Тот же путь без Bearer: глобальная {@link RestAssured#requestSpecification} подмешивается в каждый {@code given()},
     * поэтому на время запроса она снимается и восстанавливается (последовательный запуск тестов).
     */
    public static Response getTodosWithoutAuth() {
        RequestSpecification previous = RestAssured.requestSpecification;
        RestAssured.requestSpecification = null;
        try {
            return given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .when()
                    .get(GET_TODOS);
        } finally {
            RestAssured.requestSpecification = previous;
        }
    }
}
