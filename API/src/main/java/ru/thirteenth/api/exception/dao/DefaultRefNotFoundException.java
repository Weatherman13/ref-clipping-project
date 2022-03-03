package ru.thirteenth.api.exception.dao;

public class DefaultRefNotFoundException extends RuntimeException{
    public DefaultRefNotFoundException(String message) {
        super(message);
    }

    public DefaultRefNotFoundException(Throwable cause) {
        super(cause);
    }
}
