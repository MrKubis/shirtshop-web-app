package com.example.backend.api.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ItemInstanceUnAvailableException extends RuntimeException {
    public ItemInstanceUnAvailableException(UUID id) {
        super(String.format("Item instance with id: ",id , "is unavailable"));
    }
}
