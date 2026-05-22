package com.tw.step.hotel.searchservice.repository;

import com.tw.step.hotel.searchservice.model.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, Integer> {
    Hotel findHotelById(String id);
    List<Hotel> findManyHotelByCity(String city);
}
