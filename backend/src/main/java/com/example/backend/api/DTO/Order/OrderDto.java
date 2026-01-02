package com.example.backend.api.DTO.Order;

import com.example.backend.api.Models.OrderItem;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record OrderDto(
        UUID id,
        String email,
        String phoneNumber,
        List<OrderItem> items
) {}
