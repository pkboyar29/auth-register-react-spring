package com.example.backend.exceptions;

public class DublicateUserException extends RuntimeException {
    public DublicateUserException(String message) {
        super(message);
    }
}
