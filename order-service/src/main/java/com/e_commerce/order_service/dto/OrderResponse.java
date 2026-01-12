package com.e_commerce.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private String orderNumber;
    private BigDecimal price;
    private Integer quantity;
}