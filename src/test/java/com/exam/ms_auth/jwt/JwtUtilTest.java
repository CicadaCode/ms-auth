package com.exam.ms_auth.jwt;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilsTest {

    private final JwtUtils jwt = new JwtUtils();

    @Test
    void generateAndParseToken() {
        String username = "Jose";
        String role = "USER";

        String token = jwt.generateToken(username, role);

        Claims claims = jwt.parseClaims(token);
        assertEquals(username, claims.getSubject());
        assertEquals(role, claims.get("role"));

        assertTrue(jwt.isTokenValid(token, username));
    }

    @Test
    void tokenInvalid() {
        String token = jwt.generateToken("Me", "ADMIN");

        assertFalse(jwt.isTokenValid(token, "NotMe"));
    }

    @Test
    void tokenRoleClaim() {
        String token = jwt.generateToken("Ana", "ADMIN");
        Claims claims = jwt.parseClaims(token);

        assertEquals("ADMIN", claims.get("role"));
    }
}
