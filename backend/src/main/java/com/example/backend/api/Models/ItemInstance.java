package com.example.backend.api.Models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.UUID;

@Builder
@Entity
@Table(name = "items")
@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class  ItemInstance {
    @Id
    @GeneratedValue
    private UUID id;
    @Column
    private UUID item_id;
    @Column
    private Double price;
    //AVAILABLE, RESERVED, UNAVAILABLE
    @Column
    private String status;
}
