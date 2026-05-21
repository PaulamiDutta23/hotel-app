package com.example.hotelApp.domain.exceptions;

public class BookingNotFoundException extends Throwable {
    public BookingNotFoundException(int bookingId) {
        super(String.format("Booking not found with id %d",bookingId));
    }
}
