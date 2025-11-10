package com.example.backend.api.Controllers;

import com.example.backend.api.DTO.RegisterUserDTO;
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
    public ResponseEntity<List<User>> getUsers(){
    if (userRepository.findAll().isEmpty()){
        return ResponseEntity.notFound().build();
        }
    return new ResponseEntity<List<User>>(userRepository.findAll(), HttpStatus.OK);
    }
    @GetMapping(params = "id")
    public ResponseEntity<User> getUserById(@RequestParam UUID id){
        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent()){
            return new ResponseEntity<User>((User) optional.get(),HttpStatus.OK);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/register")

    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDTO dto){
        if(userService.existsByUserName(dto)) {
            return ResponseEntity.badRequest().body("Username taken");
        }
        if(userService.existsByEmail(dto)){
            return  ResponseEntity.badRequest().body("Email is taken");
        }
        userService.register(dto);
        return new ResponseEntity<RegisterUserDTO>(dto,HttpStatus.CREATED);
    }
    @DeleteMapping(params = "id")
    public ResponseEntity<Void> deleteUser(@RequestParam UUID id){
        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent()){
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping
    public ResponseEntity<User> edit(@RequestBody User user){
        Optional<User> optional = userRepository.findById(user.getId());
        if(optional.isPresent())
        {
            User newuser = optional.get();
            newuser.setCreated_at(user.getCreated_at());
            newuser.setEmail(user.getEmail());
            newuser.setPassword(user.getPassword());
            userRepository.save(newuser);
            return new ResponseEntity<User>(newuser,HttpStatus.OK);
        }
        else return ResponseEntity.notFound().build();
    }
}
