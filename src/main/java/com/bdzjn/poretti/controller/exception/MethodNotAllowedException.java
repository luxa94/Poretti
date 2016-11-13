package com.bdzjn.poretti.controller.exception;

public class MethodNotAllowedException extends RuntimeException {

    public MethodNotAllowedException() {
    }

    public MethodNotAllowedException(String message) {
        super(message);
    }
}
