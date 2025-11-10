package com.example.backend.api.Models;

import jakarta.persistence.*;

import java.util.UUID;

@Table
@Entity(name = "item_instances")
public class ItemInstance {
    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private UUID item_id;

    @Column
    private double price;
    //AVAILABLE, RESERVED, UNAVAILABLE
    @Column
    private String status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getItem_id() {
        return item_id;
    }

    public void setItem_id(UUID item_id) {
        this.item_id = item_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ItemInstance(){}
    public ItemInstance(UUID item_id,double price,String status){
        this.item_id = item_id;
        this.price = price;
        this.status = status;
    }
}
