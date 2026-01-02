package com.example.backend.api.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Entity
@Data
@Table(name = "orders")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @GeneratedValue
    @Id
    private UUID id;
    @Column
    @Pattern(regexp = "^\\+48\\d{9}$", message = "A phone number must start with +48 and contain 9 digits")
    private String phoneNumber;
    @Email
    @Column
    private String email;
    @OneToMany(mappedBy = "order",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private List<OrderItem> items;
    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime created_at;
}
