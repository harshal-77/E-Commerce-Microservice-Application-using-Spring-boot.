package com.e_commerce.order_service.repository;

import com.e_commerce.order_service.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<Object> findByCartIdAndProductId(Long id, Long productId);
}