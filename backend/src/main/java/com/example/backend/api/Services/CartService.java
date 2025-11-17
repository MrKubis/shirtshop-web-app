package com.example.backend.api.Services;

import com.example.backend.api.DTO.CartUserIdDTO;
import com.example.backend.api.Models.Cart;
import com.example.backend.api.Models.ItemInstance;
import com.example.backend.api.Principals.UserPrincipal;
import com.example.backend.api.Repositories.CartRepository;
import com.example.backend.api.Repositories.ItemInstanceRepository;
import com.example.backend.api.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemInstanceRepository itemInstanceRepository;
    public ResponseEntity<List<Cart>> getCartsByUserId(UUID user_id){
        Cart cart = new Cart();
        cart.setUser_id(user_id);
        Example<Cart> example = Example.of(cart);
        Optional<List<Cart>> optional = Optional.of(cartRepository.findAll(example));
        if(optional.get().isEmpty())
            return ResponseEntity.notFound().build();
        else return new ResponseEntity<List<Cart>>(cartRepository.findAll(example), HttpStatus.OK);
    }

    public ResponseEntity<List<Cart>> getCarts(){
        if(cartRepository.findAll().isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        else return new ResponseEntity<List<Cart>>(cartRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Cart> createCart(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        UUID userId = null;

        if (principal instanceof UserPrincipal userPrincipal) {
            if (!userPrincipal.isEnabled())
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            userId = userPrincipal.getId();
        } else if (principal instanceof Jwt jwt) {
            String userIdStr = jwt.getClaimAsString("userId");
            if (userIdStr.isEmpty())
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            userId = UUID.fromString(userIdStr);
        }

        Cart cart = new Cart(new ArrayList<>(), userId);
        return new ResponseEntity<Cart>(cartRepository.save(cart), HttpStatus.CREATED);
        }

    public ResponseEntity<Cart> createCartWithUserId(CartUserIdDTO cartUserIdDTO){
        Cart cart = new Cart(new ArrayList<>(),cartUserIdDTO.UserId());
        return new ResponseEntity<Cart>(cartRepository.save(cart),HttpStatus.CREATED);
    }

    public ResponseEntity<Cart> editCart(Cart cart){
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

    public ResponseEntity<Cart> addToCart(UUID itemInstanceId, Authentication authentication){

        checkCart(authentication);

        //CHECK IF ITEMINSTANCE EXISTS AND IS AVAILBLE
        Optional<ItemInstance> optional = itemInstanceRepository.findById(itemInstanceId);
        if(optional.isEmpty())
            return ResponseEntity.notFound().build();
        ItemInstance itemInstance = optional.get();
        if(!Objects.equals(itemInstance.getStatus(), "AVAILABLE"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        //ADDING ITEMINSTANCE
        UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
        Optional<Cart> cartOptional = cartRepository.findByUserId(userPrincipal.getId());
        if(cartOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Cart cart = cartOptional.get();
        cart.getItemInstanceIdList().add(itemInstance.getId());
        itemInstance.setStatus("IN_CART");
        itemInstanceRepository.save(itemInstance);
        return new ResponseEntity<Cart>(cartRepository.save(cart),HttpStatus.OK);
    }

    private void checkCart(Authentication authentication){
        Object principal = authentication.getPrincipal();
        UUID userId = null;

        if (principal instanceof UserPrincipal userPrincipal) {
            userId = userPrincipal.getId();
        }
        else if (principal instanceof Jwt jwt) {
            String userIdStr = jwt.getClaimAsString("userId");
            userId = UUID.fromString(userIdStr);
        }
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
        if(cartOptional.isEmpty())
            createCart(authentication);
    }
}
