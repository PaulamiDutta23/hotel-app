package com.example.hotelApp.domain.service;

import com.example.hotelApp.domain.model.Booking;
import com.example.hotelApp.domain.model.BookingProjector;
import com.example.hotelApp.domain.repository.BookingRepository;
import com.example.hotelApp.domain.view.BookingView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<BookingView> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map((booking) -> booking.project(BookingView::new)).toList();
    }
}
