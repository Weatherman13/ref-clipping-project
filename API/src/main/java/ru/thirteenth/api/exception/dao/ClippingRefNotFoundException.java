package ru.thirteenth.api.exception.dao;

public class ClippingRefNotFoundException extends RuntimeException{
    public ClippingRefNotFoundException(String message) {
        super(message);
    }

    public ClippingRefNotFoundException(Throwable cause) {
        super(cause);
    }
}
