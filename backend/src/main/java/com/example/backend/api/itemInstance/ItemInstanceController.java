package com.example.backend.api.itemInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/iteminstances")
public class ItemInstanceController {

    @Autowired
    private ItemInstanceRepository itemInstanceRepository;

    @GetMapping
    public ResponseEntity<List<ItemInstance>> getItemInstances(){
        if(itemInstanceRepository.findAll().isEmpty()){
            return  ResponseEntity.notFound().build();
        }
        else{
            return new ResponseEntity<List<ItemInstance>>(itemInstanceRepository.findAll(), HttpStatus.OK);
        }
    }
    @PostMapping
    public ResponseEntity<ItemInstance> createItemInstance(@RequestBody ItemInstance itemInstance){
        return new ResponseEntity<ItemInstance>(itemInstanceRepository.save(itemInstance),HttpStatus.CREATED);
    }
    
}
