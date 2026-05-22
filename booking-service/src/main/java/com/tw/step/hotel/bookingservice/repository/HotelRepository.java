package com.tw.step.hotel.bookingservice.repository;

import com.tw.step.hotel.bookingservice.model.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, Integer> {
    Hotel findHotelById(String id);
    List<Hotel> findManyHotelByCity(String city);
}
