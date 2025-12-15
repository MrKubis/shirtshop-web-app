package com.example.backend.api.Services;

import com.example.backend.api.Models.Cart;
import com.example.backend.api.Models.ItemInstance;
import com.example.backend.api.Repositories.CartRepository;
import com.example.backend.api.Repositories.ItemInstanceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartCleanupService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ItemInstanceRepository itemInstanceRepository;

    @Transactional
    @Scheduled(cron = "0 0 * * * *")
    public void removeExpiredCarts(){
        LocalDateTime now = LocalDateTime.now();
        long before = cartRepository.count();
        //Ustawienie statusu wszystkich przedmiotów w przeterminowanych koszykach na available
        List<Cart> carts = cartRepository.getAllByExpiredAtBefore(now);
        for(Cart cart: carts){
            for(UUID itemInstanceId: cart.getItemInstanceIdList()){
                Optional<ItemInstance> itemInstance =  itemInstanceRepository.findById(itemInstanceId);
                itemInstance.ifPresent(instance -> {
                    instance.setStatus("AVAILABLE");
                    itemInstanceRepository.save(instance);
                });
            }
        }

        cartRepository.deleteByExpiredAtBefore(now);
        long after = cartRepository.count();
        System.out.println("Deleted: " + (before - after) + " carts at: " + now);
    }
}
