//package org.example.tests.auth.login.history;
//
//import org.example.config.Credentials;
//import org.testng.annotations.Test;
//import io.restassured.response.Response;
//
//import org.example.base.BaseTest;
//import org.example.endpoints.login.history.LoginEndpoint;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//
//public class LoginTests extends BaseTest {
//
//    @Test
//    public void testLogin_Success() {
//        Response response = LoginEndpoint.login(Credentials.EMAIL, Credentials.PASSWORD);
//
//        // проверка statusCode
//        assertThat(response.statusCode(), equalTo(200));
//        // проверка поля accessToken
//        assertThat(response.jsonPath().getString("accessToken"), notNullValue());
//        // проверка поля refreshToken
//        assertThat(response.jsonPath().getString("refreshToken"), notNullValue());
//        // проверка поля user.id
//        assertThat(response.jsonPath().getString("user.id"), equalTo("18460"));
//        // проверка поля user.email
//        assertThat(response.jsonPath().getString("user.email"), equalTo("vadim_zviagintsev@mail.ru"));
//    }
//
//    @Test
//    public void testLogin_IncorrectPassword() {
//        Response response = LoginEndpoint.login(Credentials.EMAIL, "wrongPass");
//
//        // проверка statusCode
//        assertThat(response.statusCode(), equalTo(401));
//        // проверка поля statusCode
//        assertThat(response.jsonPath().getString("statusCode"), equalTo("401"));
//        // проверка поля message
//        assertThat(response.jsonPath().getString("message"), equalTo("Unauthorized"));
//    }
//
//    @Test
//    public void testLogin_NotOccupiedEmail() {
//        Response response = LoginEndpoint.login("vadim_director@mail.ru", Credentials.PASSWORD);
//
//        // проверка statusCode
//        assertThat(response.statusCode(), equalTo(401));
//        // проверка поля statusCode
//        assertThat(response.jsonPath().getString("statusCode"), equalTo("401"));
//        // проверка поля message
//        assertThat(response.jsonPath().getString("message"), equalTo("Unauthorized"));
//    }
//
//    @Test
//    public void testLogin_EmptyEmailField() {
//        Response response = LoginEndpoint.login("", "Qwerty123");
//
//        // проверка statusCode
//        assertThat(response.statusCode(), equalTo(400));
//        // проверка поля statusCode
//        assertThat(response.jsonPath().getString("statusCode"), equalTo("400"));
//        // проверка поля error
//        assertThat(response.jsonPath().getString("error"), equalTo("Bad Request"));
//        // проверка массива message
//        assertThat(response.jsonPath().getList("message"), contains(
//                "email must be longer than or equal to 6 characters",
//                "email must be an email"
//        ));
//    }
//
//    @Test
//    public void testLogin_EmptyPasswordField() {
//        Response response = LoginEndpoint.login("vadim_zviagintsev@mail.ru", "");
//
//        // проверка statusCode
//        assertThat(response.statusCode(), equalTo(400));
//        // проверка поля error
//        assertThat(response.jsonPath().getString("error"), equalTo("Bad Request"));
//        // проверка массива message
//        assertThat(response.jsonPath().getList("message"), contains(
//                "password must be longer than or equal to 6 characters"
//        ));
//    }
//}
