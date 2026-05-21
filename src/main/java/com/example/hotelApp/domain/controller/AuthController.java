package com.example.hotelApp.domain.controller;

import com.example.hotelApp.domain.exception.InvalidUserNameCreationException;
import com.example.hotelApp.domain.model.User;
import com.example.hotelApp.domain.service.UserService;
import com.example.hotelApp.domain.view.UserView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserView> registerUser(@RequestBody User user) throws InvalidUserNameCreationException {
        UserView userView = userService.register(user);
        return ResponseEntity.ok(userView);
    }
}
