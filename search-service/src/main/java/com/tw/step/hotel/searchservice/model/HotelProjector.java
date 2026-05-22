package com.tw.step.hotel.searchservice.model;

@FunctionalInterface
public interface HotelProjector<T> {
    T project(String id, String name, String city, int availableRooms,double pricePerDay);
}
