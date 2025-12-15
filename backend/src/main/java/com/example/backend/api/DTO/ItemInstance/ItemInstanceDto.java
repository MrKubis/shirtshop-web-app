package com.example.backend.api.DTO.ItemInstance;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ItemInstanceDto(
    UUID id,
    UUID item_id,
    Double price,
    String status
) {}
