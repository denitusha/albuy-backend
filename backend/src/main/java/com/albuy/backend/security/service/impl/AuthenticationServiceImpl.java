package com.albuy.backend.security.service;

import com.albuy.backend.persistence.entity.Role;
import com.albuy.backend.persistence.entity.User;
import com.albuy.backend.persistence.repository.UserRepository;
import com.albuy.backend.security.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements  AuthenticationService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private  final AuthenticationManager authenticationManager;

    private final JWTService jwtService;
    public User signup(SignUpRequest signUpRequest){
        User user = new User();

        user.setEmail(signUpRequest.getEmail());
        user.setFirst_name(signUpRequest.getFirstName());
        user.setSecond_name(signUpRequest.getLastName());
        user.setCompany_name(signUpRequest.getCompanyName());
        Role role = Role.valueOf(signUpRequest.getRole());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        User savedUser = userRepository.save(user);

        return userRepository.save(user);

    }

    public JwtAuthenticationResponse signin(SignInRequest signInRequest){
        log.info("Sign in request: {}", signInRequest);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                signInRequest.getPassword()));

        var user = userRepository.findByEmail(signInRequest
                .getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        var jwt = jwtService.generateToken(user);

        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);

        var username = user.getUsername();

        var role = user.getRole();


        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        jwtAuthenticationResponse.setUsername(username);
        jwtAuthenticationResponse.setFirstName(user.getFirst_name());
        jwtAuthenticationResponse.setLastName(user.getSecond_name());
        jwtAuthenticationResponse.setRole(role);
        jwtAuthenticationResponse.setUserId(user.getId());

        return jwtAuthenticationResponse;
    }


    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

            return jwtAuthenticationResponse;
        }
        return null;
    }
}
