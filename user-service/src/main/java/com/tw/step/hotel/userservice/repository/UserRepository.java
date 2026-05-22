package com.tw.step.hotel.userservice.repository;

import com.tw.step.hotel.userservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    public Optional<User> findByUsername(String username);
}
