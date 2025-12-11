package com.example.backend.api.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue
    private UUID id;
    @ElementCollection
    @CollectionTable(name="cart_items",joinColumns = @JoinColumn(name = "cart_id"))
    @Column(name="list_item_instance_id")
    private List<UUID> itemInstanceIdList;
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "expired_at")
    private  LocalDateTime expiredAt;



    public UUID getUser_id() {
        return userId;
    }

    public void setUser_id(UUID user_id) {
        this.userId = user_id;
    }

    public LocalDateTime getCreated_at() {
        return createdAt;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.createdAt = created_at;
    }

    public LocalDateTime getExpired_at() {
        return expiredAt;
    }

    public void setExpired_at(LocalDateTime expired_at) {
        this.expiredAt = expired_at;
    }

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.expiredAt = this.createdAt.plusHours(1);
    }
}
