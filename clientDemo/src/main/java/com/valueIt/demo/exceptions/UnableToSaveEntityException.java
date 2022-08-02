package com.valueIt.demo.exceptions;

public class UnableToSaveEntityException extends RuntimeException{
    public UnableToSaveEntityException(String message) {
        super(message);
    }
}
