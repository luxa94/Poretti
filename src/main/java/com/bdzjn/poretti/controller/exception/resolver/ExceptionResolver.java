package com.bdzjn.poretti.controller.exception.resolver;

import com.bdzjn.poretti.controller.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity notFoundException(HttpServletRequest request, NotFoundException exception) {
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity badRequestException(HttpServletRequest request, BadRequestException exception) {
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity forbiddenException(HttpServletRequest request, ForbiddenException exception) {
        return new ResponseEntity<>(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity authenticationException(HttpServletRequest request, AuthenticationException exception) {
        return new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity methodNotAllowedException(HttpServletRequest request, MethodNotAllowedException exception) {
        return new ResponseEntity<>(exception, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(BadDateException.class)
    public ResponseEntity badDateException(HttpServletRequest request, BadDateException exception) {
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidRangeException.class)
    public ResponseEntity invalidRangeException(HttpServletRequest request, InvalidRangeException exception) {
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity unprocessableEntityException(HttpServletRequest request, UnprocessableEntityException exception) {
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}