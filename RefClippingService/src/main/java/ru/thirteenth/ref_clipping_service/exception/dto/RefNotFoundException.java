package ru.thirteenth.ref_clipping_service.exception.dto;

public class RefNotFoundException extends RuntimeException{
    public RefNotFoundException(String message) {
        super(message);
    }

    public RefNotFoundException(Throwable cause) {
        super(cause);
    }
}
