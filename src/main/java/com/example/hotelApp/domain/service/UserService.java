package com.example.hotelApp.domain.service;

import com.example.hotelApp.domain.exception.InvalidUserNameCreationException;
import com.example.hotelApp.domain.model.User;
import com.example.hotelApp.domain.repository.UserRepository;
import com.example.hotelApp.domain.view.UserView;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserView register(User user) throws InvalidUserNameCreationException {
        Optional<User> existingUser = this.userRepository.findByUsername(user.getUsername());
        if(!existingUser.isEmpty()) {
           throw new InvalidUserNameCreationException("Username already exists");
        }
        this.userRepository.save(user);
        return new UserView(user.getUsername(), user.getPassword());
    }
}
