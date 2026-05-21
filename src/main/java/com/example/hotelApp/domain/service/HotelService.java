package com.example.hotelApp.domain.service;

import com.example.hotelApp.domain.model.Booking;
import com.example.hotelApp.domain.model.Hotel;
import com.example.hotelApp.domain.repository.HotelRepository;
import com.example.hotelApp.domain.view.BookingView;
import com.example.hotelApp.domain.view.HotelView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<HotelView> getHotels(String city) {
        List<Hotel> hotels = hotelRepository.findManyHotelByCity(city);
        return hotels.stream().map((hotel) -> hotel.project(HotelView::new)).toList();
    }
}
