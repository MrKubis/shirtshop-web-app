package com.example.backend.api.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
@Getter
@Setter
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

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.expiredAt = this.createdAt.plusHours(1);
    }
}
