package com.example.backend.api.DTO.Image;

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
