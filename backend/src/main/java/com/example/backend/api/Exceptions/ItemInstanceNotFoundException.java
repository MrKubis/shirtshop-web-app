package com.example.backend.api.Exceptions;

import java.util.UUID;

public class ItemInstanceNotFoundException extends RuntimeException {
    public ItemInstanceNotFoundException(UUID id) {

        super("Item instance with id: [%s]"+ id + " was not found");    }
}
