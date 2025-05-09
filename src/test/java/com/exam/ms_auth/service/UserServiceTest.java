package com.exam.ms_auth.service;

import com.exam.ms_auth.dto.RegisterRequest;
import com.exam.ms_auth.dto.UserDTO;
import com.exam.ms_auth.entity.Rol;
import com.exam.ms_auth.entity.User;
import com.exam.ms_auth.jwt.JwtUtil;
import com.exam.ms_auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUserDto() {
        RegisterRequest req = RegisterRequest.builder()
                .nombre("John")
                .email("john@gmail.com")
                .password("123456")
                .rol(Rol.USUARIO)
                .build();

        User userGuardado = User.builder()
                .id(1L)
                .nombre("John")
                .email("john@gmail.com")
                .password("encoded")
                .rol(Rol.USUARIO)
                .build();

        when(passwordEncoder.encode("123456")).thenReturn("encoded");

        when(userRepository.save(any(User.class))).thenReturn(userGuardado);

        UserDTO result = userService.register(req);

        assertNotNull(result);
        assertEquals("John", result.getNombre());
        assertEquals("john@gmail.com", result.getEmail());
        assertEquals(Rol.USUARIO, result.getRol());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void loginReturnsJwtToken() {
        String email = "john@gmail.com";
        String rawPassword = "123456";
        String encodedPassword = "encoded";

        User user = User.builder()
                .id(1L)
                .email(email)
                .password(encodedPassword)
                .rol(Rol.USUARIO)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(jwtUtil.generateToken(user.getNombre(), user.getRol())).thenReturn("jwt-token");

        String token = userService.login(email, rawPassword);

        assertEquals("jwt-token", token);
    }

    @Test
    void validateUserDto() {
        String token = "jwt-token";
        String email = "juan@test.com";

        User user = User.builder()
                .id(1L)
                .email(email)
                .nombre("Juan")
                .rol(Rol.USUARIO)
                .build();

        when(jwtUtil.extractUsername(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDTO result = userService.validate(token);

        assertEquals(email, result.getEmail());
        assertEquals("Juan", result.getNombre());
        assertEquals(Rol.USUARIO, result.getRol());
    }
}
