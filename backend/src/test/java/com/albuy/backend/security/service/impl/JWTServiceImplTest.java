package com.albuy.backend.security.service.impl;

import com.albuy.backend.security.service.JWTServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JWTServiceImplTest {

    @InjectMocks
    private JWTServiceImpl jwtService;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void generateTokenReturnsValidToken() {
        when(userDetails.getUsername()).thenReturn("testUser");
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
        assertEquals("testUser", jwtService.extractUsername(token));
    }

    @Test
    public void isTokenValidReturnsTrueForValidToken() {
        when(userDetails.getUsername()).thenReturn("testUser");
        String token = jwtService.generateToken(userDetails);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    public void isTokenValidReturnsFalseForInvalidToken() {
        when(userDetails.getUsername()).thenReturn("testUser");
        String token = jwtService.generateToken(userDetails);
        UserDetails differentUser = new User("differentUser", "password", Collections.emptyList());
        assertFalse(jwtService.isTokenValid(token, differentUser));
    }



}