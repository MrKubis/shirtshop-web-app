package com.example.backend.api.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemInstanceNotFoundException extends RuntimeException {
    public ItemInstanceNotFoundException(UUID id) {

        super("Item instance with id: [%s]"+ id + " was not found");    }
}
