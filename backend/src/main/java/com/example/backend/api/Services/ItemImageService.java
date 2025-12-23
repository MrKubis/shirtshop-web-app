package com.example.backend.api.Services;

import com.example.backend.api.DTO.ItemImage.DownloadImageDto;
import com.example.backend.api.DTO.ItemImage.ItemImageDto;
import com.example.backend.api.DTO.ItemImage.ItemImageMapper;
import com.example.backend.api.Exceptions.ItemImageNotFoundException;
import com.example.backend.api.Exceptions.ItemNotFoundException;
import com.example.backend.api.Models.Item;
import com.example.backend.api.Models.ItemImage;
import com.example.backend.api.Repositories.ItemImageRepository;
import com.example.backend.api.Repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ItemImageService {
    @Autowired
    private ItemImageRepository itemImageRepository;
    @Autowired
    private ItemImageMapper itemImageMapper;
    @Autowired
    private ItemRepository itemRepository;

    public ItemImageDto upload(MultipartFile file,UUID itemId) throws IOException {
        System.out.println("Uploading image...");
        System.out.println("itemId: "+itemId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(()->new ItemNotFoundException(itemId));

        System.out.println(file.getContentType());

        return itemImageMapper.toDto(
                itemImageRepository.save(
                        ItemImage.builder()
                        .name(file.getOriginalFilename())
                        .contentType(file.getContentType())
                        .imageData(file.getBytes())
                        .item(item)
                        .build()
                ));
    }
    public DownloadImageDto download(UUID id) throws IOException {
        return itemImageRepository.findById(id)
                .map(itemImageMapper::toDownloadDto)
                .orElseThrow(() -> new ItemImageNotFoundException(id));
    }
}
