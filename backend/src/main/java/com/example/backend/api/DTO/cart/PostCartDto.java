package com.example.backend.api.DTO.cart;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record PostCartDto(
        UUID userId,
        LocalDateTime createdAt,
        LocalDateTime expiredAt
) {}
