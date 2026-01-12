package com.e_commerce.order_service.repository;

import com.e_commerce.order_service.model.Cart;
import com.e_commerce.order_service.model.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserIdAndStatus(Long userId, CartStatus status);
}