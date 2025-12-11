package com.example.backend.api.DTO.cart;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record CartDto(
        UUID id,
        UUID userId,
        LocalDateTime createdAt,
        LocalDateTime expiredAt
){}
