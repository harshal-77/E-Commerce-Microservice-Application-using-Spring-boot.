package com.e_commerce.order_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddToCartRequest {

    private BigDecimal productPrice;
    private Integer quantity;
}
