package com.example.backend.api.DTO.ItemInstance;

import com.example.backend.api.Models.ItemInstance;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ItemInstanceMapper {
    public ItemInstanceDto toDto(final ItemInstance entity){
        return ItemInstanceDto.builder()
                .id(entity.getId())
                .item_id(entity.getItem_id())
                .price(entity.getPrice())
                .status(entity.getStatus())
                .build();
    }
    public ItemInstance toItemInstance(final ItemInstanceDto dto){
        return ItemInstance.builder()
                .id(dto.id())
                .item_id(dto.item_id())
                .price(dto.price())
                .status(dto.status())
                .build();
    }
    public ItemInstance fromPatchDto(final ItemInstance entity,final PatchItemInstanceDto dto)
    {
        return ItemInstance.builder()
                .id(entity.getId())
                .item_id(entity.getItem_id())
                .price(dto.price())
                .status(dto.status())
                .build();
    }
    public ItemInstance fromPostDto(final PostItemInstanceDto dto)
    {
        return ItemInstance.builder()
                .item_id(dto.item_id())
                .price(dto.price())
                .status(dto.status())
                .build();
    }
}
