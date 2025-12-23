package com.example.backend.api.DTO.ItemImage;

import com.example.backend.api.Models.ItemImage;
import org.springframework.stereotype.Component;

@Component
public class ItemImageMapper {
    public ItemImageDto toDto(final ItemImage entity){
        return ItemImageDto.builder()
                .id(entity.getId())
                .itemId(entity.getItem().getId())
                .name(entity.getName())
                .contentType(entity.getContentType())
                .build();
    }
    public DownloadImageDto toDownloadDto(final ItemImage entity){
        return DownloadImageDto.builder()
                .id(entity.getId())
                .itemId(entity.getItem().getId())
                .name(entity.getName())
                .contentType(entity.getContentType())
                .imageData(entity.getImageData())
                .build();
    }
}
