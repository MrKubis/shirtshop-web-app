package com.example.backend.api.Services;

import com.example.backend.api.DTO.Image.DownloadImageDto;
import com.example.backend.api.DTO.Image.ImageMapper;
import com.example.backend.api.Exceptions.ImageNotFoundException;
import com.example.backend.api.Models.Image;
import com.example.backend.api.Repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageMapper imageMapper;

    public DownloadImageDto download(UUID id) throws IOException {
        return imageRepository.findById(id)
                .map(imageMapper::toDownloadDto)
                .orElseThrow(() -> new ImageNotFoundException(id));

    }
}
