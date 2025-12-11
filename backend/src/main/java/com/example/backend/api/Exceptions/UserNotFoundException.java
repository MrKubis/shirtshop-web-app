package com.example.backend.api.Exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException( ) {
        super(String.format("User not found"));
    }
}
