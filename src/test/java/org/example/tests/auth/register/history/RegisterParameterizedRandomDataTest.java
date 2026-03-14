//package org.example.tests.auth.register.history;
//
//import net.datafaker.Faker;
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
//public class RegisterParameterizedRandomDataTest extends BaseTest {
//
//    private static final Faker faker = new Faker();
//
//    @DataProvider(name = "registerCases")
//    public Object[][] registerCases() {
//
//        // Генерация случайного email и пароля (простая строка)
//        String randomEmail = faker.internet().emailAddress();
//        String randomPassword = faker.lorem()
//                .characters(faker.number().numberBetween(8, 14), true, true);
//        // true, true → буквы + цифры
//
//        return new Object[][]{
//
//                // Успешная регистрация с динамическими данными
//                {
//                        "Успешная регистрация (random email/password)",
//                        randomEmail,
//                        randomPassword,
//                        201,
//                        new String[]{
//                                "accessToken:notNull",
//                                "refreshToken:notNull",
//                                "user.email:DYNAMIC", // сверка с email из теста
//                                "user.id:isInt"
//                        }
//                },
//
//                // Почта уже занята
//                {
//                        "Почта уже занята",
//                        Credentials.EMAIL,
//                        Credentials.PASSWORD,
//                        400,
//                        new String[]{"message:Bad Request"}
//                },
//
//                // Пустой email
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
//                // Пустой пароль
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
//    @Test(dataProvider = "registerCases")
//    public void registerParameterizedTest(
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
//                case "DYNAMIC":
//                    // Проверяем, что вернулся именно email, который был сгенерирован
//                    assertThat("Проверка поля user.email", String.valueOf(value), equalTo(email));
//                    break;
//
//                default:
//                    if (expected.startsWith("[")) {
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
