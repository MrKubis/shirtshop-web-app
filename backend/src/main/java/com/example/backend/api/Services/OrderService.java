package com.example.backend.api.Services;

import com.example.backend.api.DTO.Order.OrderDto;
import com.example.backend.api.DTO.Order.OrderMapper;
import com.example.backend.api.DTO.Order.PostOrderDto;
import com.example.backend.api.Exceptions.ItemNotFoundException;
import com.example.backend.api.Models.Item;
import com.example.backend.api.Models.Order;
import com.example.backend.api.Models.OrderItem;
import com.example.backend.api.Repositories.ItemRepository;
import com.example.backend.api.Repositories.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAll(){
        return orderRepository.findAll().stream().map(orderMapper::toDto).toList();
    }
    @Transactional
    public OrderDto create(PostOrderDto dto){
        Order order = Order.builder()
                .phoneNumber(dto.phoneNumber())
                .email(dto.email())
                .build();

        List<OrderItem> orderItems = dto.items().stream()
                .map(i-> {
                    Item item = itemRepository.findById(i.itemId())
                            .orElseThrow(() -> new ItemNotFoundException(i.itemId()));
                return OrderItem.builder()
                        .order(order)
                        .item(item)
                        .quantity(i.quantity())
                        .build();
                }).toList();
        order.setItems(orderItems);
        return orderMapper.toDto(orderRepository.save(order));
    }
    public void delete(UUID id){
        orderRepository.deleteById(id);
    }
}
