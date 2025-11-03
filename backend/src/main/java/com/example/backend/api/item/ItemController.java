package com.example.backend.api.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item){
        return new ResponseEntity<Item>(itemRepository.save(item),HttpStatus.CREATED);
    }

}
