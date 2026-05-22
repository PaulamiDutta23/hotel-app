package com.tw.step.hotel.bookingservice.repository;

import com.tw.step.hotel.bookingservice.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends MongoRepository<Booking, Integer> {
}
