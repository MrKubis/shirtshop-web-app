package com.example.backend.api.DTO.Item;


import com.example.backend.api.Exceptions.ItemInstanceNotFoundException;

import com.example.backend.api.Models.Item;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ItemMapper {
    public ItemDto toDto(final Item entity){
        return ItemDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType())
                .created_at(entity.getCreated_at())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .build();
    }
    public Item fromPatchDto(final Item entity,final PatchItemDto dto)
    {
        return Item.builder()
                .id(entity.getId())
                .name(dto.name())
                .type(dto.type())
                .created_at(entity.getCreated_at())
                .description(dto.description())
                .price(dto.price())
                .build();
    }
    public Item fromPostDto(final PostItemDto dto)
    {
        return Item.builder()
                .name(dto.name())
                .type(dto.type())
                .created_at(LocalDateTime.now())
                .description(dto.description())
                .price(dto.price())
                .build();
    }
}
