package com.nhnacademy.exam.hotel.advice;

import com.nhnacademy.exam.hotel.exception.DataAlreadyExistsException;
import com.nhnacademy.exam.hotel.exception.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({
            IllegalArgumentException.class,
            DataAlreadyExistsException.class,
    })
    public ResponseEntity<?> handleAlreadyExistException(Exception e) {
        String errorMessage = e.getMessage() != null ? e.getMessage() : "An error occurred";
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler({
            NullPointerException.class,
            DataNotFoundException.class
    })
    public ResponseEntity<?> handleNotFoundException(Exception e) {
        String errorMessage = e.getMessage() != null ? e.getMessage() : "An error occurred";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

}
