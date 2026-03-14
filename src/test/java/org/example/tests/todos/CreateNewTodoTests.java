//package org.example.tests.todos;
//
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//import io.restassured.response.Response;
//
//import org.example.base.BaseTest;
//import org.example.endpoints.todos.CreateNewTodoEndpoint;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//
//public class CreateNewTodoTests extends BaseTest {
//
//    @Test
//    public void testCreateTodo_Success() {
//        Response response = CreateNewTodoEndpoint.createTodo(
//                "Урок по Java",
//                "Отправка токенов в запросах",
//                "2025-11-21",
//                "11:30",
//                true
//        );
//
//        // Проверка статус-кода
//        assertThat(response.statusCode(), equalTo(201));
//
//        // Проверка полей тела ответа
//        assertThat(response.jsonPath().getString("title"), equalTo("Урок по Java"));
//        assertThat(response.jsonPath().getString("description"), equalTo("Отправка токенов в запросах"));
//        assertThat(response.jsonPath().getString("date"), equalTo("2025-11-21"));
//        assertThat(response.jsonPath().getString("time"), equalTo("11:30"));
//        assertThat(response.jsonPath().getBoolean("checked"), equalTo(true));
//
//        // Проверка полей id и userId (не null)
//        assertThat(response.jsonPath().getInt("id"), greaterThan(0));
//        assertThat(response.jsonPath().getInt("userId"), greaterThan(0));
//    }
//
//    @Test
//    public void testCreateTodo_MissingTitle() {
//        // title пустой
//        Response response = CreateNewTodoEndpoint.createTodo(
//                "",
//                "Отправка токенов в запросах",
//                "2025-11-21",
//                "11:30",
//                true
//        );
//
//        // Проверка статус-кода 400 (Bad Request)
//        assertThat(response.statusCode(), equalTo(400));
//
//        // Проверка тела ответа с ошибкой
//        assertThat(response.jsonPath().getString("error"), equalTo("Bad Request"));
//        assertThat(response.jsonPath().getList("message"), hasItem("title must be longer than or equal to 1 characters"));
//    }
//
//    // Параметризованный тест для проверки обязательных полей
//    // ------------------------
//    @DataProvider(name = "invalidTodoData")
//    public Object[][] invalidTodoData() {
//        return new Object[][]{
//                { "", "Описание", "2025-11-21", "11:30", true, "title must be longer than or equal to 1 characters" },
//                { "Заголовок", "", "2025-11-21", "11:30", true, "description must be longer than or equal to 1 characters" },
//                { "Заголовок", "Описание", "", "11:30", true, "date is required field" },
//                { "Заголовок", "Описание", "21-11-2025", "11:30", true, "date must be in format yyyy-MM-dd" },
//                { "Заголовок", "Описание", "2025-11-21", "", true, "time is required field" },
//                { "Заголовок", "Описание", "2025-11-21", "25:61", true, "time must be in format HH:mm" }
//        };
//    }
//
//    @Test(dataProvider = "invalidTodoData")
//    public void testCreateTodo_InvalidFields(String title, String description, String date, String time, boolean checked, String expectedMessage) {
//        Response response = CreateNewTodoEndpoint.createTodo(title, description, date, time, checked);
//
//        // Проверка статус-кода 400 (Bad Request)
//        assertThat(response.statusCode(), equalTo(400));
//        assertThat(response.jsonPath().getString("error"), equalTo("Bad Request"));
//
//        // Проверка сообщения ошибки содержит ожидаемый текст
//        assertThat(response.jsonPath().getList("message"), hasItem(expectedMessage));
//    }
//}
