package com.example.backend.api.DTO.Order;

import com.example.backend.api.DTO.OrderItem.PostOrderItemDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record PostOrderDto(
        @NotBlank
        @Pattern(regexp = "^\\+48\\d{9}$", message = "Phone number must start with +48 and contain 9 digits")
        String phoneNumber,
        @Email
        @NotBlank
        String email,
        @Valid
        List<PostOrderItemDto> items
){}
