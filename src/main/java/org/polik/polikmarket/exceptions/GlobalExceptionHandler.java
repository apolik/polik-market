package org.polik.polikmarket.exceptions;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;

/**
 * Created by Polik on 6/10/2022
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String EXCEPTION_INVALID_TYPE = "Invalid type";
    public static final String VALIDATION_MESSAGE = "Validation Failed";
    public static final String NOT_FOUND_MESSAGE = "Item not found";
    public static final String INTERNAL_ERROR_MESSAGE = "Internal error";

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Error> handleValidationException() {
        return createResponseEntity(HttpStatus.BAD_REQUEST, VALIDATION_MESSAGE);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Error> handleNotFound() {
        return createResponseEntity(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, VALIDATION_MESSAGE);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, VALIDATION_MESSAGE);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, VALIDATION_MESSAGE);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, VALIDATION_MESSAGE);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Internal Exception", ex);
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    private <T> ResponseEntity<T> createResponseEntity(HttpStatus status, String message) {
        return (ResponseEntity<T>) ResponseEntity.status(status).body(new Error(status.value(), message));
    }
}
