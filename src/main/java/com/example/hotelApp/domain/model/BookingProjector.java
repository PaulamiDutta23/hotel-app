package com.example.hotelApp.domain.model;

import java.util.List;

@FunctionalInterface
public interface BookingProjector<T> {
    T project(int id, String username, String hotelName, int totalRooms, double totalPrice);
}

