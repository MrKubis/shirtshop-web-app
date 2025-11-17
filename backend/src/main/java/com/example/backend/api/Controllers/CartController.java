package com.example.backend.api.Controllers;

import com.example.backend.api.DTO.CartUserIdDTO;
import com.example.backend.api.Models.Cart;
import com.example.backend.api.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<List<Cart>> getCarts(){
        return cartService.getCarts();
    }
    @GetMapping(params = "user_id")
    public ResponseEntity<List<Cart>> getCartsByUserId(@RequestParam UUID user_id){
        return cartService.getCartsByUserId(user_id);
    }
    @PostMapping("/userid")
    public ResponseEntity<Cart> createCart(@RequestBody CartUserIdDTO cartUserIdDTO){
        return cartService.createCartWithUserId(cartUserIdDTO);
    }
    @PostMapping
    public ResponseEntity<Cart> createCart(Authentication authentication){
        return cartService.createCart(authentication);
    }
    @PutMapping
    public ResponseEntity<Cart> editCart(@RequestBody Cart cart){
        return cartService.editCart(cart);
    }
    @PutMapping(value = "/add/{itemInstanceId}")
    public ResponseEntity<Cart> addToCart(@RequestParam UUID itemInstanceId , Authentication authentication){
        return cartService.addToCart(itemInstanceId, authentication);
    }
}
