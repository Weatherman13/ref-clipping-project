package ru.thirteenth.api.exception.dto;

public class ExpiredLinkException extends RuntimeException{
    public ExpiredLinkException(String message) {
        super(message);
    }

    public ExpiredLinkException(Throwable cause) {
        super(cause);
    }
}
