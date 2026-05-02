package org.example.tests.unit.todos;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.example.base.AuthenticatedBaseTest;
import org.example.dataproviders.TodoUnitDataProvider;
import org.example.endpoints.todos.CreateNewTodoEndpoint;
import org.example.models.todos.TodoDto;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("Todos API")
@Feature("Create task")
@Severity(SeverityLevel.CRITICAL)
@Test(groups = {"unit", "integration"})
public class CreateNewTodoTests extends AuthenticatedBaseTest {

    @Test(dataProvider = "validTodosForCreate", dataProviderClass = TodoUnitDataProvider.class)
    public void createTodoReturns201AndResTodoDto(TodoDto todo) {
        Allure.step("POST /api/todos/create с валидным TodoDto", () -> {
            Response response = CreateNewTodoEndpoint.createTodo(todo);

            Allure.step("Проверка статуса 201 и полей ResTodoDto", () -> {
                assertThat(response.getStatusCode(), equalTo(201));
                assertThat(response.jsonPath().getString("title"), equalTo(todo.getTitle()));
                assertThat(response.jsonPath().getString("description"), equalTo(todo.getDescription()));
                assertThat(response.jsonPath().getString("date"), equalTo(todo.getDate()));
                assertThat(response.jsonPath().getString("time"), equalTo(todo.getTime()));
                assertThat(response.jsonPath().getBoolean("checked"), equalTo(todo.isChecked()));
                assertThat(response.jsonPath().getLong("id"), greaterThan(0L));
                assertThat(response.jsonPath().getLong("userId"), greaterThan(0L));
            });
        });
    }

    @Test(dataProvider = "invalidTodosForCreate", dataProviderClass = TodoUnitDataProvider.class)
    public void createTodoReturns400ForInvalidBody(TodoDto todo, String responseMustContain) {
        Allure.step("POST /api/todos/create с невалидным телом", () -> {
            Response response = CreateNewTodoEndpoint.createTodo(todo);

            Allure.step("Ожидается 4xx и упоминание поля в теле (контракт валидации)", () -> {
                assertThat(response.getStatusCode(), anyOf(equalTo(400), equalTo(422)));
                assertThat(response.asString(), containsString(responseMustContain));
            });
        });
    }
}
