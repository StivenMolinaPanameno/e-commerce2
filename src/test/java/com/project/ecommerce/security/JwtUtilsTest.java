package com.project.ecommerce.security;

import com.project.ecommerce.security.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilsTest {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        jwtUtils.setSecretKey(secretKey);
        jwtUtils.setTimeExpiration(timeExpiration);
    }

    @Test
    void testGenerateAccessToken() {
        String username = "testUser";
        String token = jwtUtils.generateAccesToken(username);

        assertNotNull(token);


        // Parse and verify token
        Claims claims = Jwts.parserBuilder().setSigningKey(jwtUtils.getSecretKey())
                        .build()
                                .parseClaimsJws(token)
                                        .getBody();

        assertEquals(username, claims.getSubject());
    }



    @Test
    void testIsTokenValid() {
        String username = "testUser";
        String token = jwtUtils.generateAccesToken(username);

        assertTrue(jwtUtils.isTokenValid(token));
    }

    @Test
    void testGetUsernameFromToken() {
        String username = "testUser";
        String token = jwtUtils.generateAccesToken(username);

        assertEquals(username, jwtUtils.getUsernameFromToken(token));
    }

    // Puedes continuar escribiendo más pruebas según tus necesidades
}
