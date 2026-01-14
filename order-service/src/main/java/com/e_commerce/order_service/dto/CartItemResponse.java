package com.e_commerce.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {

    private String productId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;

//    public CartItemResponse(Long productId, Integer quantity, BigDecimal price, BigDecimal multiply) {
//    }
}
