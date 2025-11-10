package com.example.backend.api.Models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue
    private UUID id;
    @Column
    private String name;
    @Column
    private String type;
    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime created_at;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Item(){}
    public Item(String name, String type){
        this.name = name;
        this.type = type;
    }
}
