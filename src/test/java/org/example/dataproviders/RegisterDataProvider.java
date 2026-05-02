package org.example.dataproviders;

import org.example.factory.RegisterDataFactory;
import org.testng.annotations.DataProvider;

public class RegisterDataProvider {

    @DataProvider(name = "positiveRegisterData")
    public static Object[][] positiveRegisterData() {
        return new Object[][] {
                {RegisterDataFactory.validUser()},
                {RegisterDataFactory.validUser()}
        };
    }

    @DataProvider(name = "negativeRegisterData")
    public static Object[][] negativeRegisterData() {
        return new Object[][] {
                {RegisterDataFactory.shortEmail()},
                {RegisterDataFactory.longEmail()},
                {RegisterDataFactory.shortPassword()},
                {RegisterDataFactory.longPassword()},
                {RegisterDataFactory.emptyEmail()},
                {RegisterDataFactory.emptyPassword()},
                {RegisterDataFactory.emailWithoutAt()}
        };
    }
}