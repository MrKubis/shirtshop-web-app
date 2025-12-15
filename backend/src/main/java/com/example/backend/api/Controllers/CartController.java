package com.example.backend.api.Controllers;

import com.example.backend.api.DTO.cart.CartDto;
import com.example.backend.api.DTO.cart.PatchCartDto;
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
    public List<CartDto> getAll(){
        return cartService.get();
    }
    @GetMapping("/user")
    public CartDto getByAuthentication(Authentication authentication){
        return cartService.getByAuthentication(authentication);
    }
    @PostMapping
    public CartDto post(Authentication authentication){
        return cartService.create(authentication);
    }
    @PatchMapping("/id/{id}")
    public CartDto patch(@PathVariable UUID id,@RequestBody PatchCartDto dto){
        return cartService.patch(id, dto);
    }
    @PatchMapping(value = "/add/{itemInstanceId}")
    public CartDto addToCart(@PathVariable UUID itemInstanceId , Authentication authentication){
        return cartService.addToCart(itemInstanceId, authentication);
    }
    @PatchMapping(value = "/remove/{itemInstanceId}")
    public CartDto removeFromCart(@PathVariable UUID itemInstanceId, Authentication authentication){
        return cartService.removeFromCart(itemInstanceId, authentication);
    }
    @DeleteMapping("/{id}")
    public void removeFromCart(@PathVariable UUID id)
    {
        cartService.delete(id);
    }
}
