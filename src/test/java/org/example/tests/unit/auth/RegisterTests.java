package org.example.tests.unit.auth;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.example.base.BaseTest;
import org.example.dataproviders.RegisterDataProvider;
import org.example.endpoints.auth.RegisterEndpoint;
import org.example.models.auth.RegisterRequest;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Severity(SeverityLevel.BLOCKER)
@Epic("User Authentication Module")
@Feature("Register Functionality")
public class RegisterTests extends BaseTest {

    @Test(dataProvider = "positiveRegisterData", dataProviderClass = RegisterDataProvider.class)
    public void registerPositive(RegisterRequest request) {

        Allure.step("Perform positive registration for email: " + request.getEmail() + " and password: " + request.getPassword(), () -> {
            Response response = RegisterEndpoint.register(request);

            Allure.step("Verify response status code is 201", () ->
                    assertThat(response.statusCode(), equalTo(201))
            );
        });
    }

    @Test(dataProvider = "negativeRegisterData", dataProviderClass = RegisterDataProvider.class)
    public void registerNegative(RegisterRequest request) {

        Allure.step("Perform negative registration for email: " + request.getEmail() + " and password: " + request.getPassword(), () -> {
            Response response = RegisterEndpoint.register(request);

            Allure.step("Verify response status code is 400", () ->
                    assertThat(response.statusCode(), equalTo(400))
            );
        });
    }
}

