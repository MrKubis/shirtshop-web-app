package com.example.backend.api.Controllers;

import com.example.backend.api.Models.Item;
import com.example.backend.api.Repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;
    @GetMapping
    public ResponseEntity<List<Item>> getItems(){
        if(itemRepository.findAll().isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            return new ResponseEntity<List<Item>>(itemRepository.findAll(), HttpStatus.OK);
        }
    }
    @GetMapping(params = "type")
    public ResponseEntity<List<Item>> getItemsByType(@RequestParam String type){
        Item item = new Item();
        item.setType(type);
        Example<Item> example = Example.of(item);
        Optional<List<Item>> optional = Optional.of(itemRepository.findAll(example));
        if(optional.get().isEmpty())
            return ResponseEntity.notFound().build();
        else return  new ResponseEntity<List<Item>>(itemRepository.findAll(example),HttpStatus.OK);
    }
    @GetMapping(params = "id")
    public ResponseEntity<Item> getItemById(@RequestParam UUID id)
    {
        Optional<Item> optional = (itemRepository.findById(id));
        if(optional.isPresent()) return new ResponseEntity<Item>(optional.get(),HttpStatus.OK);
        else return ResponseEntity.notFound().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item){
        return new ResponseEntity<Item>(itemRepository.save(item),HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<Item> editItem(@RequestBody Item item){
        Optional<Item> optional = itemRepository.findById(item.getId());
        if(optional.isPresent()){
            Item newitem = optional.get();
            newitem.setType(item.getType());
            newitem.setCreated_at(item.getCreated_at());
            newitem.setName(item.getName());
            itemRepository.save(newitem);
            return new ResponseEntity<Item>(newitem,HttpStatus.OK);
        }
        else return ResponseEntity.notFound().build();
    }


}
