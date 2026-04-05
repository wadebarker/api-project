# Todo application API (OpenAPI 3.0)

Базовый URL по умолчанию: `http://2.59.41.2:7320` (см. `ApiConfig.BASE_URI`).

Аутентификация: **HTTP Bearer JWT** (`Authorization: Bearer <accessToken>`), схема `bearer` в Swagger.

Исключение из описания ниже: **POST /api/files/upload** (загрузка аватара) — по заданию не используется в автотестах.

---

## Auth

| Метод | Путь | Описание | Тело запроса | Успех | Security |
|-------|------|----------|--------------|-------|----------|
| POST | `/api/auth/login` | Вход | `SignInDto` (email, password) | 200 `AuthDto` | нет |
| POST | `/api/auth/register` | Регистрация | `SignInDto` | 201 `AuthDto` | нет |
| GET | `/api/auth/logout` | Выход | — | 200 | Bearer |
| GET | `/api/auth/refresh` | Обновление токена | — | 200 `AuthDto` | нет* |
| PATCH | `/api/auth/update-email` | Смена email | `SignInDto` | 200 `UserDto` | Bearer |
| PATCH | `/api/auth/update-pass` | Смена пароля | `PassDto` (password, newPassword) | 200 `UserDto` | Bearer |

\* В спецификации для `refresh` не указан `security`. На развёрнутом инстансе возможны **200** (новая пара токенов) или **401**, если refresh ожидается в httpOnly-cookie после логина в браузере. Интеграционный тест учитывает оба исхода.

Ошибки: **400** `ExceptionBadRequest`, **401** `ExceptionUnauthorized`, **500** `ExceptionServerInternal` (где указано в OpenAPI).

---

## Profile

| Метод | Путь | Описание | Тело / параметры | Успех | Security |
|-------|------|----------|------------------|-------|----------|
| POST | `/api/profile/save` | Сохранить профиль | `ProfileDto` | 200 `ResProfileDto` | Bearer |
| GET | `/api/profile/get` | Получить профиль | — | 200 `ResProfileDto` | Bearer |
| GET | `/api/profile/image` | Получить аватар | — | 200 (бинарное тело) | Bearer |

Дополнительно: **404** `ExceptionNotFounded` для `GET /api/profile/get`.

---

## Todos

| Метод | Путь | Описание | Тело / параметры | Успех | Security |
|-------|------|----------|------------------|-------|----------|
| POST | `/api/todos/create` | Создать задачу | `TodoDto` | 201 `ResTodoDto` | Bearer |
| PATCH | `/api/todos/edit/{id}` | Изменить задачу | path `id`, body `TodoDto` | 200 `ResTodoDto` | Bearer |
| DELETE | `/api/todos/delete/{id}` | Удалить задачу | path `id` | 204 без тела | Bearer |
| GET | `/api/todos` | Список задач | — | 200 `ResTodoDto[]` | Bearer |
| GET | `/api/todos/search` | Поиск по заголовку | query `title` (required) | 200 `ResTodoDto[]` | Bearer |
| GET | `/api/todos/{id}` | Задача по id | path `id` | 200 `ResTodoDto` | Bearer |

Ошибки: **404** `ExceptionNotFounded` для get/edit/delete по несуществующему id.

---

## Модели (components.schemas)

| Схема | Поля |
|-------|------|
| **SignInDto** | email (string, required), password (string, required) |
| **AuthDto** | accessToken, refreshToken, user (`UserDto`) |
| **UserDto** | id (number), email (string) |
| **PassDto** | password (текущий), newPassword |
| **ProfileDto** | name, dateOfBirth, surname, patronymic, sex, phone (все required) |
| **ResProfileDto** | поля `ProfileDto` + id, avatar, userId |
| **TodoDto** | title, description, date, time, checked |
| **ResTodoDto** | поля `TodoDto` + id, userId |
| **ExceptionBadRequest** | statusCode, message |
| **ExceptionUnauthorized** | statusCode, message |
| **ExceptionNotFounded** | statusCode, message |
| **ExceptionServerInternal** | statusCode, message |
| **FileUploadDto** | image (binary) — multipart, не покрывается тестами |

Полная машиночитаемая схема: `spec/openapi-todo-application.json`.
