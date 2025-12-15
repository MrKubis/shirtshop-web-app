package com.example.backend.api.DTO.ItemInstance;

import java.util.UUID;

public record PostItemInstanceDto(
        UUID item_id,
        Double price,
        String status
) {
}
