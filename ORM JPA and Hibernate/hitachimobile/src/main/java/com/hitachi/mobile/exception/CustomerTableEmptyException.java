package com.hitachi.mobile.exception;

public class CustomerTableEmptyException extends RuntimeException {
    public CustomerTableEmptyException(String message) {
        super(message);
    }
}
