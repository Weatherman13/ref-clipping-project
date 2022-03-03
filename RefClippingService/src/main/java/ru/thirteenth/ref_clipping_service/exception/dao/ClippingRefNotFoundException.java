package ru.thirteenth.ref_clipping_service.exception.dao;

public class ClippingRefNotFoundException extends RuntimeException{
    public ClippingRefNotFoundException(String message) {
        super(message);
    }

    public ClippingRefNotFoundException(Throwable cause) {
        super(cause);
    }
}
