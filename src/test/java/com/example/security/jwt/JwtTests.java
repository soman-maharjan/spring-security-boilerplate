package com.example.security.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtTests {
    @Autowired
    JwtService jwtService;

    @Autowired
    JwtTestsUtils jwtTestsUtils;

    @Test
    public void testGenerateToken() {
        String token = jwtTestsUtils.generateTestJwtToken();

        assertNotNull(token);
        assertTrue(token.startsWith("eyJ"));
    }

    @Test
    public void testExtractEmail(){
        String token = jwtTestsUtils.generateTestJwtToken();
        String email = jwtService.extractEmail(token);

        assertNotNull(email);
        assertEquals(email, jwtTestsUtils.TEST_EMAIL);
    }

    @Test
    public void testExtractExpirationDate(){
        String token = jwtTestsUtils.generateTestJwtToken();
        Date expirationDate = jwtService.extractExpirationDate(token);

        assertNotNull(expirationDate);
    }

    @Test
    public void testExpirationDate(){
        String token = jwtTestsUtils.generateTestJwtToken();

        Date expirationDate = jwtService.extractExpirationDate(token);
        Date currentDate = new Date();

        long timeDifference = expirationDate.getTime() - currentDate.getTime();
        assertTrue(timeDifference < TimeUnit.MINUTES.toMillis(10), "Token expiration time is not less than 10 minutes");
    }
}
