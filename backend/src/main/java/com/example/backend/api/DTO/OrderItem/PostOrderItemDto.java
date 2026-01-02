package com.example.backend.api.DTO.OrderItem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PostOrderItemDto(
        @NotNull(message = "item id is required")
        UUID itemId,
    @NotNull
    @Min(value = 1, message = "Quantity must be at least 1")
    Integer quantity
) {}
