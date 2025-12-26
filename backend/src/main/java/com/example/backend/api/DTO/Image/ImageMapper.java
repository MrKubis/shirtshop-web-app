package com.example.backend.api.DTO.Image;

import com.example.backend.api.Models.Image;
import com.example.backend.api.Models.ItemImage;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {
    public DownloadImageDto toDownloadDto(final Image entity){
        return DownloadImageDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .contentType(entity.getContentType())
                .imageData(entity.getImageData())
                .build();
    }
}
