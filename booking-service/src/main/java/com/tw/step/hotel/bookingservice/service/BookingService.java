package com.tw.step.hotel.bookingservice.service;

import com.tw.step.hotel.bookingservice.exception.BookingNotFoundException;
import com.tw.step.hotel.bookingservice.exception.HotelNotFoundException;
import com.tw.step.hotel.bookingservice.exception.InsufficientAvailableRoomsException;
import com.tw.step.hotel.bookingservice.model.Booking;
import com.tw.step.hotel.bookingservice.model.Hotel;
import com.tw.step.hotel.bookingservice.repository.BookingRepository;
import com.tw.step.hotel.bookingservice.repository.HotelRepository;
import com.tw.step.hotel.bookingservice.view.BookingRequest;
import com.tw.step.hotel.bookingservice.view.BookingView;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.tw.step.hotel.bookingservice.view.HotelView;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final SearchServiceClient searchServiceClient;
    private int currentBookingId;

    public BookingService(BookingRepository bookingRepository, SearchServiceClient searchServiceClient) {
        this.bookingRepository = bookingRepository;
        this.searchServiceClient = searchServiceClient;
        this.currentBookingId = 0;
    }

    public List<BookingView> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map((booking) -> booking.project(BookingView::new)).toList();
    }

    public BookingView bookHotel(String username, BookingRequest bookingRequest) throws HotelNotFoundException, InsufficientAvailableRoomsException {
        ResponseEntity<HotelView> response =
                searchServiceClient.bookHotel(bookingRequest);

        if(!response.getStatusCode().is2xxSuccessful()) throw new InsufficientAvailableRoomsException(bookingRequest.hotelId(),bookingRequest.totalRooms());
        HotelView hotelView = response.getBody();

        assert hotelView != null;
        double totalPrice = calculatePrice(hotelView.pricePerDay(),bookingRequest.totalRooms());
        Booking booking = new Booking(++currentBookingId, username, hotelView.name(), bookingRequest.totalRooms(), totalPrice);
        bookingRepository.save(booking);

        return booking.project(BookingView::new);
    }

    private double calculatePrice(double pricePerDay, int rooms) {
        return pricePerDay * rooms;
    }

    public byte[] generateReceipt(int bookingId) throws Exception, BookingNotFoundException {
        Optional<Booking> byId = bookingRepository.findById(bookingId);

        if (byId.isEmpty()) throw new BookingNotFoundException(bookingId);

        Booking booking = byId.get();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Booking Receipt")
                    .setBold()
                    .setFontSize(18));

            document.add(new Paragraph("Booking ID: " + booking.getId()));
            document.add(new Paragraph("Total Price: " + booking.getTotalPrice()));
            document.add(new Paragraph("Total Rooms: " + booking.getTotalRooms()));
            document.add(new Paragraph("Hotel Name: " + booking.getHotelName()));
            document.add(new Paragraph("Username: " + booking.getUsername()));

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
