package com.exam.ms_auth.controller;

import com.exam.ms_auth.dto.LoginRequest;
import com.exam.ms_auth.dto.RegisterRequest;
import com.exam.ms_auth.dto.UserDTO;
import com.exam.ms_auth.jwt.JwtUtil;
import com.exam.ms_auth.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.exam.ms_auth.entity.Rol.USUARIO;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerReturns201AndUserDto() throws Exception {
        UserDTO response = new UserDTO(1L, "John", "john@gmail.com", USUARIO);

        Mockito.when(userService.register(Mockito.any(RegisterRequest.class))).thenReturn(response);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "nombre": "John",
                          "email": "john@gmail.com",
                          "password": "123456",
                          "rol": "USUARIO"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("John"));
    }

    @Test
    void loginReturnsToken() throws Exception {
        LoginRequest request = new LoginRequest("john@gmail.com", "123456");

        when(userService.login(("john@gmail.com"), ("123456")))
                .thenReturn("mocked-jwt-token");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"));
    }

    @Test
    void validateReturnUsuarioDto() throws Exception {
        UserDTO userDTO = new UserDTO(1L, "John", "John@gmail.com", USUARIO);

        when(userService.validate("token123")).thenReturn(userDTO);

        mockMvc.perform(get("/auth/validate")
                        .header("Authorization", "Bearer token123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("John"))
                .andExpect(jsonPath("$.rol").value("USUARIO"));
    }
}