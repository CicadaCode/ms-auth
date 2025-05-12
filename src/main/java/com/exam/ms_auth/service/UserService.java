package com.exam.ms_auth.service;

import com.exam.ms_auth.dto.RegisterRequest;
import com.exam.ms_auth.dto.UserDTO;
import com.exam.ms_auth.entity.Rol;
import com.exam.ms_auth.entity.User;
import com.exam.ms_auth.jwt.JwtUtil;
import com.exam.ms_auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    public UserDTO register(RegisterRequest req) {
        User user = User.builder()
                .nombre(req.getNombre())
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))
                .rol(req.getRol())
                .build();

        user = userRepository.save(user);
        return new UserDTO(user);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(UserDTO::new).toList();
    }

    public String login(String email, String rawPwd) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        if (!encoder.matches(rawPwd, user.getPassword())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }
        return jwtUtil.generateToken(user.getNombre(), user.getRol());
    }

    public UserDTO validate(String token) {
        String email = jwtUtil.extractUsername(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new UserDTO(user);
    }

    public List<UserDTO> findByRol(Rol rol) {
        return userRepository.findAllByRol(rol).stream().map(UserDTO::new).toList();
    }
}