package org.example.tests.e2e.todos;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.example.base.AuthenticatedBaseTest;
import org.example.endpoints.todos.CreateNewTodoEndpoint;
import org.example.endpoints.todos.GetTodosListEndpoint;
import org.example.endpoints.todos.TodoDeleteEndpoint;
import org.example.endpoints.todos.TodoGetByIdEndpoint;
import org.example.endpoints.todos.TodoUpdateEndpoint;
import org.example.factory.TodoDataFactory;
import org.example.models.todos.TodoDto;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Сквозной сценарий: создание дела → чтение → список → обновление → удаление → отсутствие по id.
 */
@Epic("Todos")
@Feature("End-to-end CRUD")
@Severity(SeverityLevel.CRITICAL)
@Test(groups = {"integration", "e2e"})
public class TodosCrudE2eTest extends AuthenticatedBaseTest {

    @Test
    @Story("Пользователь создаёт задачу, правит её и удаляет")
    public void todoFullCrudFlow() {
        TodoDto draft = TodoDataFactory.crudFlowTask();
        final long[] idHolder = new long[1];

        Allure.step("POST /api/todos/create", () -> {
            Response response = CreateNewTodoEndpoint.createTodo(draft);
            assertThat(response.getStatusCode(), equalTo(201));
            idHolder[0] = response.jsonPath().getLong("id");
            assertThat(idHolder[0], greaterThan(0L));
            assertThat(response.jsonPath().getString("title"), equalTo(draft.getTitle()));
        });

        long id = idHolder[0];

        Allure.step("GET /api/todos/{id} — задача доступна", () -> {
            Response response = TodoGetByIdEndpoint.getById(id);
            assertThat(response.getStatusCode(), equalTo(200));
            assertThat(response.jsonPath().getString("title"), equalTo(draft.getTitle()));
        });

        Allure.step("GET /api/todos — список содержит созданную задачу", () -> {
            Response response = GetTodosListEndpoint.getTodos();
            assertThat(response.getStatusCode(), equalTo(200));
            List<Map<String, Object>> items = response.jsonPath().getList("$");
            boolean found = items.stream().anyMatch(m -> idOf(m) == id);
            assertThat("Список должен содержать id=" + id, found, is(true));
        });

        TodoDto updated = TodoDataFactory.updatedTaskFrom(draft);

        Allure.step("PATCH /api/todos/edit/{id} — обновление полей", () -> {
            Response response = TodoUpdateEndpoint.update(id, updated);
            assertThat(response.getStatusCode(), equalTo(200));
            assertThat(response.jsonPath().getString("title"), equalTo(updated.getTitle()));
            assertThat(response.jsonPath().getBoolean("checked"), equalTo(updated.isChecked()));
        });

        Allure.step("GET /api/todos/{id} — отражены изменения", () -> {
            Response response = TodoGetByIdEndpoint.getById(id);
            assertThat(response.getStatusCode(), equalTo(200));
            assertThat(response.jsonPath().getString("title"), equalTo(updated.getTitle()));
        });

        Allure.step("DELETE /api/todos/delete/{id}", () -> {
            Response response = TodoDeleteEndpoint.delete(id);
            assertThat(response.getStatusCode(), equalTo(204));
        });

        Allure.step("GET /api/todos/{id} — после удаления не найдено", () -> {
            Response response = TodoGetByIdEndpoint.getById(id);
            assertThat(response.getStatusCode(), equalTo(404));
        });
    }

    private static long idOf(Map<String, Object> row) {
        Object raw = row.get("id");
        return raw instanceof Number ? ((Number) raw).longValue() : -1L;
    }
}
