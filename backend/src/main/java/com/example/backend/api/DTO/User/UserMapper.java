package com.example.backend.api.DTO.User;

import com.example.backend.api.Models.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class UserMapper {

    public UserDto toDto(final User entity){
        return UserDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .roles(entity.getRoles())
                .created_at(entity.getCreated_at())
                .build();
    }
    //???? nw co tu dać jeszcze
    /*
    public User fromPatchDto (final User entity, final PatchUserDto dto){
        return User.builder()
                .id(entity.getId())
                .password(entity.getPassword())

                .build();
    }
     */
    public User fromPostDto(final PostUserDto dto){
        return User.builder()
                .password(dto.password())
                .userName(dto.username())
                .created_at(LocalDateTime.now())
                .build();
    }
}
