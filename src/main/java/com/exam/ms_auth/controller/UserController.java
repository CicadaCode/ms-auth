package com.exam.ms_auth.controller;

import com.exam.ms_auth.jwt.JwtUtils;
import com.exam.ms_auth.service.UserService;
import com.exam.ms_auth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class UserController {

    @Autowired
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody User u) {
        userService.registerUsuario(u);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email,
                                        @RequestParam String password) {
        String token = userService.login(email, password);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login2")
    public ResponseEntity<String> login(@RequestBody LoginDTO req) {
        String token = userService.login(req.getEmail(), req.getPassword());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validate(@RequestHeader("Authorization") String h) {
        String token = h.substring(7);
        boolean valid = jwtUtils.isTokenValid(token, jwtUtils.parseClaims(token).getSubject());
        return valid ? ResponseEntity.ok("valid") : ResponseEntity.status(401).body("invalid");
    }
}