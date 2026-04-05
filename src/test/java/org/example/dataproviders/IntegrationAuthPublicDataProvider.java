package org.example.dataproviders;

import org.example.factory.LoginDataFactory;
import org.example.factory.RegisterDataFactory;
import org.testng.annotations.DataProvider;

/**
 * Публичные auth-ручки: логин и регистрация (без предварительного Bearer в {@code @BeforeClass}).
 */
public final class IntegrationAuthPublicDataProvider {

    private IntegrationAuthPublicDataProvider() {
    }

    @DataProvider(name = "integrationLoginSuccess")
    public static Object[][] integrationLoginSuccess() {
        return new Object[][]{
                {LoginDataFactory.validUser()}
        };
    }

    @DataProvider(name = "integrationRegisterValid")
    public static Object[][] integrationRegisterValid() {
        return new Object[][]{
                {RegisterDataFactory.validUser()}
        };
    }

    /** Негативные кейсы логина для интеграции (без дублирования всего unit-набора). */
    @DataProvider(name = "integrationLoginUnauthorized")
    public static Object[][] integrationLoginUnauthorized() {
        return new Object[][]{
                {LoginDataFactory.wrongPassword()},
                {LoginDataFactory.randomEmail()}
        };
    }
}
