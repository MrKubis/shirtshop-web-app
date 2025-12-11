package com.example.backend.api.DTO.cart;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PatchCartDto(
        UUID userId,
        List<UUID> itemInstanceIdList,
        LocalDateTime createdAt,
        LocalDateTime expiredAt
) {
}
