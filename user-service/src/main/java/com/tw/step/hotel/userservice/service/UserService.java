package com.tw.step.hotel.userservice.service;

import com.tw.step.hotel.userservice.exception.InvalidCredentialsException;
import com.tw.step.hotel.userservice.exception.InvalidUserNameCreationException;
import com.tw.step.hotel.userservice.model.User;
import com.tw.step.hotel.userservice.repository.UserRepository;
import com.tw.step.hotel.userservice.view.UserView;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public UserView register(User user) throws InvalidUserNameCreationException {
        if(!doesUserExists(user.getUsername())) {
           throw new InvalidUserNameCreationException("Username already exists");
        }
        this.userRepository.save(user);
        return new UserView(user.getUsername(), user.getPassword());
    }

    public String login(User user) throws InvalidCredentialsException {
        if(doesUserExists(user.getUsername())) {
            throw new InvalidCredentialsException("Username does not exists");
        }

        return jwtService.generateToken(user.getUsername());
    }

    public boolean doesUserExists(String username) {
        Optional<User> existingUser = this.userRepository.findByUsername(username);
        return existingUser.isEmpty();
    }
}
