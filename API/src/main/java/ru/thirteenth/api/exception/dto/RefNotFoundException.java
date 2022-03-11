package ru.thirteenth.api.exception.dto;

public class RefNotFoundException extends RuntimeException{
    public RefNotFoundException(String message) {
        super(message);
    }

    public RefNotFoundException(Throwable cause) {
        super(cause);
    }
}
