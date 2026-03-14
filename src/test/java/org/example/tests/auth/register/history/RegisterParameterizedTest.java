//package org.example.tests.auth.register.history;
//
//import org.example.config.Credentials;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//import io.restassured.response.Response;
//
//import org.example.base.BaseTest;
//import org.example.endpoints.register.history.RegisterEndpoint;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//
//public class RegisterParameterizedTest extends BaseTest {
//
//    @DataProvider(name = "registerCases")
//    public Object[][] registerCases() {
//        return new Object[][]{
//
//                {
//                        "Успешная регистрация",
//                        "vadim_zviagintsev_112@mail.ru",
//                        "Qwert123",
//                        201,
//                        new String[]{
//                                "accessToken:notNull",
//                                "refreshToken:notNull",
//                                "user.email:vadim_zviagintsev_112@mail.ru",
//                                "user.id:isInt"
//                        }
//                },
//
//                {
//                        "Почта уже занята",
//                        Credentials.EMAIL,
//                        Credentials.PASSWORD,
//                        400,
//                        new String[]{"message:Bad Request"}
//                },
//
//                {
//                        "Пустой email",
//                        "",
//                        "Qwerty123",
//                        400,
//                        new String[]{
//                                "error:Bad Request",
//                                "message:[email must be longer than or equal to 6 characters,email must be an email]"
//                        }
//                },
//
//                {
//                        "Пустой пароль",
//                        "vadim_zviagintsev@mail.ru",
//                        "",
//                        400,
//                        new String[]{
//                                "error:Bad Request",
//                                "message:[password must be longer than or equal to 6 characters]"
//                        }
//                }
//        };
//    }
//
//
//    @Test(dataProvider = "registerCases")
//    public void registerParameterizedTest(
//            String testName,
//            String email,
//            String password,
//            int expectedStatus,
//            String[] checks
//    ) {
//        System.out.println("Тест: " + testName);
//
//        Response response = RegisterEndpoint.register(email, password);
//
//        // Проверка status code
//        assertThat(response.statusCode(), equalTo(expectedStatus));
//
//        // Универсальные проверки
//        for (String check : checks) {
//            String[] parts = check.split(":", 2);
//            String field = parts[0];
//            String expected = parts[1];
//
//            Object value = response.jsonPath().get(field);
//
//            switch (expected) {
//                case "notNull":
//                    assertThat("Проверка поля: " + field, value, notNullValue());
//                    break;
//
//                case "isInt":
//                    assertThat("user.id должен быть Integer", value, instanceOf(Integer.class));
//                    break;
//
//                default:
//                    if (expected.startsWith("[")) {
//                        // сравнение списков
//                        String trimmed = expected.replace("[", "").replace("]", "");
//                        String[] expectedArr = trimmed.split(",");
//                        assertThat(response.jsonPath().getList(field), contains(expectedArr));
//                    } else {
//                        assertThat("Проверка поля: " + field, String.valueOf(value), equalTo(expected));
//                    }
//                    break;
//            }
//        }
//    }
//}
