package com.example.hotelApp.domain.repository;

import com.example.hotelApp.domain.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends MongoRepository<Booking, Integer> {
}
