package com.valueIt.demo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApiExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException exception) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "no entity found", exception);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(UnableToSaveEntityException.class)
    public ResponseEntity<ApiError> handleEntityNotSaved(UnableToSaveEntityException exception) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "could not save the entity", exception);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(IllegalPageRequestArgumentsException.class)
    public ResponseEntity<ApiError> handleIllegalPageRequestParameters(IllegalPageRequestArgumentsException exception) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "unable to get result due to page request arguments", exception);
        log.error(apiError.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "value mismatch for the parameter", exception);
        log.error(apiError.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}