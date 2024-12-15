package com.example.credideb.configuration.exception;

public class ApiValidationException extends RuntimeException {

    public ApiValidationException(String errorMessage) {
        super(errorMessage);
    }
}
