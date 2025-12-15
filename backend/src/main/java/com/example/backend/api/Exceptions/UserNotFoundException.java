package com.example.backend.api.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException( ) {
        super(String.format("User not found"));
    }
    public UserNotFoundException(UUID id){
        super(String.format("User with id: " + id + " not found"));
    }
}
