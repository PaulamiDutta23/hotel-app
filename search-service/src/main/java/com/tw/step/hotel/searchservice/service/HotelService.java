package com.tw.step.hotel.searchservice.service;

import com.tw.step.hotel.searchservice.exception.HotelNotFoundException;
import com.tw.step.hotel.searchservice.exception.InsufficientAvailableRoomsException;
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

    public HotelView updateRoomsQuantity(String hotelId, int rooms) throws HotelNotFoundException, InsufficientAvailableRoomsException {
        Hotel hotel = hotelRepository.findHotelById(hotelId);

        if (hotel == null) throw new HotelNotFoundException(hotelId);

        if (!hotel.isRequestedRoomsAvailable(rooms))
            throw new InsufficientAvailableRoomsException(hotel.getName(), rooms);

        Hotel updatedHotel = hotel.bookRooms(rooms);
        hotelRepository.save(updatedHotel);
        return updatedHotel.project(HotelView::new);
    }
}
