package com.example.backend.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

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
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        return new ResponseEntity<User>(userRepository.save(user),HttpStatus.CREATED);
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
}
