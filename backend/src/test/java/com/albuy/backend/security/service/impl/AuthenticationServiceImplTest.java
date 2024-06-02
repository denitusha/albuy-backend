package com.albuy.backend.security.service.impl;

import com.albuy.backend.persistence.entity.Role;
import com.albuy.backend.persistence.entity.User;
import com.albuy.backend.persistence.repository.UserRepository;
import com.albuy.backend.security.dto.JwtAuthenticationResponse;
import com.albuy.backend.security.dto.RefreshTokenRequest;
import com.albuy.backend.security.dto.SignInRequest;
import com.albuy.backend.security.dto.SignUpRequest;
import com.albuy.backend.security.service.AuthenticationServiceImpl;
import com.albuy.backend.security.service.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void signupCreatesNewUser() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@test.com");
        signUpRequest.setPassword("password");
        signUpRequest.setFirstName("Test");
        signUpRequest.setLastName("User");
        signUpRequest.setCompanyName("Test Company");
        signUpRequest.setRole("BUYER");

        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setFirst_name(signUpRequest.getFirstName());
        user.setSecond_name(signUpRequest.getLastName());
        user.setCompany_name(signUpRequest.getCompanyName());
        user.setRole(Role.BUYER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = authenticationService.signup(signUpRequest);

        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getFirst_name(), savedUser.getFirst_name());
        assertEquals(user.getSecond_name(), savedUser.getSecond_name());
        assertEquals(user.getCompany_name(), savedUser.getCompany_name());
        assertEquals(user.getRole(), savedUser.getRole());
    }

    @Test
    public void signinReturnsJwtAuthenticationResponseWhenCredentialsAreValid() {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmail("test@test.com");
        signInRequest.setPassword("password");

        User user = new User();
        user.setEmail(signInRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signInRequest.getPassword()));

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn("token");
        when(jwtService.generateRefreshToken(any(Map.class), any(User.class))).thenReturn("refreshToken");

        JwtAuthenticationResponse response = authenticationService.signin(signInRequest);

        assertEquals(user.getEmail(), response.getUsername());
        assertEquals("token", response.getToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }

    @Test
    public void refreshTokenReturnsNewJwtAuthenticationResponseWhenTokenIsValid() {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setToken("token");

        User user = new User();
        user.setEmail("test@test.com");

        when(jwtService.extractUsername(any(String.class))).thenReturn(user.getEmail());
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(any(String.class), any(User.class))).thenReturn(true);
        when(jwtService.generateToken(any(User.class))).thenReturn("newToken");

        JwtAuthenticationResponse response = authenticationService.refreshToken(refreshTokenRequest);

        assertEquals("newToken", response.getToken());
        assertEquals("token", response.getRefreshToken());
    }
}