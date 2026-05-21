package com.example.hotelApp.domain.service;

import com.example.hotelApp.domain.exception.InvalidUserNameCreationException;
import com.example.hotelApp.domain.model.User;
import com.example.hotelApp.domain.repository.UserRepository;
import com.example.hotelApp.domain.view.UserView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.AutoConfigureDataMongo;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureRestTestClient
@AutoConfigureDataMongo
class UserServiceTest {

    @Autowired
    UserRepository userRepo;

    @Test
    void shouldRegisterTheUser() throws InvalidUserNameCreationException {
        UserService userService = new UserService(userRepo);
        UserView user = userService.register(new User("haji", "1234"));
        assertEquals(new UserView("haji", "1234"), user);
    }

    @Test
    void shouldThrowForCreatingWithExistingUserName() throws InvalidUserNameCreationException {
        UserService userService = new UserService(userRepo);
        userService.register(new User("anonymous", "1234"));
        InvalidUserNameCreationException exception = assertThrows(InvalidUserNameCreationException.class, () -> userService.register(new User("anonymous", "1234")));
        assertEquals("Username already exists", exception.getMessage());
    }
}