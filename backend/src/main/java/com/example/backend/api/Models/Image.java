package com.example.backend.api.Models;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.UUID;

@Builder
@Entity
@Table(name = "images")
@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue
    private UUID id;
    @Lob
    private byte[] imageData;
    @Column
    private String name;
    @Column
    private String contentType;
}
