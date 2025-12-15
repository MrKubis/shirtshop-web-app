package com.example.backend.api.DTO.User;

import com.example.backend.api.Models.Role;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
public record UserDto(
        UUID id,
        String email,
        LocalDateTime created_at,
        Set<Role> roles
) {}
