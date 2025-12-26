package com.example.backend.api.Controllers;

import com.example.backend.api.DTO.ItemImage.ItemImageDto;
import com.example.backend.api.Services.ItemImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/images/item")
public class ItemImageController {
    @Autowired
    private ItemImageService service;
    @GetMapping
    List<ItemImageDto> getAll(){
        return service.getAll();
    }
    @GetMapping(value = "/{itemId}")
    List<ItemImageDto> getItemImage(@PathVariable UUID itemId) throws IOException {
        return service.getItemImageList(itemId);
    }
    @PostMapping
    public ItemImageDto upload(@RequestParam MultipartFile file,@RequestParam UUID itemId) throws IOException {
        return service.upload(file,itemId);
    }
}
