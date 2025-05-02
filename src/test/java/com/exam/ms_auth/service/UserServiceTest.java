package com.exam.ms_auth.service;

import com.exam.ms_auth.entity.Rol;
import com.exam.ms_auth.entity.User;
import com.exam.ms_auth.jwt.JwtUtils;
import com.exam.ms_auth.repository.UserRepository;
import com.exam.ms_auth.service.builder.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository repo;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private UserService svc;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerEncodesAndSaves() {
        User u = UserBuilder.builder()
                .email("a@b.com")
                .password("pwd")
                .rol(Rol.USUARIO)
                .build();

        svc.registerUsuario(u);

        assertNotEquals("pwd", u.getPassword());
        verify(repo).save(u);
    }

    @Test
    void loginSuccessReturnsToken() {
        User u = UserBuilder.builder()
                .email("x")
                .password("pw")
                .rol(Rol.USUARIO)
                .build();

        u.setPassword(encoder.encode("pw"));

        when(repo.findByEmail("x")).thenReturn(Optional.of(u));
        when(jwtUtils.generateToken("x", Rol.USUARIO.name())).thenReturn("TOK");

        String token = svc.login("x", "pw");

        assertEquals("TOK", token);
        verify(repo).findByEmail("x");
        verify(jwtUtils).generateToken("x", Rol.USUARIO.name());
    }
}
