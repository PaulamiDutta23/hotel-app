package com.example.hotelApp.domain.controller;

import com.example.hotelApp.domain.service.BookingService;
import com.example.hotelApp.domain.view.BookingView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        System.out.println("inside controller");
        List<BookingView> allBookings = bookingService.getAllBookings();
        return ResponseEntity.ok(allBookings);
    }
}
