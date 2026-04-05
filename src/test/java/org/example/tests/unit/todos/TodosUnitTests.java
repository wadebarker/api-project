package org.example.tests.unit.todos;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.example.base.AuthenticatedBaseTest;
import org.example.dataproviders.IntegrationTodoDataProvider;
import org.example.endpoints.todos.CreateNewTodoEndpoint;
import org.example.endpoints.todos.GetTodosListEndpoint;
import org.example.endpoints.todos.TodoDeleteEndpoint;
import org.example.endpoints.todos.TodoGetByIdEndpoint;
import org.example.endpoints.todos.TodoSearchEndpoint;
import org.example.endpoints.todos.TodoUpdateEndpoint;
import org.example.factory.TodoDataFactory;
import org.example.models.todos.TodoDto;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("Todos API")
@Feature("CRUD, list, search")
@Severity(SeverityLevel.NORMAL)
@Test(groups = "unit")
public class TodosUnitTests extends AuthenticatedBaseTest {

    @Test(dataProvider = "integrationCreateTodos", dataProviderClass = IntegrationTodoDataProvider.class)
    public void createTodoReturns201(TodoDto todo) {
        Allure.step("POST /api/todos/create", () -> {
            Response response = CreateNewTodoEndpoint.createTodo(todo);

            assertThat(response.getStatusCode(), equalTo(201));
            assertThat(response.jsonPath().getString("title"), equalTo(todo.getTitle()));
            assertThat(response.jsonPath().getLong("id"), greaterThan(0L));
            assertThat(response.jsonPath().getLong("userId"), greaterThan(0L));
        });
    }

    @Test(dataProvider = "integrationSearchSlugs", dataProviderClass = IntegrationTodoDataProvider.class)
    public void searchByTitleReturns200(String slug) {
        Allure.step("GET /api/todos/search", () -> {
            TodoDto todo = TodoDataFactory.parameterizedTask(slug);
            assertThat(CreateNewTodoEndpoint.createTodo(todo).getStatusCode(), equalTo(201));

            Response search = TodoSearchEndpoint.searchByTitle(todo.getTitle());

            assertThat(search.getStatusCode(), equalTo(200));
            List<Map<String, Object>> list = search.jsonPath().getList("$");
            assertThat(list.size(), greaterThanOrEqualTo(1));
        });
    }

    @Test
    public void listTodosReturns200() {
        Allure.step("GET /api/todos", () -> {
            Response response = GetTodosListEndpoint.getTodos();

            assertThat(response.getStatusCode(), equalTo(200));
            assertThat(response.jsonPath().getList("$"), notNullValue());
        });
    }

    @Test
    public void getTodoByUnknownIdIsNotOk200() {
        Allure.step("GET /api/todos/{id} для несуществующего id", () -> {
            Response response = TodoGetByIdEndpoint.getById(999_999L);

            assertThat(response.getStatusCode(), not(equalTo(200)));
        });
    }

    @Test
    public void todoLifecyclePatchAndDelete() {
        Allure.step("PATCH /api/todos/edit/{id} и DELETE /api/todos/delete/{id}", () -> {
            TodoDto created = TodoDataFactory.crudFlowTask();
            Response createRes = CreateNewTodoEndpoint.createTodo(created);
            assertThat(createRes.getStatusCode(), equalTo(201));
            long id = createRes.jsonPath().getLong("id");

            Response getOne = TodoGetByIdEndpoint.getById(id);
            assertThat(getOne.getStatusCode(), equalTo(200));
            assertThat(getOne.jsonPath().getString("title"), equalTo(created.getTitle()));

            TodoDto patch = TodoDataFactory.updatedTaskFrom(created);
            Response patchRes = TodoUpdateEndpoint.update(id, patch);
            assertThat(patchRes.getStatusCode(), equalTo(200));
            assertThat(patchRes.jsonPath().getString("title"), equalTo(patch.getTitle()));

            Response deleteRes = TodoDeleteEndpoint.delete(id);
            assertThat(deleteRes.getStatusCode(), equalTo(204));

            assertThat(TodoGetByIdEndpoint.getById(id).getStatusCode(), equalTo(404));
        });
    }
}
