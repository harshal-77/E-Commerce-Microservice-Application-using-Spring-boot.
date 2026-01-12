package com.e_commerce.order_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponse {

    private Long productId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;

    public CartItemResponse(Long productId, Integer quantity, BigDecimal price, BigDecimal multiply) {
    }
}
