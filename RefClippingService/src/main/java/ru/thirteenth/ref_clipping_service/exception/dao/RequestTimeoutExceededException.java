package ru.thirteenth.ref_clipping_service.exception.dao;

public class RequestTimeoutExceededException extends RuntimeException{
    public RequestTimeoutExceededException(String message) {
        super(message);
    }

    public RequestTimeoutExceededException(Throwable cause) {
        super(cause);
    }
}
