package com.example.backend.api.Services;

import com.example.backend.api.DTO.cart.CartDto;
import com.example.backend.api.DTO.cart.CartMapper;
import com.example.backend.api.DTO.cart.PatchCartDto;
import com.example.backend.api.Exceptions.*;
import com.example.backend.api.Models.Cart;
import com.example.backend.api.Principals.UserPrincipal;
import com.example.backend.api.Repositories.CartRepository;
import com.example.backend.api.Repositories.ItemInstanceRepository;
import com.example.backend.api.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemInstanceRepository itemInstanceRepository;
    @Autowired
    private CartMapper cartMapper;

    public List<CartDto> get(){
        return cartRepository.findAll()
                .stream()
                .map(cartMapper::toCartDto)
                .toList();
    }
    public CartDto getByAuthentication(Authentication authentication){
        Object principal = authentication.getPrincipal();
        UUID userId;

        if (principal instanceof UserPrincipal userPrincipal) {
            if (!userPrincipal.isEnabled())
                throw new UserNotFoundException();
            userId = userPrincipal.getId();
        }
        else if (principal instanceof Jwt jwt) {
            String userIdStr = jwt.getClaimAsString("userId");
            if (userIdStr.isEmpty())
                throw new UserNotFoundException();
            userId = UUID.fromString(userIdStr);
        } else {
            userId = null;
        }
        if(userId == null) throw new UserNotFoundException();

        return cartRepository.findByUserId(userId)
                .map(cartMapper::toCartDto)
                .orElseThrow(()-> new UserCartNotFoundException(userId));
    }

    public CartDto getByUserId(UUID user_id) {
        return cartRepository.findByUserId(user_id)
                .map(cartMapper::toCartDto)
                .orElseThrow(() -> new UserCartNotFoundException(user_id));
    }

    public CartDto create(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        UUID userId = null;

        if (principal instanceof UserPrincipal userPrincipal) {
            if (!userPrincipal.isEnabled())
                throw new UserNotFoundException();
            userId = userPrincipal.getId();
        }
        else if (principal instanceof Jwt jwt) {
            String userIdStr = jwt.getClaimAsString("userId");
            if (userIdStr.isEmpty())
                throw new UserNotFoundException();
            userId = UUID.fromString(userIdStr);
        }
    Cart cart = Cart.builder()
            .userId(userId)
            .createdAt(LocalDateTime.now())
            .expiredAt(LocalDateTime.now().plusDays(1))
            .itemInstanceIdList(new ArrayList<>())
            .build();
        return cartMapper.toCartDto(cartRepository.save(cart));
        }

    public CartDto patch(UUID id,PatchCartDto dto){
            return cartRepository.findById(id)
                    .map((entity) -> cartMapper.fromPatchDto(entity,dto))
                    .map(cartRepository::save)
                    .map(cartMapper::toCartDto)
                    .orElseThrow(()-> new CartNotFoundException(id));
        }


    public CartDto addToCart(UUID itemInstanceId, Authentication authentication){
        Object principal = authentication.getPrincipal();
        UUID userId;
        if (principal instanceof UserPrincipal userPrincipal) {
            if (!userPrincipal.isEnabled())
                throw new UserNotFoundException();
            userId = userPrincipal.getId();
        }
        else if (principal instanceof Jwt jwt) {
            String userIdStr = jwt.getClaimAsString("userId");
            if (userIdStr.isEmpty())
                throw new UserNotFoundException();
            userId = UUID.fromString(userIdStr);
        }
        else throw new UserNotFoundException();
        System.out.println(itemInstanceId);

        itemInstanceRepository.findById(itemInstanceId).orElseThrow(()-> new ItemInstanceNotFoundException(itemInstanceId));

        return  cartRepository.findByUserId(userId)
                .map((entity)->cartMapper.addItem(entity,itemInstanceId))
                .map(cartRepository::save)
                .map(cartMapper::toCartDto)
                .orElseThrow(()->new UserCartNotFoundException(userId));
    }

}

