package com.example.backend.api.Repositories;

import com.example.backend.api.Models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByUserId(UUID UserId);
    void deleteByExpiredAtBefore(LocalDateTime date);
    List<Cart> getAllByExpiredAtBefore(LocalDateTime date);
}
