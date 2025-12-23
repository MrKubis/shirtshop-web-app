package com.example.backend.api.Exceptions;

import java.util.UUID;

public class ItemImageNotFoundException extends RuntimeException {
    public ItemImageNotFoundException(UUID id) {

        super("Item image with id: [%s]"+ id + " was not found");
    }
}
