package com.example.backend.api.Services;

import com.example.backend.api.Repositories.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class CartCleanupService {

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    @Scheduled(cron = "0 0 * * * *")
    public void removeExpiredCarts(){
        LocalDateTime now = LocalDateTime.now();
        long before = cartRepository.count();
        cartRepository.deleteByExpiredAtBefore(now);
        long after = cartRepository.count();
        System.out.println("Deleted: " + (before - after) + " carts at: " + now);
    }
}
