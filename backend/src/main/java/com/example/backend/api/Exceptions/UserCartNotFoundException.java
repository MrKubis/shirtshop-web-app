package com.example.backend.api.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserCartNotFoundException extends RuntimeException {
    public UserCartNotFoundException(UUID id) {
        super("Cart with user id: "+ id + " was not found");
    }
}
