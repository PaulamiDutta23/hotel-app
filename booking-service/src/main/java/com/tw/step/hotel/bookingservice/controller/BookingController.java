package com.tw.step.hotel.bookingservice.controller;

import com.tw.step.hotel.bookingservice.exception.BookingNotFoundException;
import com.tw.step.hotel.bookingservice.exception.HotelNotFoundException;
import com.tw.step.hotel.bookingservice.exception.InsufficientAvailableRoomsException;
import com.tw.step.hotel.bookingservice.service.BookingService;
import com.tw.step.hotel.bookingservice.view.BookingRequest;
import com.tw.step.hotel.bookingservice.view.BookingView;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final Logger logger = LoggerFactory.getLogger(BookingController.class);
    private final BookingService bookingService;

    BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping()
    public ResponseEntity<List<BookingView>> getBookings(HttpServletRequest req) {
        logger.info("{} {}", req.getMethod(), req.getRequestURI());

        List<BookingView> allBookings = bookingService.getAllBookings();
        return ResponseEntity.ok(allBookings);
    }

    @PostMapping()
    public ResponseEntity<BookingView> book(@RequestBody BookingRequest bookingRequest, HttpServletRequest req, Authentication authentication){
        logger.info("{} {}", req.getMethod(), req.getRequestURI());
        BookingView booking = null;
        try {
            booking = bookingService.bookHotel(authentication.getName(),bookingRequest);
        } catch (HotelNotFoundException | InsufficientAvailableRoomsException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(booking);
    }

    @GetMapping("{bookingId}/receipt.pdf")
    public ResponseEntity<?> downloadReceipt(@PathVariable String bookingId) throws Exception {

        byte[] bytes = null;
        try {
            bytes = bookingService.generateReceipt(Integer.parseInt(bookingId));
        } catch (BookingNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=receipt.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    }
}
