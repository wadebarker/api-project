package org.example.tests.unit.todos;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.example.base.AuthenticatedBaseTest;
import org.example.endpoints.todos.GetTodosListEndpoint;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("Todos API")
@Feature("List tasks")
@Severity(SeverityLevel.NORMAL)
@Test(groups = {"unit", "integration"})
public class GetTodosListTests extends AuthenticatedBaseTest {

    private static final List<String> RES_TODO_DTO_KEYS = List.of(
            "title", "description", "date", "time", "checked", "id", "userId"
    );

    @Test
    public void testGetTodos_WithBearer_Returns200AndJsonArray() {
        Allure.step("GET /api/todos с Bearer", () -> {
            Response response = GetTodosListEndpoint.getTodos();

            assertThat(response.statusCode(), equalTo(200));
            assertThat(response.contentType(), containsString("application/json"));

            List<?> list = response.jsonPath().getList("$");
            assertThat(list, notNullValue());
        });
    }

    @Test
    public void testGetTodos_EachItemMatchesResTodoDto() {
        Allure.step("Элементы списка соответствуют ResTodoDto", () -> {
            Response response = GetTodosListEndpoint.getTodos();

            assertThat(response.statusCode(), equalTo(200));

            List<Map<String, Object>> items = response.jsonPath().getList("$");
            for (Map<String, Object> item : items) {
                assertThat(item.keySet(), hasItems(RES_TODO_DTO_KEYS.toArray(new String[0])));
                assertThat(item.get("id"), instanceOf(Number.class));
                assertThat(item.get("userId"), instanceOf(Number.class));
                assertThat(item.get("checked"), instanceOf(Boolean.class));
            }
        });
    }

    @Test
    public void testGetTodos_WithoutAuth_Returns401() {
        Allure.step("GET /api/todos без Authorization", () -> {
            Response response = GetTodosListEndpoint.getTodosWithoutAuth();

            assertThat(response.statusCode(), equalTo(401));
            assertThat(response.jsonPath().getInt("statusCode"), equalTo(401));
            assertThat(response.jsonPath().getString("message"), not(emptyOrNullString()));
        });
    }
}
