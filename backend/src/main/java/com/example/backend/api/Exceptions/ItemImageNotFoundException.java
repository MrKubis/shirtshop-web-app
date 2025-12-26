package com.example.backend.api.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemImageNotFoundException extends RuntimeException {
    public ItemImageNotFoundException(UUID id) {

        super("Item image with id: [%s]"+ id + " was not found");
    }
}
