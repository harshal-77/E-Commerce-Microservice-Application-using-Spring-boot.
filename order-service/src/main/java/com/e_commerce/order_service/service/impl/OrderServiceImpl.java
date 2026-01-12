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
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;


    @Override
    @Transactional
    public CartResponse addToCart(Long userId, AddToCartRequest request) {

        // 1️⃣ Find ACTIVE cart for the user, or create a new one
        Cart cart = cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseGet(() -> createNewCart(userId));

        // 2️⃣ Check if the cart already has this product
        Optional<CartItem> optionalItem = cart.getCartItems().stream()
                .filter(i -> i.getProductId().equals(request.getProductId()))
                .findFirst();

        if (optionalItem.isPresent()) {
            // 3️⃣ Update existing CartItem
            CartItem item = optionalItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            item.setPrice(request.getProductPrice());
        } else {
            // 4️⃣ Create new CartItem
            CartItem item = new CartItem();
            item.setProductId(request.getProductId());
            item.setQuantity(request.getQuantity());
            item.setPrice(request.getProductPrice());

            // 5️⃣ Maintain both sides
            cart.addItem(item);
        }

        // 6️⃣ Save the cart (cascades to items)
        Cart savedCart = cartRepository.saveAndFlush(cart);

        // 7️⃣ Ensure items are loaded (optional but safe)
        savedCart.getCartItems().size();

        // 8️⃣ Map to DTO
        return mapToCartResponse(savedCart);
    }




    @Override
    public OrderResponse checkout(Long cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getStatus() != CartStatus.ACTIVE) {
            throw new RuntimeException("Cart is not active");
        }

        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setUserId(cart.getUserId());
        order.setPrice(calculateCartTotal(cart));
        order.setStatus(OrderStatus.CREATED);

        Order savedOrder = orderRepository.save(order);

        cart.setStatus(CartStatus.CHECKED_OUT);
        cartRepository.save(cart);

        return mapToResponse(savedOrder);
    }

    private BigDecimal calculateCartTotal(Cart cart) {
        return cart.getCartItems().stream()
                .map(item -> item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }





    @Override
    public OrderResponse placeOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Order cannot be placed");
        }

        // 1. Payment validation (mock)
        boolean paymentSuccess = true;

        if (!paymentSuccess) {
            throw new RuntimeException("Payment failed");
        }

        // 2. Inventory check (mock)
        boolean inventoryAvailable = true;

        if (!inventoryAvailable) {
            throw new RuntimeException("Insufficient inventory");
        }

        // 3. Update order status
        order.setStatus(OrderStatus.PLACED);
        Order savedOrder = orderRepository.save(order);

        return mapToResponse(savedOrder);
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
                order.getPrice()
        );
    }

    private Cart createNewCart(Long userId) {

        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setStatus(CartStatus.ACTIVE);

        return cartRepository.save(cart);
    }

    private CartResponse mapToCartResponse(Cart cart) {

        List<CartItemResponse> items = cart.getCartItems().stream()
                .map(item -> {
                    BigDecimal price = item.getPrice() != null
                            ? item.getPrice()
                            : BigDecimal.ZERO;

                    int quantity = item.getQuantity() != null
                            ? item.getQuantity()
                            : 0;

                    BigDecimal totalPrice =
                            price.multiply(BigDecimal.valueOf(quantity));

                    return new CartItemResponse(
                            item.getProductId(),
                            quantity,
                            price,
                            totalPrice
                    );
                })
                .toList();

        BigDecimal totalAmount = items.stream()
                .map(CartItemResponse::getTotalPrice)
                .filter(Objects::nonNull)          // IMPORTANT
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