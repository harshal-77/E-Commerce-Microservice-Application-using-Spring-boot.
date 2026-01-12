package com.e_commerce.order_service.controller;

import com.e_commerce.order_service.dto.AddToCartRequest;
import com.e_commerce.order_service.dto.CartResponse;
import com.e_commerce.order_service.dto.OrderResponse;
import com.e_commerce.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/cart/add")
    public ResponseEntity<CartResponse> addToCart(@Valid @RequestBody AddToCartRequest request)
    {

//        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();
        Long userId = 1L;
        CartResponse response = orderService.addToCart(userId, request);
        return ResponseEntity.ok(response);
    }



    @PostMapping("/{orderId}/checkout")
    public ResponseEntity<OrderResponse> placeOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.placeOrder(orderId));
    }


    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return  ResponseEntity.ok(orderService.getAllOrders());
    }
}
