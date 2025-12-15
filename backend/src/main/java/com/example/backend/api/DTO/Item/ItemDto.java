package com.example.backend.api.DTO.Item;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ItemDto(
        UUID id,
        String name,
        String type,
        LocalDateTime created_at
) {}

