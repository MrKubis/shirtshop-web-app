package com.example.backend.api.DTO.ItemImage;

import com.example.backend.api.Models.ItemImage;
import org.springframework.stereotype.Component;

@Component
public class ItemImageMapper {
    public ItemImageDto toDto(final ItemImage entity){
        return ItemImageDto.builder()
                .id(entity.getId())
                .itemId(entity.getItem().getId())
                .imageId(entity.getImage().getId())
                .name(entity.getImage().getName())
                .contentType(entity.getImage().getContentType())
                .build();
    }

}
