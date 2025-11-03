package com.example.backend.api.auth;

import com.example.backend.service.TokenService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final TokenService tokenService;

    public AuthController(TokenService tokenService){
        this.tokenService = tokenService;
    }
    @PostMapping("/token")
    public ResponseEntity<Token> getToken(Authentication authentication){
        Token token = tokenService.generateToken(authentication);
        LOGGER.debug("Token requested for user: {}", authentication.getName());
        return new ResponseEntity<Token>(token, HttpStatus.CREATED);
    }
}
