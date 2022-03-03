package ru.thirteenth.api.exception.dao;

public class ExpiredLinkException extends RuntimeException{
    public ExpiredLinkException(String message) {
        super(message);
    }

    public ExpiredLinkException(Throwable cause) {
        super(cause);
    }
}
