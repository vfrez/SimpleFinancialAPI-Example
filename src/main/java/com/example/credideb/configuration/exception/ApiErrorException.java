package com.example.credideb.configuration.exception;

public class ApiErrorException extends RuntimeException {

    public ApiErrorException(String errorMessage) {
        super(errorMessage);
    }
}
