package com.example.backend.api.Controllers;

import com.example.backend.api.DTO.Order.OrderDto;
import com.example.backend.api.DTO.Order.PostOrderDto;
import com.example.backend.api.Services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @GetMapping
    public List<OrderDto> getAll(){
        return service.getAll();
    }
    @PostMapping
    public OrderDto create(@RequestBody PostOrderDto dto){
        return service.create(dto);
    }
}
