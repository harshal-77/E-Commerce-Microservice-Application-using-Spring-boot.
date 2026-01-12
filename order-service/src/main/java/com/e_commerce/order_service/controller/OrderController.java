package com.e_commerce.order_service.controller;

import com.e_commerce.order_service.dto.AddToCartRequest;
import com.e_commerce.order_service.dto.OrderResponse;
import com.e_commerce.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/cart")
    public OrderResponse addToCart(@RequestBody AddToCartRequest request) {
        return orderService.addToCart(request);
    }


    @PostMapping("/{orderId}/checkout")
    public OrderResponse placeOrder(@PathVariable Long orderId) {
        return orderService.placeOrder(orderId);
    }


    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }
}
