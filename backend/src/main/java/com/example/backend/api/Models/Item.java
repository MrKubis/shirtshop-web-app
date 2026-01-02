package com.example.backend.api.Models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@Data
@Table(name = "items")
@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue
    private UUID id;
    @Column
    private String name;
    @Column
    private String type;
    @Column
    private String description;
    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime created_at;
}
