package com.example.hotelApp.domain.service;

import com.example.hotelApp.domain.exceptions.HotelNotFoundException;
import com.example.hotelApp.domain.exceptions.InsufficientAvailableRoomsException;
import com.example.hotelApp.domain.model.Booking;
import com.example.hotelApp.domain.model.Hotel;
import com.example.hotelApp.domain.repository.BookingRepository;
import com.example.hotelApp.domain.repository.HotelRepository;
import com.example.hotelApp.domain.view.BookingRequest;
import com.example.hotelApp.domain.view.BookingView;
import com.example.hotelApp.domain.view.HotelView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    private int currentBookingId;
    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;

    public BookingService(BookingRepository bookingRepository, HotelRepository hotelRepository) {
        this.bookingRepository = bookingRepository;
        this.hotelRepository = hotelRepository;
        this.currentBookingId = 0;
    }

    public List<BookingView> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map((booking) -> booking.project(BookingView::new)).toList();
    }

    public BookingView bookHotel(String username, BookingRequest bookingRequest) throws HotelNotFoundException, InsufficientAvailableRoomsException {
        Hotel hotel = hotelRepository.findHotelById(bookingRequest.hotelId());

        if (hotel == null) throw new HotelNotFoundException(bookingRequest.hotelId());
        HotelView hotelView = hotel.project(HotelView::new);

        if(!hotel.isRequestedRoomsAvailable(bookingRequest.totalRooms())) throw new InsufficientAvailableRoomsException(hotelView.name(),bookingRequest.hotelId());
        Hotel updatedHotel = hotel.bookRooms(bookingRequest.totalRooms());
        HotelView updatedHotelView = updatedHotel.project(HotelView::new);
        hotelRepository.save(updatedHotel);
        double totalPrice = updatedHotel.calculatePrice(bookingRequest.totalRooms());
        Booking booking = new Booking(++currentBookingId, username, hotelView.name(), updatedHotelView.availableRooms(), totalPrice);
        bookingRepository.save(booking);
        return booking.project(BookingView::new);
    }
}
