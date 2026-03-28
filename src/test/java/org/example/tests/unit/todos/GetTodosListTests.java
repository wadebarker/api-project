//package org.example.tests.todos;
//
//import org.example.base.BaseTest;
//import org.example.endpoints.todos.GetTodosListEndpoint;
//import org.testng.annotations.Test;
//import io.restassured.response.Response;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//
//public class GetTodosListTests extends BaseTest {
//
//    @Test
//    public void testGetTodos_EmptyList() {
//        Response response = GetTodosListEndpoint.getTodos();
//
//        // проверка statusCode
//        assertThat(response.statusCode(), equalTo(200));
//
//        // проверка, что пришёл пустой список []
//        assertThat(response.jsonPath().getList("$"), is(empty()));
//    }
//}
