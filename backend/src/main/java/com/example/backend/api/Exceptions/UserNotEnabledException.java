package com.example.backend.api.Exceptions;

import java.util.UUID;

public class UserNotEnabledException extends RuntimeException {
    public UserNotEnabledException(UUID id) {
        super(String.format("User with id: ", id, "is not enabled"));
    }
}
