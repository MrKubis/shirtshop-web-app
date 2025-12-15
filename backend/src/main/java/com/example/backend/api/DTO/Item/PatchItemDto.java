package com.example.backend.api.DTO.Item;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record PatchItemDto(
        String name,
        String type
) {}
