package com.example.hotelApp.domain.service;

import com.example.hotelApp.domain.exception.InvalidCredentialsException;
import com.example.hotelApp.domain.exception.InvalidUserNameCreationException;
import com.example.hotelApp.domain.model.User;
import com.example.hotelApp.domain.repository.UserRepository;
import com.example.hotelApp.domain.view.UserView;
import org.springframework.security.core.userdetails.UserDetails;
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

//    public UserDetails findUserByUsername(String username) {
//        return this.userRepository.findByUsername(username).get();
//    }
}
