package com.example.backend.api.DTO.Item;

import lombok.Builder;

@Builder
public record PostItemDto (
    String name,
    String type,
    String description,
    Float price
){}
