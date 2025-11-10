package com.example.backend.api.Models;

import java.time.Instant;

public class Token {
    //TODO ROLES
    private String token;
    private Instant created_at;
    private Instant expired_at;
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public Instant getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(Instant expired_at) {
        this.expired_at = expired_at;
    }

    public Token(String token, Instant created_at, Instant expired_at) {
        this.token = token;
        this.created_at = created_at;
        this.expired_at = expired_at;
    }
}
