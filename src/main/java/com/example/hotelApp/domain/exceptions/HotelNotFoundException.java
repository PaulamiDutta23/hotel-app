package com.example.hotelApp.domain.exceptions;

public class HotelNotFoundException extends Throwable {
    public HotelNotFoundException(int hotelId) {
        String template = "Hotel with id %d not found";
        super(String.format(template, hotelId));
    }
}
