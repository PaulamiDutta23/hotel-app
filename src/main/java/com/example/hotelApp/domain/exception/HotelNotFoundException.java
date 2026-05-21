package com.example.hotelApp.domain.exception;

public class HotelNotFoundException extends Throwable {
    public HotelNotFoundException(String hotelId) {
        String template = "Hotel with id %s not found";
        super(String.format(template, hotelId));
    }
}
