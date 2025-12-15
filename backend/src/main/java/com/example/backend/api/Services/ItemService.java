package com.example.backend.api.Services;

import com.example.backend.api.DTO.Item.ItemDto;
import com.example.backend.api.DTO.Item.ItemMapper;
import com.example.backend.api.DTO.Item.PatchItemDto;
import com.example.backend.api.DTO.Item.PostItemDto;
import com.example.backend.api.Exceptions.ItemNotFoundException;
import com.example.backend.api.Repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemMapper itemMapper;

    public List<ItemDto> getAll() {
        return itemRepository.findAll()
                .stream()
                .map(itemMapper::toDto)
                .toList();
    }

    public List<ItemDto> getByType(String type) {
        return itemRepository.findByType(type)
                .stream()
                .map(itemMapper::toDto)
                .toList();
    }

    public ItemDto getById(UUID id) {
        return itemRepository.findById(id)
                .map(itemMapper::toDto)
                .orElseThrow(()->new ItemNotFoundException(id));
    }

    public ItemDto create(PostItemDto dto) {
        return itemMapper.toDto(itemRepository.save(itemMapper.fromPostDto(dto)));
    }
    public ItemDto patch(UUID id,PatchItemDto dto) {
        return itemRepository.findById(id)
                .map((entity)->itemMapper.fromPatchDto(entity,dto))
                .map(itemRepository::save)
                .map(itemMapper::toDto)
                .orElseThrow(()-> new ItemNotFoundException(id));
    }
    public void delete(UUID id){
        itemRepository.deleteById(id);
    }
}
