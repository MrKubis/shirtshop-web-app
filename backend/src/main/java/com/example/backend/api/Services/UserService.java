package com.example.backend.api.Services;

import com.example.backend.api.DTO.RegisterUserDTO;
import com.example.backend.api.DTO.User.UserDto;
import com.example.backend.api.DTO.User.UserMapper;
import com.example.backend.api.Exceptions.RoleNotFoundException;
import com.example.backend.api.Exceptions.UserNotFoundException;
import com.example.backend.api.Models.Role;
import com.example.backend.api.Models.User;
import com.example.backend.api.Repositories.AuthorityRepository;
import com.example.backend.api.Repositories.RoleRepository;
import com.example.backend.api.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    @Autowired
    private UserMapper userMapper;

    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto getById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(()-> new UserNotFoundException(id));
    }
    public UserDto register(RegisterUserDTO dto){
        User user = new User();
        user.setUserName(dto.userName());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));

        //DEFAULT ROLE

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RoleNotFoundException("USER role not found"));
        user.getRoles().add(userRole);

        return userMapper.toDto(userRepository.save(user));
    }


    public void delete(UUID id){
        userRepository.deleteById(id);
    }
}
