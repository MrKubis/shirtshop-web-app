package com.example.backend.api.Controllers;

import com.example.backend.api.DTO.ItemImage.DownloadImageDto;
import com.example.backend.api.DTO.ItemImage.ItemImageDto;
import com.example.backend.api.Exceptions.ItemImageNotFoundException;
import com.example.backend.api.Repositories.ItemImageRepository;
import com.example.backend.api.Services.ItemImageService;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/item/images")
public class ItemImageController {
    @Autowired
    private ItemImageService service;
    @Autowired
    private ItemImageRepository itemImageRepository;

    @PostMapping
    public ItemImageDto upload(@RequestParam MultipartFile file, UUID itemId) throws IOException {
        return service.upload(file,itemId);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<?> downloadImage(@PathVariable UUID id) throws IOException {
        DownloadImageDto dto = service.download(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dto.contentType()))
                .body(new ByteArrayResource(dto.imageData()));

    }
}
