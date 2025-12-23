package com.example.backend.api.DTO.ItemImage;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PostItemImageDto(
        UUID itemId,
        byte[] imageData
) {
}
