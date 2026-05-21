package com.example.hotelApp.domain.exception;

public class InsufficientAvailableRoomsException extends Throwable {
    public InsufficientAvailableRoomsException(String hotelName, int requestRooms) {
        String template = "%d rooms are not Available in hotel %s";
        super(String.format(template, requestRooms, hotelName));
    }
}
