package com.hitachi.mobile.exception;

public class SIMDoesNotExistException extends RuntimeException {
    public SIMDoesNotExistException(String message) {
        super(message);
    }
}
