package com.example.hotelApp.domain.exception;

public class InvalidCredentialsException extends Throwable {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
