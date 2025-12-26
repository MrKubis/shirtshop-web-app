package com.example.backend.api.Exceptions;

import java.util.UUID;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(UUID id) {

        super(String.format("Image with id:", id, " not found"));
    }
}
