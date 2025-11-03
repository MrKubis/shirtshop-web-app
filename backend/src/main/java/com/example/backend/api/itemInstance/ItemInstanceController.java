package com.example.backend.api.itemInstance;

import com.example.backend.api.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @PutMapping
    public ResponseEntity<ItemInstance> editItemInstance(@RequestBody ItemInstance itemInstance){
        Optional<ItemInstance> optional = itemInstanceRepository.findById(itemInstance.getId());
        if (optional.isPresent())
        {
            ItemInstance newiteminstance = optional.get();
            newiteminstance.setItem_id(itemInstance.getItem_id());
            newiteminstance.setPrice(itemInstance.getPrice());
            newiteminstance.setStatus(itemInstance.getStatus());
            itemInstanceRepository.save(newiteminstance);
            return new ResponseEntity<ItemInstance>(newiteminstance,HttpStatus.OK);
        }
        else
            return ResponseEntity.notFound().build();
    }
    
}
