package ru.spb.somebet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.spb.somebet.exception.NotEnoughMoney;
import ru.spb.somebet.exception.UserNotFoundException;

@ControllerAdvice
public class SimpleControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotEnoughMoney.class)
    public ResponseEntity<String> handleException(NotEnoughMoney e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleException(UserNotFoundException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
