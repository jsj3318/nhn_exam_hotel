package com.nhnacademy.exam.hotel.exception;

public class InvalidAccessException extends RuntimeException {
    public InvalidAccessException(String message) {
        super(message);
    }
}
