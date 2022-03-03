package ru.thirteenth.ref_clipping_service.exception.dao;

public class DefaultRefNotFoundException extends RuntimeException{
    public DefaultRefNotFoundException(String message) {
        super(message);
    }

    public DefaultRefNotFoundException(Throwable cause) {
        super(cause);
    }
}
