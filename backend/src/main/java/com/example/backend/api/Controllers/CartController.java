package com.example.backend.api.Controllers;

import com.example.backend.api.Models.Cart;
import com.example.backend.api.Repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @GetMapping
    public ResponseEntity<List<Cart>> getCarts(){
        if(cartRepository.findAll().isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        else return new ResponseEntity<List<Cart>>(cartRepository.findAll(), HttpStatus.OK);
    }
    @GetMapping(params = "user_id")
    public ResponseEntity<List<Cart>> getCartsByUserId(@RequestParam UUID user_id){
        Cart cart = new Cart();
        cart.setUser_id(user_id);
        Example<Cart> example = Example.of(cart);
        Optional<List<Cart>> optional = Optional.of(cartRepository.findAll(example));
        if(optional.isEmpty())
            return ResponseEntity.notFound().build();
        else return new ResponseEntity<List<Cart>>(cartRepository.findAll(example),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart){
        return new ResponseEntity<Cart>(cartRepository.save(cart),HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Cart> editItem(@RequestBody Cart cart){
        Optional<Cart> optional = cartRepository.findById(cart.getId());
        if(optional.isPresent()){
            Cart newcart = optional.get();
            newcart.setUser_id(cart.getUser_id());
            newcart.setCreated_at(cart.getCreated_at());
            newcart.setExpired_at(cart.getCreated_at().plusHours(1));
            newcart.setItemInstanceIdList(cart.getItemInstanceIdList());
            cartRepository.save(newcart);
            return new ResponseEntity<Cart>(newcart, HttpStatus.OK);
        }
        else return ResponseEntity.notFound().build();
    }
    //TODO ADD TO CART
}
