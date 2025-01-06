package com.nhnacademy.exam.hotel.advice;

import com.nhnacademy.exam.hotel.dto.ErrorDto;
import com.nhnacademy.exam.hotel.exception.DataAlreadyExistsException;
import com.nhnacademy.exam.hotel.exception.DataNotFoundException;
import com.nhnacademy.exam.hotel.exception.InvalidAccessException;
import com.nhnacademy.exam.hotel.exception.WrongDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({
            DataAlreadyExistsException.class,
    })
    public ResponseEntity<?> handleAlreadyExistException(Exception e) {
        String errorMessage = e.getMessage() != null ? e.getMessage() : "An error occurred";
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDto(errorMessage));
    }

    @ExceptionHandler({
            NullPointerException.class,
            DataNotFoundException.class
    })
    public ResponseEntity<?> handleNotFoundException(Exception e) {
        String errorMessage = e.getMessage() != null ? e.getMessage() : "An error occurred";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(errorMessage));
    }

    @ExceptionHandler({
            WrongDataException.class
    })
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        String errorMessage = e.getMessage() != null ? e.getMessage() : "An error occurred";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(errorMessage));
    }

    @ExceptionHandler({
            InvalidAccessException.class
    })
    public ResponseEntity<?> handleFrobiddenException(Exception e) {
        String errorMessage = e.getMessage() != null ? e.getMessage() : "An error occurred";
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(errorMessage));
    }

    @ExceptionHandler({
            RuntimeException.class
    })
    public ResponseEntity<?> handleInternalErrorException(Exception e) {
        String errorMessage = e.getMessage() != null ? e.getMessage() : "An error occurred";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto(errorMessage));
    }

}
