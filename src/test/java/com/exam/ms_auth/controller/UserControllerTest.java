package com.exam.ms_auth.controller;

import com.exam.ms_auth.jwt.JwtUtils;
import com.exam.ms_auth.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private UserController controller;
    private UserService userService;
    private JwtUtils jwtUtils;

    @BeforeEach
    void setup() {
        userService = mock(UserService.class);
        controller = new UserController(userService, jwtUtils);
    }

    @Test
    void loginOk() {
        when(userService.login("user", "pass")).thenReturn("token");

        ResponseEntity<?> response = controller.login("Emma@gmail.com", "23498");

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
