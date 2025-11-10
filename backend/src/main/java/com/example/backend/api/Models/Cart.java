package com.example.backend.api.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
    private UUID user_id;
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "expired_at")
    private  LocalDateTime expired_at;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<UUID> getItemInstanceIdList() {
        return itemInstanceIdList;
    }

    public void setItemInstanceIdList(List<UUID> itemInstanceIdList) {
        this.itemInstanceIdList = itemInstanceIdList;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(LocalDateTime expired_at) {
        this.expired_at = expired_at;
    }

    @PrePersist
    public void onCreate(){
        this.created_at = LocalDateTime.now();
        this.expired_at = this.created_at.plusHours(1);
    }
    public Cart(){}
    public Cart(List<UUID> itemInstanceIdList, UUID user_id) {
        this.itemInstanceIdList = itemInstanceIdList;
        this.user_id = user_id;
        this.expired_at = getCreated_at().plusHours(1);
    }
}
