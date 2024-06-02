package com.albuy.backend.security.dto;


import com.albuy.backend.persistence.entity.Role;
import lombok.Data;

@Data
public class JwtAuthenticationResponse {

    private Long userId;
    private String token;

    private String refreshToken;

    private String username;

    private Role role;

    private String firstName;

    private String lastName;



}
