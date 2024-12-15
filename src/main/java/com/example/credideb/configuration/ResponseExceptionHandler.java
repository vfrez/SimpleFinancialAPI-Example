package com.example.credideb.configuration;

import com.example.credideb.configuration.dto.ExceptionDTO;
import com.example.credideb.configuration.exception.ApiErrorException;
import com.example.credideb.configuration.exception.ApiValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler({ApiErrorException.class, ApiValidationException.class})
    public ResponseEntity<ExceptionDTO> methodArgumentNotValidExceptionHandler(Exception e) {
        log.error("Exception error Status 400: {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionDTO> handlerUnexpectedException(Exception e) {
        log.error("Exception error Status 400: {}", e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

}
