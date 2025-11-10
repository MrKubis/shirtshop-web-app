package com.example.backend.api.Repositories;

import com.example.backend.api.Models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
}
