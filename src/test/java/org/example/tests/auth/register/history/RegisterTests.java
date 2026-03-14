//package org.example.tests.auth.register.history;
//
//import org.example.config.Credentials;
//import org.testng.annotations.Test;
//import io.restassured.response.Response;
//
//import org.example.base.BaseTest;
//import org.example.endpoints.register.history.RegisterEndpoint;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//
//public class RegisterTests extends BaseTest {
//
//    @Test
//    public void testRegister_Success() {
//        Response response = RegisterEndpoint.register("vadim_zviagintsev_110@mail.ru", "Qwert123");
//
//        assertThat(response.statusCode(), equalTo(201));
//        // проверка поля accessToken
//        assertThat(response.jsonPath().getString("accessToken"), notNullValue());
//        // проверка поля refreshToken
//        assertThat(response.jsonPath().getString("refreshToken"), notNullValue());
//        // проверка поля user.email
//        assertThat(response.jsonPath().getString("user.email"), equalTo("vadim_zviagintsev_110@mail.ru"));
//        // проверка, что id является int
//        Object id = response.jsonPath().get("user.id");
//        assertThat(id, instanceOf(Integer.class));
//    }
//
//    @Test
//    public void testRegister_OccupiedEmail() {
//        Response response = RegisterEndpoint.register(Credentials.EMAIL, Credentials.PASSWORD);
//
//        assertThat(response.statusCode(), equalTo(400));
//        assertThat(response.jsonPath().getString("message"), equalTo("Bad Request"));
//    }
//
//    @Test
//    public void testRegister_EmptyEmailField() {
//        Response response = RegisterEndpoint.register("", "Qwerty123");
//
//        // проверка statusCode
//        assertThat(response.statusCode(), equalTo(400));
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
//    public void testRegister_EmptyPasswordField() {
//        Response response = RegisterEndpoint.register("vadim_zviagintsev600@mail.ru", "");
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
