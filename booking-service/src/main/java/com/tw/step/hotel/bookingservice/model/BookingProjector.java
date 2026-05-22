package com.tw.step.hotel.bookingservice.model;

@FunctionalInterface
public interface BookingProjector<T> {
    T project(int id, String username, String hotelName, int totalRooms, double totalPrice);
}

