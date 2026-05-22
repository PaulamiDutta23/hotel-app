package com.tw.step.hotel.searchservice.service;

import com.tw.step.hotel.searchservice.model.Hotel;
import com.tw.step.hotel.searchservice.repository.HotelRepository;
import com.tw.step.hotel.searchservice.view.HotelView;
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
