package com.example.backend.api.DTO.cart;

import com.example.backend.api.Models.Cart;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CartMapper {
    public CartDto toCartDto(final Cart entity){
        return CartDto.builder()
                .id(entity.getId())
                .userId(entity.getUser_id())
                .createdAt(entity.getCreated_at())
                .expiredAt(entity.getExpired_at())
                .build();
    }
    public Cart toCart(final CartDto dto){
        return Cart.builder()
                .id(dto.id())
                .userId(dto.userId())
                .createdAt(dto.createdAt())
                .expiredAt(dto.expiredAt())
                .build();
    }
    public Cart fromPatchDto(final Cart entity,final PatchCartDto dto)
    {
        return Cart.builder()
                .userId(dto.userId())
                .createdAt(dto.createdAt())
                .expiredAt(dto.expiredAt())
                .build();
    }
    public Cart fromPostDto(final Cart entity, final PostCartDto dto)
    {
        return Cart.builder()
                .userId(dto.userId())
                .createdAt(dto.createdAt())
                .expiredAt(dto.expiredAt())
                .build();
    }

    public Cart addItem (final Cart entity, final UUID itemInstanceID){
        List<UUID> updatedList = new ArrayList<>();
        if(entity.getItemInstanceIdList() != null){
            updatedList.addAll(entity.getItemInstanceIdList());
        }
        return Cart.builder()
                .id(entity.getId())
                .userId(entity.getUser_id())
                .createdAt(entity.getCreated_at())
                .expiredAt(entity.getExpired_at())
                .itemInstanceIdList(updatedList)
                .build();
    }
}
