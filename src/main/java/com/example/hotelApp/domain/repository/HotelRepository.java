package com.example.hotelApp.domain.repository;

import com.example.hotelApp.domain.model.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, Integer> {
    Hotel findHotelById(int id);
}
