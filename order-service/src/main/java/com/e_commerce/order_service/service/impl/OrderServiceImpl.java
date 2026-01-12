package com.e_commerce.order_service.service.impl;

import com.e_commerce.order_service.dto.AddToCartRequest;
import com.e_commerce.order_service.dto.OrderResponse;
import com.e_commerce.order_service.model.Order;
import com.e_commerce.order_service.repository.OrderRepository;
import com.e_commerce.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;


    @Override
    public OrderResponse addToCart(AddToCartRequest request) {

        BigDecimal totalPrice =
                request.getProductPrice()
                        .multiply(BigDecimal.valueOf(request.getQuantity()));

        Order order = new Order();
        order.setQuantity(request.getQuantity());
        order.setPrice(totalPrice);
        order.setOrderNumber(generateOrderNumber());

        Order savedOrder = orderRepository.save(order);

        return mapToResponse(savedOrder);
    }


    @Override
    public OrderResponse placeOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // - payment validation
        // - inventory check
        // - order status update

        return mapToResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private OrderResponse mapToResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getPrice(),
                order.getQuantity()
        );
    }
}