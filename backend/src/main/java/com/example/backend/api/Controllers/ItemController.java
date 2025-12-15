package com.example.backend.api.Controllers;

import com.example.backend.api.DTO.Item.ItemDto;
import com.example.backend.api.DTO.Item.PatchItemDto;
import com.example.backend.api.DTO.Item.PostItemDto;
import com.example.backend.api.Models.Item;
import com.example.backend.api.Repositories.ItemRepository;
import com.example.backend.api.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Patch;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @GetMapping
    public List<ItemDto> getAll(){
        return itemService.getAll();
    }
    @GetMapping(value = "/type/{type}")
    public List<ItemDto> getByType(@PathVariable String type){
        return itemService.getByType(type);
    }
    @GetMapping(value = "/id/{id}")
    public ItemDto getById(@PathVariable UUID id)
    {
        return itemService.getById(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ItemDto create(@RequestBody PostItemDto dto){
        return itemService.create(dto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "/{id}")
    public ItemDto patch(@PathVariable UUID id,@RequestBody PatchItemDto dto){
        return itemService.patch(id, dto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable UUID id){
        itemService.delete(id);
    }

}
