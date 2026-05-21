package com.example.hotelApp.domain.model;

import com.example.hotelApp.domain.view.BookingView;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bookings")
public class Booking {
    @Id
    private final int id;
    private final double totalPrice;
    private final int totalRooms;
    private final String hotelName;
    private final String username;

    public Booking(int id, String username, String hotelName, int totalRooms, double totalPrice) {
        this.totalPrice = totalPrice;
        this.totalRooms = totalRooms;
        this.hotelName = hotelName;
        this.username = username;
        this.id = id;
    }

    public BookingView project(BookingProjector<BookingView> bookingProjector) {
        return bookingProjector.project(id, username, hotelName, totalRooms,  totalPrice);
    }
}
