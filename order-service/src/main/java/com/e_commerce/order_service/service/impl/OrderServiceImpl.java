package com.e_commerce.order_service.service.impl;

import com.e_commerce.order_service.dto.AddToCartRequest;
import com.e_commerce.order_service.dto.CartItemResponse;
import com.e_commerce.order_service.dto.CartResponse;
import com.e_commerce.order_service.dto.OrderResponse;
import com.e_commerce.order_service.model.*;
import com.e_commerce.order_service.model.enums.CartStatus;
import com.e_commerce.order_service.model.enums.OrderStatus;
import com.e_commerce.order_service.repository.CartItemRepository;
import com.e_commerce.order_service.repository.CartRepository;
import com.e_commerce.order_service.repository.OrderRepository;
import com.e_commerce.order_service.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;


    @Override
    public CartResponse addToCart(Long userId, AddToCartRequest request) {

        Cart cart = cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseGet(() -> createNewCart(userId));

        Optional<CartItem> optionalItem = cart.getCartItems().stream()
                .filter(i -> i.getProductId().equals(request.getProductId()))
                .findFirst();

        if (optionalItem.isPresent()) {
            CartItem item = optionalItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            item.setPrice(request.getProductPrice());
        } else {
            CartItem item = new CartItem();
            item.setProductId(request.getProductId());
            item.setQuantity(request.getQuantity());
            item.setPrice(request.getProductPrice());

            cart.addItem(item);
        }

        Cart savedCart = cartRepository.save(cart);
        return mapToCartResponse(savedCart);
    }


    @Override
    public OrderResponse checkout(Long cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getStatus() != CartStatus.ACTIVE) {
            throw new RuntimeException("Cart is not active");
        }

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setUserId(cart.getUserId());
        order.setTotalAmount(calculateCartTotal(cart));
        order.setStatus(OrderStatus.CREATED);

        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProductId(cartItem.getProductId());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getPrice());
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .toList();

        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        cart.setStatus(CartStatus.CHECKED_OUT);
        cartRepository.save(cart);

        return mapToOrderResponse(savedOrder);
    }


    @Override
    public OrderResponse placeOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Order cannot be placed");
        }

        // Mock payment
        boolean paymentSuccess = true;
        if (!paymentSuccess) {
            throw new RuntimeException("Payment failed");
        }

        // Mock inventory
        boolean inventoryAvailable = true;
        if (!inventoryAvailable) {
            throw new RuntimeException("Insufficient inventory");
        }

        order.setStatus(OrderStatus.PLACED);
        return mapToOrderResponse(orderRepository.save(order));
    }


    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToOrderResponse)
                .toList();
    }


    private BigDecimal calculateCartTotal(Cart cart) {
        return cart.getCartItems().stream()
                .map(item -> item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private Cart createNewCart(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setStatus(CartStatus.ACTIVE);
        return cartRepository.save(cart);
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getTotalAmount()
        );
    }

    private CartResponse mapToCartResponse(Cart cart) {

        List<CartItemResponse> items = cart.getCartItems().stream()
                .map(item -> {
                    BigDecimal price = item.getPrice() != null ? item.getPrice() : BigDecimal.ZERO;
                    int quantity = item.getQuantity() != null ? item.getQuantity() : 0;

                    return new CartItemResponse(
                            item.getProductId(),
                            quantity,
                            price,
                            price.multiply(BigDecimal.valueOf(quantity))
                    );
                })
                .toList();

        BigDecimal totalAmount = items.stream()
                .map(CartItemResponse::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(
                cart.getId(),
                cart.getUserId(),
                cart.getStatus().name(),
                totalAmount,
                items
        );
    }
}
