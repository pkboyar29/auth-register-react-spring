package com.example.backend.exceptions;

public class AuthenticationFailedException extends RuntimeException {
    private final String errorCode;
    public AuthenticationFailedException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    public String getErrorCode() {
        return this.errorCode;
    }
}
