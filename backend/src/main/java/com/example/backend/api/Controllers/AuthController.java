package com.example.backend.api.Controllers;

import com.example.backend.api.DTO.UserRoleDTO;
import com.example.backend.api.Models.Token;
import com.example.backend.api.Principals.UserPrincipal;
import com.example.backend.api.Services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.TemporalUnit;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @PostMapping("/token")
    public ResponseEntity<Token> getToken(Authentication authentication){
        Token token = authService.generateToken(authentication);
        return new ResponseEntity<Token>(token, HttpStatus.CREATED);
    }

    @PostMapping("/userrole")
    public ResponseEntity<?> appendRoleToUser(UserRoleDTO dto){
        if (authService.appendRoleToUser(dto)){
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/public-token")
    public ResponseEntity<?> getPublicToken(HttpServletResponse response){
        Token token = authService.generatePublicToken();

        Cookie cookie = new Cookie("token",token.getToken());
        cookie.setPath("/");
        cookie.setSecure(true); //DEV ONLY
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*30);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

}
