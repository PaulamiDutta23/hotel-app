package com.example.hotelApp.domain.controller;

import com.example.hotelApp.domain.service.BookingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }
}
