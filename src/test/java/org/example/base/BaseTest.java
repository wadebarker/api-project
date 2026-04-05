package org.example.base;

import io.restassured.RestAssured;
import org.example.config.ApiConfig;
import org.testng.annotations.BeforeClass;

/**
 * Базовая подготовка окружения без авторизации.
 * <p>
 * {@code @BeforeClass} в TestNG вызывается один раз на класс (до всех {@code @Test} этого класса),
 * а не перед каждым HTTP-запросом. Глобальный токен задаётся не здесь, а в {@link AuthenticatedBaseTest}.
 */
public abstract class BaseTest {

    @BeforeClass
    public void setupBase() {
        ApiConfig.setup();
        RestAssured.requestSpecification = null;
    }
}
