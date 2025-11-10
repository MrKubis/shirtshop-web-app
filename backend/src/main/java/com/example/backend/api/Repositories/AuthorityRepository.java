package com.example.backend.api.Repositories;

import com.example.backend.api.Models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthorityRepository extends JpaRepository<Authority, UUID> {
    Optional<Authority> findByName (String name);
}
