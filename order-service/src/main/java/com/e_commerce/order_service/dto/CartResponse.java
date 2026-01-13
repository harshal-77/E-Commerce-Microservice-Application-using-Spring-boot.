package com.e_commerce.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {

    private Long cartId;
    private Long userId;
    private String status;
    private BigDecimal totalAmount;
    private List<CartItemResponse> items;

//    public CartResponse(Long id, Long userId, String name, BigDecimal totalAmount, List<CartItemResponse> items) {
//    }
}