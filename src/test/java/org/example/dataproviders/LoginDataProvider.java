package org.example.dataproviders;

import org.example.factory.LoginDataFactory;
import org.testng.annotations.DataProvider;

public class LoginDataProvider {

    @DataProvider(name = "positiveLogin")
    public static Object[][] positiveLogin() {
        return new Object[][]{
                {LoginDataFactory.validUser()}
        };
    }

    @DataProvider(name = "negativeLogin")
    public static Object[][] negativeLogin() {
        return new Object[][]{
                {LoginDataFactory.wrongPassword()},
                {LoginDataFactory.randomEmail()},
                {LoginDataFactory.emptyEmail()},
                {LoginDataFactory.emptyPassword()}
        };
    }
}