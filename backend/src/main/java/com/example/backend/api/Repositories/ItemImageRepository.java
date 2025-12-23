package com.example.backend.api.Repositories;

import com.example.backend.api.Models.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ItemImageRepository extends JpaRepository<ItemImage, UUID> {
    Optional<ItemImage> findByItemId(UUID itemId);
}
