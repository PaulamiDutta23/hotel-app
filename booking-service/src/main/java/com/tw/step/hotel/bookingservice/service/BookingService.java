package com.tw.step.hotel.bookingservice.service;

import com.tw.step.hotel.bookingservice.model.Booking;
import com.tw.step.hotel.bookingservice.repository.BookingRepository;
import com.tw.step.hotel.bookingservice.view.BookingRequest;
import com.tw.step.hotel.bookingservice.view.BookingView;
import com.tw.step.hotel.bookingservice.view.HotelView;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.naming.InsufficientResourcesException;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final SearchServiceClient searchServiceClient;
    private final ReceiptJobProducer receiptJobProducer;
    private int currentBookingId;

    public BookingService(BookingRepository bookingRepository, SearchServiceClient searchServiceClient, ReceiptJobProducer receiptJobProducer) {
        this.bookingRepository = bookingRepository;
        this.searchServiceClient = searchServiceClient;
        this.receiptJobProducer = receiptJobProducer;
        this.currentBookingId = 0;
    }

    public List<BookingView> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map((booking) -> booking.project(BookingView::new)).toList();
    }

    public BookingView bookHotel(String username, BookingRequest bookingRequest) throws InsufficientResourcesException {
        ResponseEntity<HotelView> response =
                searchServiceClient.bookHotel(bookingRequest);

        HotelView hotelView = response.getBody();

        assert hotelView != null;
        double totalPrice = calculatePrice(hotelView.pricePerDay(),bookingRequest.totalRooms());
        Booking booking = new Booking(++currentBookingId, username, hotelView.name(), bookingRequest.totalRooms(), totalPrice);
        bookingRepository.save(booking);
        BookingView bookingView = booking.project(BookingView::new);
        receiptJobProducer.sendReceiptJob(bookingView);
        return bookingView;
    }

    private double calculatePrice(double pricePerDay, int rooms) {
        return pricePerDay * rooms;
    }

}
