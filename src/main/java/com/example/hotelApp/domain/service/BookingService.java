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
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
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
        System.out.println(bookingRequest.hotelId());
        System.out.println(hotel);
        if (hotel == null) throw new HotelNotFoundException(bookingRequest.hotelId());

        if (!hotel.isRequestedRoomsAvailable(bookingRequest.totalRooms()))
            throw new InsufficientAvailableRoomsException(hotel.getName(), bookingRequest.totalRooms());

        Hotel updatedHotel = hotel.bookRooms(bookingRequest.totalRooms());
        hotelRepository.save(updatedHotel);
        double totalPrice = updatedHotel.calculatePrice(bookingRequest.totalRooms());
        Booking booking = new Booking(++currentBookingId, username, updatedHotel.getName(), updatedHotel.getAvailableRooms(), totalPrice);
        bookingRepository.save(booking);

        return booking.project(BookingView::new);
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
