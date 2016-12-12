package com.bdzjn.poretti.controller.exception;

public class UnprocessableException extends RuntimeException {

    public UnprocessableException() {
    }

    public UnprocessableException(String message) {
        super(message);
    }
}
