package com.tw.step.hotel.searchservice.model;

import com.tw.step.hotel.searchservice.view.HotelView;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "hotels")
public class  Hotel {
    @Id
    private final String id;
    private final String name;
    private final String city;
    private final double pricePerDay;
    private final int availableRooms;

    public Hotel(String id, String name, String city, int availableRooms,double pricePerDay) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.pricePerDay = pricePerDay;
        this.availableRooms = availableRooms;
    }

    public boolean isRequestedRoomsAvailable(int requestedRooms) {
        return availableRooms >= requestedRooms;
    }


    public Hotel bookRooms(int rooms) {
        return new Hotel(id,name,city,availableRooms-rooms,pricePerDay);
    }

    public double calculatePrice(int rooms) {
        return pricePerDay * rooms;
    }

    public HotelView project(HotelProjector<HotelView> hotelView) {
        return hotelView.project( id,  name,  city,  availableRooms, pricePerDay);
    }
}
