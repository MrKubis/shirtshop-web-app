package com.example.backend.api.DTO.User;

import com.example.backend.api.Models.Role;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record PostUserDto(
        String username,
        String email,
        String password,
        Set<Role> roles
) {}
