package com.example.backend.api.DTO.cart;

import com.example.backend.api.Models.ItemInstance;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record CartDto(
        UUID id,
        UUID userId,
        List<UUID> itemInstanceIdList,
        LocalDateTime createdAt,
        LocalDateTime expiredAt
){}
