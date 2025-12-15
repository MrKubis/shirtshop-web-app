package com.example.backend.api.Services;

import com.example.backend.api.DTO.ItemInstance.ItemInstanceDto;
import com.example.backend.api.DTO.ItemInstance.ItemInstanceMapper;
import com.example.backend.api.DTO.ItemInstance.PatchItemInstanceDto;
import com.example.backend.api.DTO.ItemInstance.PostItemInstanceDto;
import com.example.backend.api.Exceptions.ItemInstanceNotFoundException;
import com.example.backend.api.Exceptions.ItemNotFoundException;
import com.example.backend.api.Repositories.ItemInstanceRepository;
import com.example.backend.api.Repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemInstanceService {
    @Autowired
    private ItemInstanceRepository itemInstanceRepository;
    @Autowired
    private ItemInstanceMapper itemInstanceMapper;
    @Autowired
    private ItemRepository itemRepository;

    public List<ItemInstanceDto> getAll() {
        return itemInstanceRepository.findAll()
                .stream()
                .map(itemInstanceMapper::toDto)
                .toList();
    }
    public ItemInstanceDto getById(UUID id) {
        return itemInstanceRepository.findById(id)
                .map(itemInstanceMapper::toDto)
                .orElseThrow(()-> new ItemInstanceNotFoundException(id));
    }

    public ItemInstanceDto create(PostItemInstanceDto dto) {
        itemRepository.findById(dto.item_id()).orElseThrow(()-> new ItemNotFoundException(dto.item_id()));

        return itemInstanceMapper.toDto(itemInstanceRepository.save(itemInstanceMapper.fromPostDto(dto)));
    }

    public ItemInstanceDto edit(UUID id, PatchItemInstanceDto dto) {
        return itemInstanceRepository.findById(id)
                .map((entity)-> itemInstanceMapper.fromPatchDto(entity,dto))
                .map(itemInstanceRepository::save)
                .map(itemInstanceMapper::toDto)
                .orElseThrow(()-> new ItemInstanceNotFoundException(id));
    }

    public void delete(UUID id) {
        itemInstanceRepository.deleteById(id);
    }
}
