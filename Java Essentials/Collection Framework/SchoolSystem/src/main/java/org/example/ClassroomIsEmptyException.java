package org.example;

public class ClassroomIsEmptyException extends RuntimeException {
    public ClassroomIsEmptyException(String message) {
        super(message);
    }
}
