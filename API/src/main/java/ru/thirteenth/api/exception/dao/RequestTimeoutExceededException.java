package ru.thirteenth.api.exception.dao;

public class RequestTimeoutExceededException extends RuntimeException{
    public RequestTimeoutExceededException(String message) {
        super(message);
    }

    public RequestTimeoutExceededException(Throwable cause) {
        super(cause);
    }
}
