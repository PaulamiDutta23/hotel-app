package com.tw.step.hotel.bookingservice.exception;

public class InsufficientAvailableRoomsException extends Throwable {
    public InsufficientAvailableRoomsException(String hotelId, int requestRooms) {
        String template = "%d rooms are not Available in hotel with id %s";
        super(String.format(template, requestRooms, hotelId));
    }
}
