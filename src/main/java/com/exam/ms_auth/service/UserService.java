package com.exam.ms_auth.service;

import com.exam.ms_auth.entity.User;
import com.exam.ms_auth.repository.UserRepository;
import com.exam.ms_auth.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void registerUsuario(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
    }

    public String login(String email, String password) {
        User u = repo.findByEmail(email).orElseThrow(() -> new RuntimeException("No such user"));
        if (!encoder.matches(password, u.getPassword())) {
            throw new RuntimeException("Bad credentials");
        }
        return jwtUtils.generateToken(u.getEmail(), u.getRol().name());
    }
}
