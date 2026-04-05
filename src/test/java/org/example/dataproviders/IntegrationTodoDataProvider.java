package org.example.dataproviders;

import org.example.factory.TodoDataFactory;
import org.example.models.todos.TodoDto;
import org.testng.annotations.DataProvider;

public final class IntegrationTodoDataProvider {

    private IntegrationTodoDataProvider() {
    }

    @DataProvider(name = "integrationCreateTodos")
    public static Object[][] integrationCreateTodos() {
        return new Object[][]{
                {TodoDataFactory.parameterizedTask("Alpha")},
                {TodoDataFactory.parameterizedTask("Beta")}
        };
    }

    /** Слуги для уникальных заголовков при создании + поиске в одном тесте. */
    @DataProvider(name = "integrationSearchSlugs")
    public static Object[][] integrationSearchSlugs() {
        return new Object[][]{
                {"Sun"},
                {"Moon"}
        };
    }
}
