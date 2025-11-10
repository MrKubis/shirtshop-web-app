package com.example.backend.api.Services;

import com.example.backend.api.DTO.RegisterUserDTO;
import com.example.backend.api.Models.Role;
import com.example.backend.api.Models.User;
import com.example.backend.api.Repositories.AuthorityRepository;
import com.example.backend.api.Repositories.RoleRepository;
import com.example.backend.api.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean existsByUserName(RegisterUserDTO dto){
        return userRepository.findByUserName(dto.userName()).isPresent();
    }
    public boolean existsByEmail(RegisterUserDTO dto){
        return userRepository.findByEmail(dto.email()).isPresent();
    }

    public void register(RegisterUserDTO dto){
        User user = new User();
        user.setUserName(dto.userName());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));

        //DEFAULT ROLE

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("USER role not found"));
        user.getRoles().add(userRole);
        userRepository.save(user);
    }

    public void delete(User user){

    }
}
