package com.example.hotelApp.domain.model;

@FunctionalInterface
public interface HotelProjector<T> {
    T project(int id, String name, String city, int availableRooms, double pricePerDay);
}