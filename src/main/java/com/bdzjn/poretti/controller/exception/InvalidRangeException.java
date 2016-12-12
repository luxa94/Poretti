package com.bdzjn.poretti.controller.exception;

public class InvalidRangeException extends RuntimeException {

    public InvalidRangeException() {
    }

    public InvalidRangeException(String message) {
        super(message);
    }
}
