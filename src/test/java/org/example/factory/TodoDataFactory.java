package org.example.factory;

import net.datafaker.Faker;
import org.example.models.todos.TodoDto;

public final class TodoDataFactory {

    private static final Faker FAKER = new Faker();

    private TodoDataFactory() {
    }

    public static TodoDto crudFlowTask() {
        String key = FAKER.lorem().word();
        return new TodoDto(
                "CRUD " + key,
                "Integration flow " + FAKER.lorem().sentence(3),
                "2026-06-15",
                "14:30",
                false
        );
    }

    public static TodoDto parameterizedTask(String titleSuffix) {
        return new TodoDto(
                "Param " + titleSuffix,
                FAKER.lorem().sentence(4),
                "2026-07-20",
                "09:15",
                true
        );
    }

    public static TodoDto updatedTaskFrom(TodoDto original) {
        return new TodoDto(
                original.getTitle() + " updated",
                original.getDescription() + " (edited)",
                "2026-08-01",
                "18:00",
                !original.isChecked()
        );
    }

    public static TodoDto invalidEmptyTitle() {
        return new TodoDto("", "Описание", "2026-01-10", "10:00", false);
    }

    /** Длина title &gt; 50 (лимит OpenAPI). */
    public static TodoDto invalidTitleTooLong() {
        return new TodoDto("x".repeat(51), "Описание", "2026-01-10", "10:00", false);
    }
}
