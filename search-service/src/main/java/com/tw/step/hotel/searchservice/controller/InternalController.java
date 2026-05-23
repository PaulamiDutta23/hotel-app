package com.tw.step.hotel.searchservice.controller;

import com.tw.step.hotel.searchservice.exception.HotelNotFoundException;
import com.tw.step.hotel.searchservice.exception.InsufficientAvailableRoomsException;
import com.tw.step.hotel.searchservice.service.HotelService;
import com.tw.step.hotel.searchservice.view.BookingRequest;
import com.tw.step.hotel.searchservice.view.HotelView;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/api/hotel/")
public class InternalController {
    private final HotelService hotelService;
    private final Logger logger = LoggerFactory.getLogger("logger");
    public InternalController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookHotel(@RequestBody BookingRequest bookingRequest, HttpServletRequest request) {
        logger.info("{} {}", request.getMethod(), request.getRequestURI());
        HotelView hotelView = null;
        try {
            hotelView = hotelService.updateRoomsQuantity(bookingRequest.hotelId(),bookingRequest.totalRooms());
        } catch (HotelNotFoundException | InsufficientAvailableRoomsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(hotelView);
    }
}
