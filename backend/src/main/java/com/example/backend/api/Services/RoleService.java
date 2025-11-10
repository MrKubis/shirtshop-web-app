package com.example.backend.api.Services;

import com.example.backend.api.DTO.CreateRoleDTO;
import com.example.backend.api.Models.Role;
import com.example.backend.api.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    public ResponseEntity<?> createRole(CreateRoleDTO dto){
        if(roleRepository.findByName(dto.roleName()).isPresent())
            return ResponseEntity.badRequest().body("Role already has been taken");
        Role role = new Role(dto.roleName());
        return  new ResponseEntity<Role>(roleRepository.save(role),HttpStatus.CREATED);
    }
    public ResponseEntity<List<Role>> getRoles(){
        if (roleRepository.findAll().isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<List<Role>>(roleRepository.findAll(), HttpStatus.OK);
    }
}
