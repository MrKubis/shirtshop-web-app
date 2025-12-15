package com.example.backend.api.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserNotEnabledException extends RuntimeException {
    public UserNotEnabledException(UUID id) {
        super(String.format("User with id: ", id, "is not enabled"));
    }
}
