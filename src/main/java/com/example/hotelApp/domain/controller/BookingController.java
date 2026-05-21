package com.example.hotelApp.domain.controller;

import com.example.hotelApp.domain.exceptions.HotelNotFoundException;
import com.example.hotelApp.domain.exceptions.InsufficientAvailableRoomsException;
import com.example.hotelApp.domain.service.BookingService;
import com.example.hotelApp.domain.view.BookingRequest;
import com.example.hotelApp.domain.view.BookingView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @GetMapping()
    public ResponseEntity<List<BookingView>> getBookings(){
        List<BookingView> allBookings = bookingService.getAllBookings();
        return ResponseEntity.ok(allBookings);
    }

    @PostMapping()
    public ResponseEntity<BookingView> book(@RequestBody BookingRequest bookingRequest){
        System.out.println("booking");
        BookingView booking = null;
        try {
            booking = bookingService.bookHotel("abc",bookingRequest);
        } catch (HotelNotFoundException | InsufficientAvailableRoomsException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(booking);
    }
}
