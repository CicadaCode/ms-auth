package com.exam.ms_auth.controller;

import com.exam.ms_auth.dto.JwtResponse;
import com.exam.ms_auth.dto.LoginRequest;
import com.exam.ms_auth.dto.RegisterRequest;
import com.exam.ms_auth.dto.UserDTO;
import com.exam.ms_auth.entity.Rol;
import com.exam.ms_auth.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest request) {
        UserDTO newUser = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        String token = userService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    // 3. Validar token y obtener datos de usuario
    @GetMapping("/validate")
    public ResponseEntity<UserDTO> validateToken(
            @RequestHeader("Authorization") String bearerToken) {
        UserDTO user = userService.validate(bearerToken.substring(7));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/test/superadmin")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public List<UserDTO> testSuperadmin() {
        return userService.findByRol(Rol.SUPERADMIN);
    }

    @GetMapping("/test/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> testAdmin() {
        return userService.findByRol(Rol.ADMIN);
    }

    @GetMapping("/test/user")
    @PreAuthorize("hasRole('USUARIO')")
    public List<UserDTO> testUser() {
        return userService.findByRol(Rol.USUARIO);
    }
}