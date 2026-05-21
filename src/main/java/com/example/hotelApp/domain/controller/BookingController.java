package com.example.hotelApp.domain.controller;

import com.example.hotelApp.domain.exceptions.HotelNotFoundException;
import com.example.hotelApp.domain.exceptions.InsufficientAvailableRoomsException;
import com.example.hotelApp.domain.service.BookingService;
import com.example.hotelApp.domain.view.BookingRequest;
import com.example.hotelApp.domain.view.BookingView;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final Logger logger = LoggerFactory.getLogger(BookingController.class);
    private final BookingService bookingService;

    BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @GetMapping()
    public ResponseEntity<List<BookingView>> getBookings(HttpServletRequest req){
        logger.info("{} {}", req.getMethod(), req.getRequestURI());

        List<BookingView> allBookings = bookingService.getAllBookings();
        return ResponseEntity.ok(allBookings);
    }

    @PostMapping()
    public ResponseEntity<BookingView> book(@RequestBody BookingRequest bookingRequest, HttpServletRequest req){
        logger.info("{} {}", req.getMethod(), req.getRequestURI());
        BookingView booking = null;
        try {
            booking = bookingService.bookHotel("abc",bookingRequest);
        } catch (HotelNotFoundException | InsufficientAvailableRoomsException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(booking);
    }
}
