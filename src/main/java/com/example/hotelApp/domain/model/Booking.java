package com.example.hotelApp.domain.model;

import com.example.hotelApp.domain.view.BookingView;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
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

    @Override
    public String toString() {
        return "Booking Receipt" +
                "id=" + id + "\n"+
                " totalPrice=" + totalPrice +  "\n"+
                " totalRooms=" + totalRooms + "\n"+
                " hotelName='" + hotelName + "\n" +
                " username='" + username + "\n";
    }
}
