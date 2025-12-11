package com.example.backend.api.Exceptions;

import java.util.UUID;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(UUID id) {

        super("Cart with id: [%s]"+ id + " was not found");
    }
}
