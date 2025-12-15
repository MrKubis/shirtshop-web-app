package com.example.backend.api.Controllers;

import com.example.backend.api.DTO.RegisterUserDTO;
import com.example.backend.api.DTO.User.PatchUserDto;
import com.example.backend.api.DTO.User.UserDto;
import com.example.backend.api.Models.User;
import com.example.backend.api.Repositories.UserRepository;
import com.example.backend.api.Services.UserService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> getAll(){
        return userService.getAll();
    }
    @GetMapping("/{id}")
    public UserDto getById(@PathVariable UUID id){
        return userService.getById(id);
    }
    @PostMapping("/register")
    public UserDto registerUser(@RequestBody RegisterUserDTO dto){
        return userService.register(dto);
    }
    @DeleteMapping
    public void deleteUser(@PathVariable UUID id){
         userService.delete(id);
    }
    /*
    @PatchMapping("/{id}")
    public User edit(@PathVariable UUID id,@RequestBody PatchUserDto dto){
        return userService.edit(id,dto);
    }
*/
}

