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
                .build();
    }
    public Item toItem(final ItemDto dto){
        return Item.builder()
                .id(dto.id())
                .name(dto.name())
                .type(dto.type())
                .created_at(dto.created_at())
                .build();
    }
    public Item fromPatchDto(final Item entity,final PatchItemDto dto)
    {
        return Item.builder()
                .id(entity.getId())
                .name(dto.name())
                .type(dto.type())
                .created_at(entity.getCreated_at())
                .build();
    }
    public Item fromPostDto(final PostItemDto dto)
    {
        return Item.builder()
                .name(dto.name())
                .type(dto.type())
                .created_at(LocalDateTime.now())
                .build();
    }

}
