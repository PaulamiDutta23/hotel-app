package com.tw.step.hotel.bookingservice.exception;

public class BookingNotFoundException extends Throwable {
    public BookingNotFoundException(int bookingId) {
        super(String.format("Booking with id %d not found",bookingId));
    }
}
