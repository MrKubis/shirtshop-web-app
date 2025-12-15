package com.example.backend.api.Services;

import com.example.backend.api.DTO.cart.CartDto;
import com.example.backend.api.DTO.cart.CartMapper;
import com.example.backend.api.DTO.cart.PatchCartDto;
import com.example.backend.api.Exceptions.*;
import com.example.backend.api.Models.Cart;
import com.example.backend.api.Models.ItemInstance;
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
                .map(cartMapper::toDto)
                .toList();
    }
    public CartDto getByAuthentication(Authentication authentication){

        UUID userId = getUserIdFromAuthentication(authentication);

        return cartRepository.findByUserId(userId)
                .map(cartMapper::toDto)
                .orElseThrow(()-> new UserCartNotFoundException(userId));
    }

    public CartDto getByUserId(UUID user_id) {
        return cartRepository.findByUserId(user_id)
                .map(cartMapper::toDto)
                .orElseThrow(() -> new UserCartNotFoundException(user_id));
    }

    public CartDto create(Authentication authentication) {

        UUID userId = getUserIdFromAuthentication(authentication);
        Cart cart = Cart.builder()
            .userId(userId)
            .createdAt(LocalDateTime.now())
            .expiredAt(LocalDateTime.now().plusDays(1))
            .itemInstanceIdList(new ArrayList<>())
            .build();
        return cartMapper.toDto(cartRepository.save(cart));
        }

    public CartDto patch(UUID id,PatchCartDto dto){
            return cartRepository.findById(id)
                    .map((entity) -> cartMapper.fromPatchDto(entity,dto))
                    .map(cartRepository::save)
                    .map(cartMapper::toDto)
                    .orElseThrow(()-> new CartNotFoundException(id));
        }


    public CartDto addToCart(UUID itemInstanceId, Authentication authentication){

        UUID userId = getUserIdFromAuthentication(authentication);

        //ZMIANA STATUSU
        ItemInstance itemInstance = itemInstanceRepository.findById(itemInstanceId).orElseThrow(()-> new ItemInstanceNotFoundException(itemInstanceId));

        if(itemInstance.getStatus().equals("UNAVAILABLE")) throw new ItemInstanceUnAvailableException(itemInstance.getId());
        itemInstance.setStatus("UNAVAILABLE");
        itemInstanceRepository.save(itemInstance);

        return  cartRepository.findByUserId(userId)
                .map((entity)->cartMapper.addItem(entity,itemInstanceId))
                .map(cartRepository::save)
                .map(cartMapper::toDto)
                .orElseThrow(()->new UserCartNotFoundException(userId));
    }

    public CartDto removeFromCart(UUID itemInstanceId, Authentication authentication) {

        UUID userId = getUserIdFromAuthentication(authentication);
        return cartRepository.findByUserId(userId)
                .map((entity)->cartMapper.removeItem(entity,itemInstanceId))
                .map(cartRepository::save)
                .map(cartMapper::toDto)
                .orElseThrow(()-> new UserCartNotFoundException(userId));
    }

    public void delete(UUID id) {
        Cart cart = cartRepository.findById(id).orElseThrow(()-> new CartNotFoundException(id));

        for (UUID itemInstanceId : cart.getItemInstanceIdList()) {
            Optional<ItemInstance> itemInstance = itemInstanceRepository.findById(itemInstanceId);
            itemInstance.ifPresent(instance -> {
                instance.setStatus("AVAILABLE");
                itemInstanceRepository.save(instance);
            });
            
            cartRepository.deleteById(id);
        }
    }
    UUID getUserIdFromAuthentication(Authentication authentication){
        Object principal = authentication.getPrincipal();
        UUID userId;
        if (principal instanceof UserPrincipal userPrincipal) {
            if (!userPrincipal.isEnabled())
                throw new UserNotFoundException();
            return userPrincipal.getId();
        }
        else if (principal instanceof Jwt jwt) {
            String userIdStr = jwt.getClaimAsString("userId");
            if (userIdStr.isEmpty())
                throw new UserNotFoundException();
            return UUID.fromString(userIdStr);
        }
        else throw new UserNotFoundException();
    }
}

