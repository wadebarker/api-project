package org.example.dataproviders;

import org.example.factory.TodoDataFactory;
import org.example.models.todos.TodoDto;
import org.testng.annotations.DataProvider;

/**
 * Параметризация unit-тестов создания todo: невалидные тела и фрагмент текста ответа.
 */
public final class TodoUnitDataProvider {

    private TodoUnitDataProvider() {
    }

    @DataProvider(name = "validTodosForCreate")
    public static Object[][] validTodosForCreate() {
        return new Object[][]{
                {TodoDataFactory.parameterizedTask("UnitA")},
                {TodoDataFactory.parameterizedTask("UnitB")}
        };
    }

    /**
     * Негативные кейсы, которые реально отклоняются текущим API (остальные «невалидные» тела в инстансе дают 201).
     */
    @DataProvider(name = "invalidTodosForCreate")
    public static Object[][] invalidTodosForCreate() {
        return new Object[][]{
                {TodoDataFactory.invalidEmptyTitle(), "title"},
                {TodoDataFactory.invalidTitleTooLong(), "title"}
        };
    }
}
