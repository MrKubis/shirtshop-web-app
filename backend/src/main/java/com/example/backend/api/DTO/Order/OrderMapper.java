package com.example.backend.api.DTO.Order;

import com.example.backend.api.Models.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderDto toDto(Order entity){
        return OrderDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .items(entity.getItems())
                .build();
    }
}
