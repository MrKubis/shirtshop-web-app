package com.example.backend.api.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MainItemImageNotFoundException extends RuntimeException {
    public MainItemImageNotFoundException(UUID itemId) {

        super(String.format("No Main Image found for item with id: %s",itemId));
    }
}
