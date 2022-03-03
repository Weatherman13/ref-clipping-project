package ru.thirteenth.api.exception.dao;

public class BlankException extends RuntimeException{
    public BlankException(String message) {
        super(message);
    }

    public BlankException(Throwable cause) {
        super(cause);
    }
}
