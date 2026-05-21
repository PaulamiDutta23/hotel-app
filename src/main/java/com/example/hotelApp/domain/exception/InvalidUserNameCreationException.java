package com.example.hotelApp.domain.exception;

public class InvalidUserNameCreationException extends Throwable {
    public InvalidUserNameCreationException(String message) {
        super(message);
    }
}
