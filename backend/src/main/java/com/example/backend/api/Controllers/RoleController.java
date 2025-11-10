package com.example.backend.api.Controllers;

import com.example.backend.api.DTO.CreateRoleDTO;
import com.example.backend.api.Models.Role;
import com.example.backend.api.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody CreateRoleDTO dto){
        return createRole(dto);
    }
    @GetMapping
    public ResponseEntity<List<Role>> getRoles(){
        return roleService.getRoles();
    }
}
