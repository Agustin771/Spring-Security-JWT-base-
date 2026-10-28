package com.agustinperez.proyectoSecurity.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(
            BadCredentialsException exception) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Email o contraseña incorrectos",
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException
            (IllegalArgumentException exception)
    {
        log.warn("Illegal argument detected | status={} | message={}",
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception
    ){
        String message = exception.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        log.warn("Validation failed | status={} | field={} | message={}",
                HttpStatus.BAD_REQUEST.value(),
                exception.getBindingResult().getFieldError().getField(),
                message);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                message,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(
            ResourceNotFoundException exception
    ){

        log.warn("Resource not found | status={} | message={}",
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);

    }
}