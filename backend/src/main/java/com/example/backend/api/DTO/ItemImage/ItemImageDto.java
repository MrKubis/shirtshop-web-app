package com.example.backend.api.DTO.ItemImage;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ItemImageDto(
        UUID id,
        UUID itemId,
        String name,
        String contentType
) {}