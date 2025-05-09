package com.exam.ms_auth.jwt;

import com.exam.ms_auth.entity.Rol;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private final JwtUtil jwt = new JwtUtil();

    @Test
    void generateAndParseToken() {
        String username = "John";

        String token = jwt.generateToken(username, Rol.USUARIO);

        Claims claims = jwt.parseClaims(token);
        assertEquals(username, claims.getSubject());
        assertEquals(Rol.USUARIO, claims.get("role"));

        assertTrue(jwt.isTokenValid(token, username));
    }

    @Test
    void tokenInvalid() {
        String token = jwt.generateToken("Me", Rol.ADMIN);

        assertFalse(jwt.isTokenValid(token, "NotMe"));
    }

    @Test
    void tokenRoleClaim() {
        String token = jwt.generateToken("Ana", Rol.ADMIN);
        Claims claims = jwt.parseClaims(token);

        assertEquals(Rol.ADMIN, claims.get("role"));
    }
}