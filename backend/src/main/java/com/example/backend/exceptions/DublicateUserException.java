package com.example.backend.exceptions;

public class DublicateUserException extends RuntimeException {
    private final String errorCode;
    public DublicateUserException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    public String getErrorCode() {
        return this.errorCode;
    }
}
