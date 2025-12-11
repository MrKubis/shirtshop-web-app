package com.example.backend.api.Exceptions;

import java.util.UUID;

public class UserCartNotFoundException extends RuntimeException {
    public UserCartNotFoundException(UUID id) {

        super("Cart with user id: "+ id + " was not found");
    }
}
