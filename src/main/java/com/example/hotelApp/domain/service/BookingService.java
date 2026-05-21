package com.example.hotelApp.domain.service;

import com.example.hotelApp.domain.exceptions.BookingNotFoundException;
import com.example.hotelApp.domain.exceptions.HotelNotFoundException;
import com.example.hotelApp.domain.exceptions.InsufficientAvailableRoomsException;
import com.example.hotelApp.domain.model.Booking;
import com.example.hotelApp.domain.model.Hotel;
import com.example.hotelApp.domain.repository.BookingRepository;
import com.example.hotelApp.domain.repository.HotelRepository;
import com.example.hotelApp.domain.view.BookingRequest;
import com.example.hotelApp.domain.view.BookingView;
import com.example.hotelApp.domain.view.HotelView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private int currentBookingId;

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

        if (!hotel.isRequestedRoomsAvailable(bookingRequest.totalRooms()))
            throw new InsufficientAvailableRoomsException(hotel.getName(), bookingRequest.hotelId());

        Hotel updatedHotel = hotel.bookRooms(bookingRequest.totalRooms());
        hotelRepository.save(updatedHotel);
        double totalPrice = updatedHotel.calculatePrice(bookingRequest.totalRooms());
        Booking booking = new Booking(++currentBookingId, username, updatedHotel.getName(), updatedHotel.getAvailableRooms(), totalPrice);
        bookingRepository.save(booking);

        return booking.project(BookingView::new);
    }

    public byte[] generateReceipt(int bookingId) throws Exception, BookingNotFoundException {
        Optional<Booking> byId = bookingRepository.findById(bookingId);

        if(byId.isEmpty()) throw new BookingNotFoundException(bookingId);

        Booking booking = byId.get();
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);

            float lineHeight = 20;

            content.beginText();
            content.newLineAtOffset(100, 650);
            content.setFont(PDType1Font.HELVETICA, 12);

            content.showText("Receipt");
            content.showText("-----------------");

            content.showText(String.format("Booking ID: %s", booking.getId()));
            content.newLineAtOffset(0, -lineHeight);
            content.showText(String.format("Total Price: %.2f", booking.getTotalPrice()));
            content.newLineAtOffset(0, -lineHeight);
            content.showText(String.format("Total Rooms: %d", booking.getTotalRooms()));
            content.newLineAtOffset(0, -lineHeight);
            content.showText(String.format("Hotel Name: %s", booking.getHotelName()));
            content.newLineAtOffset(0, -lineHeight);
            content.showText(String.format("Username: %s", booking.getUsername()));

            content.endText();

            content.close();

            document.save(out);
            return out.toByteArray();
        }

    }
}
