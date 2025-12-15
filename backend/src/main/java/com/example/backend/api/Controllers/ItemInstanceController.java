package com.example.backend.api.Controllers;

import com.example.backend.api.DTO.ItemInstance.ItemInstanceDto;
import com.example.backend.api.DTO.ItemInstance.PatchItemInstanceDto;
import com.example.backend.api.DTO.ItemInstance.PostItemInstanceDto;
import com.example.backend.api.Services.ItemInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/iteminstances")
public class ItemInstanceController {

    @Autowired
    private ItemInstanceService itemInstanceService;

    @GetMapping
    public List<ItemInstanceDto> getAll(){
        return itemInstanceService.getAll();
    }
    @GetMapping("/id/{id}")
    public ItemInstanceDto getById(UUID id){
        return itemInstanceService.getById(id);
    }
    @PostMapping
    public ItemInstanceDto create(@RequestBody PostItemInstanceDto dto){
        return itemInstanceService.create(dto);
    }
    @PatchMapping("/{id}")
    public ItemInstanceDto edit(@PathVariable UUID id,@RequestBody PatchItemInstanceDto dto){
        return itemInstanceService.edit(id, dto);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        itemInstanceService.delete(id);
    }
    
}
