package com.example.backend.api.Services;

import com.example.backend.api.Controllers.AuthController;
import com.example.backend.api.DTO.UserRoleDTO;
import com.example.backend.api.Models.Role;
import com.example.backend.api.Models.Token;
import com.example.backend.api.Models.User;
import com.example.backend.api.Repositories.RoleRepository;
import com.example.backend.api.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final TokenService tokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    public AuthService(TokenService tokenService){
        this.tokenService = tokenService;
    }
    public Token generateToken(Authentication authentication){

        Token token = tokenService.generateToken(authentication);
        LOGGER.debug("Token requested for user: {}", authentication.getName());
        return token;
    }

    public boolean appendRoleToUser(UserRoleDTO dto){
        User user = new User();
        user.setId(dto.user_id());
        Role role = new Role();
        role.setId(dto.role_id());
        if (!userRepository.exists(Example.of(user)) || !roleRepository.exists((Example.of(role))) )
            return false;

        User selectedUser = userRepository.getById(dto.user_id());
        Role selectedRole = roleRepository.getById(dto.role_id());

        selectedUser.getRoles().add(selectedRole);
        userRepository.save(selectedUser);
        return true;
    }
}
