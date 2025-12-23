package com.example.backend.api.DTO.ItemImage;

import lombok.Builder;

import java.util.UUID;

@Builder
public record DownloadImageDto(
        UUID id,
        UUID itemId,
        String name,
        String contentType,
        byte[] imageData

) {}
