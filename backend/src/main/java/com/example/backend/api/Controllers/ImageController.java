package com.example.backend.api.Controllers;

import com.example.backend.api.DTO.Image.DownloadImageDto;
import com.example.backend.api.Services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    private ImageService service;

    @GetMapping(value = "/download/{id}")
    ResponseEntity<?> downloadImage(@PathVariable UUID id) throws IOException {
        DownloadImageDto dto = service.download(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dto.contentType()))
                .body(dto.imageData());
    }
}
