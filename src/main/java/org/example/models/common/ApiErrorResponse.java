package org.example.models.common;

/**
 * Общая форма ошибок API:
 * ExceptionBadRequest, ExceptionUnauthorized,
 * ExceptionNotFounded, ExceptionServerInternal
 */
public class ApiErrorResponse {

    private int statusCode;
    private String message;

    public ApiErrorResponse() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
