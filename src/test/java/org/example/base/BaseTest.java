package org.example.base;

import io.restassured.RestAssured;
import org.example.config.ApiConfig;
import org.testng.annotations.BeforeClass;

/**
 * Базовая подготовка окружения без авторизации
 */
public abstract class BaseTest {

    @BeforeClass
    public void setupBase() {
        ApiConfig.setup();
        RestAssured.requestSpecification = null;
    }
}
