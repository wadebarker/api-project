//package org.example.tests.auth.login.history;
//
//import net.datafaker.Faker;
//import org.example.config.Credentials;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//import io.restassured.response.Response;
//
//import org.example.base.BaseTest;
//import org.example.endpoints.login.history.LoginEndpoint;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//
//public class LoginParameterizedRandomDataTest extends BaseTest {
//
//    private static final Faker faker = new Faker();
//
//    @DataProvider(name = "loginCases")
//    public Object[][] loginCases() {
//
//        // Генерация случайного email и пароля для негативного кейса
//        String randomEmail = faker.internet().emailAddress();
//        String randomPassword = faker.lorem()
//                .characters(faker.number().numberBetween(8, 14), true, true);
//
//        return new Object[][]{
//
//                // Успешный логин — используем реальные credentials
//                {
//                        "Успешный логин",
//                        Credentials.EMAIL,
//                        Credentials.PASSWORD,
//                        200,
//                        new String[]{
//                                "accessToken:notNull",
//                                "refreshToken:notNull",
//                                "user.id:18460",
//                                "user.email:vadim_zviagintsev@mail.ru"
//                        }
//                },
//
//                // Неверный пароль
//                {
//                        "Неверный пароль",
//                        Credentials.EMAIL,
//                        randomPassword,
//                        401,
//                        new String[]{
//                                "statusCode:401",
//                                "message:Unauthorized"
//                        }
//                },
//
//                // Несуществующий email — используем случайный email
//                {
//                        "Несуществующий email",
//                        randomEmail,
//                        Credentials.PASSWORD,
//                        401,
//                        new String[]{
//                                "statusCode:401",
//                                "message:Unauthorized"
//                        }
//                },
//
//                // Пустой email
//                {
//                        "Пустой email",
//                        "",
//                        randomPassword,
//                        400,
//                        new String[]{
//                                "statusCode:400",
//                                "error:Bad Request",
//                                "message:[email must be longer than or equal to 6 characters,email must be an email]"
//                        }
//                },
//
//                // Пустой пароль
//                {
//                        "Пустой пароль",
//                        randomPassword,
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
//    @Test(dataProvider = "loginCases")
//    public void loginParameterizedTest(
//            String testName,
//            String email,
//            String password,
//            int expectedStatus,
//            String[] checks
//    ) {
//        System.out.println("Тест: " + testName);
//        System.out.println("Email: " + email);
//        System.out.println("Password: " + password);
//
//        Response response = LoginEndpoint.login(email, password);
//
//        // Проверка статус кода
//        assertThat(response.statusCode(), equalTo(expectedStatus));
//
//        // Проверка всех остальных ожиданий
//        for (String check : checks) {
//            String[] parts = check.split(":", 2);
//            String field = parts[0];
//            String expected = parts[1];
//
//            Object value = response.jsonPath().get(field);
//
//            if ("notNull".equals(expected)) {
//                assertThat("Проверка поля: " + field, value, notNullValue());
//            } else if (expected.startsWith("[")) {
//                String trimmed = expected.replace("[", "").replace("]", "");
//                String[] expectedArr = trimmed.split(",");
//
//                assertThat(response.jsonPath().getList(field), contains(expectedArr));
//            } else {
//                assertThat("Проверка поля: " + field, String.valueOf(value), equalTo(expected));
//            }
//        }
//    }
//}
//
