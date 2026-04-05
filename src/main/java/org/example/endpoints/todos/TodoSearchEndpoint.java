package org.example.endpoints.todos;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoSearchEndpoint {

    private static final String SEARCH = "/api/todos/search";

    public static Response searchByTitle(String title) {
        return given()
                .spec(RestAssured.requestSpecification)
                .queryParam("title", title)
                .when()
                .get(SEARCH);
    }
}
