package com.e_commerce.order_service.service;


import com.e_commerce.order_service.dto.AddToCartRequest;
import com.e_commerce.order_service.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse addToCart(AddToCartRequest request);

    OrderResponse placeOrder(Long orderId);

    List<OrderResponse> getAllOrders();
}
