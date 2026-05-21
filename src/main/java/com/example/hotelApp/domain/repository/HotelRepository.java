package com.example.hotelApp.domain.repository;

import com.example.hotelApp.domain.model.Hotel;
import com.example.hotelApp.domain.view.HotelView;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, Integer> {
    Hotel findHotelById(String id);
    List<Hotel> findManyHotelByCity(String city);
}
