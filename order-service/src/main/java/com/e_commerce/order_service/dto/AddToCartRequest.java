package com.e_commerce.order_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddToCartRequest {

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    private BigDecimal productPrice;
}