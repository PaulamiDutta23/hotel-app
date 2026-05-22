package com.tw.step.hotel.bookingservice.service;

import com.tw.step.hotel.bookingservice.model.Hotel;
import com.tw.step.hotel.bookingservice.repository.HotelRepository;
import com.tw.step.hotel.bookingservice.view.HotelView;
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
