package com.example.backend.api.DTO;

public record RegisterUserDTO(
        String userName,
        String email,
        String password
) { }
