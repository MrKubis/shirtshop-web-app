package com.example.backend.api.DTO.cart;

import java.time.LocalDateTime;
import java.util.UUID;

public record PatchCartDto(
        UUID userId,
        LocalDateTime createdAt,
        LocalDateTime expiredAt
) {
}
