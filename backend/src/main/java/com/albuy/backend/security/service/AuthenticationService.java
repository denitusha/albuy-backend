package com.albuy.backend.security.service;

import com.albuy.backend.persistence.entity.User;
import com.albuy.backend.security.dto.JwtAuthenticationResponse;
import com.albuy.backend.security.dto.RefreshTokenRequest;
import com.albuy.backend.security.dto.SignInRequest;
import com.albuy.backend.security.dto.SignUpRequest;

public interface AuthenticationService {

    User signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
