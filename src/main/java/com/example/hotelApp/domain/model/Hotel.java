package com.example.hotelApp.domain.model;

import com.example.hotelApp.domain.view.HotelView;

public class Hotel {
    private final int id;
    private final String name;
    private final String city;
    private final double pricePerDay;
    private final int availableRooms;

    public Hotel(int id, String name, String city, int availableRooms,double pricePerDay) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.pricePerDay = pricePerDay;
        this.availableRooms = availableRooms;
    }

    public boolean isRequestedRoomsAvailable(int requestedRooms) {
        return availableRooms >= requestedRooms;
    }

    public HotelView project(HotelProjector<HotelView> hotelProjector) {
        return hotelProjector.project(id,name,city,availableRooms,pricePerDay);
    }

    public Hotel bookRooms(int rooms) {
        return new Hotel(id,name,city,availableRooms-rooms,pricePerDay);
    }

    public double calculatePrice(int rooms) {
        return pricePerDay * rooms;
    }
}
