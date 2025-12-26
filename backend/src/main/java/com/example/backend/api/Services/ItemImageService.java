package com.example.backend.api.Services;

import com.example.backend.api.DTO.ItemImage.ItemImageDto;
import com.example.backend.api.DTO.ItemImage.ItemImageMapper;
import com.example.backend.api.Exceptions.ItemNotFoundException;
import com.example.backend.api.Models.Image;
import com.example.backend.api.Models.Item;
import com.example.backend.api.Models.ItemImage;
import com.example.backend.api.Repositories.ImageRepository;
import com.example.backend.api.Repositories.ItemImageRepository;
import com.example.backend.api.Repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ImageRepository imageRepository;

    public List<ItemImageDto> getAll(){
        return itemImageRepository.findAll()
                .stream()
                .map(itemImageMapper::toDto)
                .toList();
    }

    public ItemImageDto upload(MultipartFile file,UUID itemId) throws IOException {
        System.out.println("Uploading image...");
        Item item = itemRepository.findById(itemId)
                .orElseThrow(()->new ItemNotFoundException(itemId));
        System.out.println("itemId: "+item.getId());
        Image image = Image.builder()
                .name(file.getOriginalFilename())
                .contentType(file.getContentType())
                .imageData(file.getBytes())
                .build();
        imageRepository.save(image);

        ItemImage itemImage = itemImageRepository.save(ItemImage.builder()
                        .image(image)
                        .item(item)
                        .build());
        return itemImageMapper.toDto(itemImage);
    }
    public List<ItemImageDto> getItemImageList(UUID itemId){
        Item item = itemRepository.findById(itemId).orElseThrow(()->new ItemNotFoundException(itemId));
          return itemImageRepository.findByItemId(itemId)
                  .stream()
                  .map(itemImageMapper::toDto)
                  .toList();
    }
}
